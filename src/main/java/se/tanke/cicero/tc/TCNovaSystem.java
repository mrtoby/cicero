package se.tanke.cicero.tc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import se.tanke.cicero.DeviceType;
import se.tanke.cicero.DeviceConfiguration;
import se.tanke.cicero.Patch;
import se.tanke.cicero.midi.ManufacturerIdConstants;
import se.tanke.cicero.midi.MidiConstants;
import se.tanke.cicero.midi.MidiMessage;
import se.tanke.cicero.midi.MidiMessageReader;
import se.tanke.cicero.midi.NotMatchingMessageException;

/**
 * This is a class representing TC Electronics Nova System.
 */
public class NovaSystem implements DeviceType {

	private static final String DEVICE_NAME = "TC Electronics Nova System";
	private static final String DEVICE_ID = "tc-nova";
	private static final String CURRENT_PATCH_BANK = "current";
	private static final String FACTORY_BANK_PREFIX = "F";

	private static final int NUMBER_OF_FACTORY_PATCHES = 30;
    private static final int NUMBER_OF_USER_PATCHES = 60;
    private static final int NUMBER_OF_VARIATIONS = 28;
    private static final int MODEL_ID_BYTE = 0x63;
    private static final int PRESET_REQUEST_COMMAND_BYTE = 0x45;
    private static final int PRESET_TYPE_PRESET_PARAMETERS_BYTE = 0x01;
    private static final int PRESET_TYPE_SYSTEM_PARAMETERS_BYTE = 0x02;
	private static final int DEFAULT_SYSEX_ID = 0;
	
	@Override
	public String getName() {
		return DEVICE_NAME;
	}

	@Override
	public String getId() {
		return DEVICE_ID;
	}

	@Override
	public DeviceConfiguration getDefaultConfiguration() {
		final DeviceConfiguration config = new DeviceConfiguration();
		config.setSysexId(DEFAULT_SYSEX_ID);
		return config;
	}

	@Override
	public MidiMessage getDeviceDetectionMessage(final DeviceConfiguration config) {
		return createRequestSystemParametersSysex(config);
	}

	@Override
	public boolean isDeviceDetectionResponse(final DeviceConfiguration config, final MidiMessage reply) {
		return isSystemParametersSysex(reply);
	}

	@Override
	public List<MidiMessage> getRequestAllPatchesMessage(final DeviceConfiguration config) {
		List<MidiMessage> messages = new ArrayList<>();
		for (int i = 0; i < NUMBER_OF_FACTORY_PATCHES; i++) {
			messages.add(createRequestFactoryPresetSysex(config, i));
		}
		for (int i = 0; i < NUMBER_OF_USER_PATCHES; i++) {
			messages.add(createRequestUserPresetSysex(config, i));
		}
		return null;
	}

	@Override
	public boolean isManufacturerApplicable(final int[] manufacturerBytes) {
		return Arrays.equals(manufacturerBytes, ManufacturerIdConstants.TC_ELECTRONICS);
	}

	@Override
	public Optional<Patch> decodePatch(final DeviceConfiguration config, final MidiMessage msg) {
		final MidiMessageReader reader = msg.reader();
		
		try {
			// Total message size should be 520 bytes
			reader.matchByte(MidiConstants.SYSTEM_EXCLUSIVE)
					.matchBytes(ManufacturerIdConstants.TC_ELECTRONICS)
					.skipByte() // The sysex id
					.matchByte(MODEL_ID_BYTE)
					.matchByte(0x20) // Patch data?
					.matchByte(0x01);
			final int number = reader.read14BitNumberLE();
			final String name = reader.readString(21);
			reader.skipBytes(488) // The rest of the data
					.matchByte(MidiConstants.SYSTEM_EXCLUSIVE_END)
					.done();

			// Skip variation patches and current patch
			if (isVariationPatch(number) || isCurrentPatch(number)) {
				return Optional.empty();
			}
			
			final Patch patch = new Patch();
			patch.setName(name);
			patch.setMessage(msg);

			if (number == 0) {
				patch.setBank(CURRENT_PATCH_BANK);
				patch.setPosition(1);
			} else {
				patch.setBank(bankByPatchNumber(number));
				patch.setPosition(1 + ((number - 1) % 3));
			}
			return Optional.of(patch);
		} catch (NotMatchingMessageException e) {
			// TODO Logging
			return Optional.empty();
		}
	}

	@Override
	public Optional<MidiMessage> encodePatch(final DeviceConfiguration config, final Patch patch) {
		// TODO Auto-generated method stub
		return null;
	}

	private static boolean isVariationPatch(final int number) {
		final int variationNo = number - NUMBER_OF_FACTORY_PATCHES - NUMBER_OF_USER_PATCHES;
		return variationNo >= 0 && variationNo < NUMBER_OF_VARIATIONS;
	}
	
	private static boolean isCurrentPatch(final int number) {
		return number == 0;
	}
	
	private static String bankByPatchNumber(final int number) {
		if (number < 0) {
			throw new IllegalArgumentException("Negative patch number");
		}
		if (number == 0) {
			return CURRENT_PATCH_BANK;
		}
		int patchNo = number - 1;
		if (patchNo < NUMBER_OF_FACTORY_PATCHES) {
			return FACTORY_BANK_PREFIX + (patchNo / 3);
		}
		patchNo -= NUMBER_OF_FACTORY_PATCHES;
		if (patchNo < NUMBER_OF_USER_PATCHES) {
			return Integer.toString(patchNo / 3);
		}
		throw new IllegalArgumentException("IllegalPatchNumber");
	}
	
    private static boolean isSystemParametersSysex(final MidiMessage msg) {
    	try {
    		msg.matcher()
    				.matchByte(MidiConstants.SYSTEM_EXCLUSIVE)
    				.matchBytes(ManufacturerIdConstants.TC_ELECTRONICS)
    				.skipByte() // The sysex id
    				.matchByte(MODEL_ID_BYTE)
    				.matchByte(0x20) // Patch data?
    				.matchByte(0x02) // Type of data?
    				.skipBytes(517) // Total size is 526 bytes
	    			.matchByte(MidiConstants.SYSTEM_EXCLUSIVE_END)
    				.done();
    	} catch (NotMatchingMessageException e) {
    		return false;
    	}
    	return true;
    }

    private static MidiMessage createRequestSystemParametersSysex(final DeviceConfiguration config) {
    	return MidiMessage.newBuilder()
    			.append(MidiConstants.SYSTEM_EXCLUSIVE)
    			.append(ManufacturerIdConstants.TC_ELECTRONICS)
    			.append(config.getSysexId())
    			.append(MODEL_ID_BYTE)
    			.append(PRESET_REQUEST_COMMAND_BYTE)
    			.append(PRESET_TYPE_SYSTEM_PARAMETERS_BYTE)
    			.append(0x00, 0x00)
    			.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
    			.build();
    }

    private static MidiMessage createRequestFactoryPresetSysex(final DeviceConfiguration config, final int factoryPresetNumber) {
        if (factoryPresetNumber < 0 || factoryPresetNumber >= NUMBER_OF_FACTORY_PATCHES) {
            throw new IllegalArgumentException("Factory preset number out of range: " + factoryPresetNumber);
        }
        return createRequestPresetSysex(config, factoryPresetNumber + 1);
    }

    private static MidiMessage createRequestUserPresetSysex(final DeviceConfiguration config, final int userPresetNumber) {
        if (userPresetNumber < 0 || userPresetNumber >= NUMBER_OF_USER_PATCHES) {
            throw new IllegalArgumentException("User preset number out of range: " + userPresetNumber);
        }
        return createRequestPresetSysex(config, userPresetNumber + 1 + NUMBER_OF_FACTORY_PATCHES);
    }
    
    private static MidiMessage createRequestPresetSysex(final DeviceConfiguration config, final int presetNumber) {
    	return MidiMessage.newBuilder()
    			.append(MidiConstants.SYSTEM_EXCLUSIVE)
    			.append(ManufacturerIdConstants.TC_ELECTRONICS)
    			.append(config.getSysexId())
    			.append(MODEL_ID_BYTE)
    			.append(PRESET_REQUEST_COMMAND_BYTE)
    			.append(PRESET_TYPE_PRESET_PARAMETERS_BYTE)
    			.append14BitNumberBE(presetNumber)
    			.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
    			.build();
    }
}
