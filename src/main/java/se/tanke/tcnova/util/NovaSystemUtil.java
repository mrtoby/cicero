package se.tanke.tcnova.util;

import static se.tanke.tcnova.util.NovaSystemConstants.NUMBER_OF_FACTORY_PATCHES;
import static se.tanke.tcnova.util.NovaSystemConstants.NUMBER_OF_USER_PATCHES;
import static se.tanke.tcnova.util.NovaSystemConstants.NUMBER_OF_VARIATIONS;
import se.tanke.tcnova.DeviceConfiguration;
import se.tanke.tcnova.midi.MidiConstants;
import se.tanke.tcnova.midi.MidiMessage;
import se.tanke.tcnova.midi.MidiMessageReader;

/**
 * Utility methods that are nice to have when working with the Nova System.
 */
public final class NovaSystemUtil {

    /**
	 * Check if this is a reply from the nova system that indicates an empty
	 * patch or an invalid preset number.
     * @param msg The midi message to check
     * @return <code>true</code> if the reply is a reply that indicates an invalid request
     */
    public static boolean isEmptyOrInvalidPresetReply(final MidiMessage msg) {
    	final MidiMessageReader reader = msg.reader();
    	try {
	    	reader.matchByte(MidiConstants.SYSTEM_EXCLUSIVE);
	    	reader.matchByte(NovaSystemConstants.REPLY_NON_REAL_TIME_BYTE);
	    	reader.skipByte(); // The sysex id
	    	reader.matchByte(NovaSystemConstants.NAK);
	    	reader.matchByte(0); // Dummy
	    	reader.matchByte(MidiConstants.SYSTEM_EXCLUSIVE_END);
	    	reader.done();
    	} catch (IllegalStateException e) {
    		return false;
    	}
    	return true;
    }

    /**
     * Create a sysex message to request system parameters.
     * @param config The configuration
     * @return The sysex message
     */
    public static MidiMessage createRequestSystemParametersSysex(final DeviceConfiguration config) {
    	return MidiMessage.newBuilder()
    			.append(MidiConstants.SYSTEM_EXCLUSIVE)
    			.append(NovaSystemConstants.MANUFACTURER_ID_BYTES)
    			.append(config.getSysexId())
    			.append(NovaSystemConstants.MODEL_ID_BYTE)
    			.append(NovaSystemConstants.PRESET_REQUEST_COMMAND_BYTE)
    			.append(NovaSystemConstants.PRESET_TYPE_SYSTEM_PARAMETERS_BYTE)
    			.append(0x00, 0x00)
    			.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
    			.build();
    }

    /**
     * Create a sysex message to request the currently active patch.
     * @param config The configuration
     * @return The sysex message
     */
    public static MidiMessage createRequestCurrentPatchSysex(final DeviceConfiguration config) {
        return createRequestPatchSysex(config, 0);
    }

    /**
     * Create a sysex message to request a factory preset/patch.
     * @param config The configuration
     * @param factoryPresetNumber A number from 0 to 29
     * @return The sysex message
     */
    public static MidiMessage createRequestFactoryPresetSysex(final DeviceConfiguration config, final int factoryPresetNumber) {
        if (factoryPresetNumber < 0 || factoryPresetNumber >= NUMBER_OF_FACTORY_PATCHES) {
            throw new IllegalArgumentException("Factory preset number out of range: " + factoryPresetNumber);
        }
        return createRequestPatchSysex(config, factoryPresetNumber + 1);
    }

    /**
     * Create a sysex message to request a user preset/patch.
     * @param config The configuration
     * @param userPresetNumber A number from 0 to 59
     * @return The sysex message
     */
    public static MidiMessage createRequestUserPatchSysex(final DeviceConfiguration config, final int userPresetNumber) {
        if (userPresetNumber < 0 || userPresetNumber >= NUMBER_OF_USER_PATCHES) {
            throw new IllegalArgumentException("Patch number out of range: " + userPresetNumber);
        }
        return createRequestPatchSysex(config, userPresetNumber + 1 + NUMBER_OF_FACTORY_PATCHES);
    }
    
    /**
     * Create a sysex message to request a variation patch.
     * @param config The configuration
     * @param variationNumber A number from 0 to 27
     * @return The sysex message
     */
    public static MidiMessage createRequestVariationSysex(final DeviceConfiguration config, final int variationNumber) {
        if (variationNumber < 0 || variationNumber >= NUMBER_OF_VARIATIONS) {
            throw new IllegalArgumentException("Variation number out of range: " + variationNumber);
        }
        return createRequestPatchSysex(config, variationNumber + 1 + NUMBER_OF_FACTORY_PATCHES + NUMBER_OF_USER_PATCHES);
    }

    private static MidiMessage createRequestPatchSysex(final DeviceConfiguration config, final int presetNumber) {
    	return MidiMessage.newBuilder()
    			.append(MidiConstants.SYSTEM_EXCLUSIVE)
    			.append(NovaSystemConstants.MANUFACTURER_ID_BYTES)
    			.append(config.getSysexId())
    			.append(NovaSystemConstants.MODEL_ID_BYTE)
    			.append(NovaSystemConstants.PRESET_REQUEST_COMMAND_BYTE)
    			.append(NovaSystemConstants.PRESET_TYPE_PRESET_PARAMETERS_BYTE)
    			.append14BitNumber(presetNumber)
    			.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
    			.build();
    }
    
    private NovaSystemUtil() {
    }
}
