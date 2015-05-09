package se.tanke.novatool.util;

import static se.tanke.novatool.util.MidiUtil.numberAsTwo7BitBytes;
import static se.tanke.novatool.util.NovaSystemConstants.MANUFACTURER_ID_BYTE1;
import static se.tanke.novatool.util.NovaSystemConstants.MANUFACTURER_ID_BYTE2;
import static se.tanke.novatool.util.NovaSystemConstants.MANUFACTURER_ID_BYTE3;
import static se.tanke.novatool.util.NovaSystemConstants.MODEL_ID_BYTE;
import static se.tanke.novatool.util.NovaSystemConstants.NUMBER_OF_FACTORY_PATCHES;
import static se.tanke.novatool.util.NovaSystemConstants.NUMBER_OF_USER_PATCHES;
import static se.tanke.novatool.util.NovaSystemConstants.NUMBER_OF_VARIATIONS;
import static se.tanke.novatool.util.NovaSystemConstants.PRESET_REQUEST_COMMAND_BYTE;
import static se.tanke.novatool.util.NovaSystemConstants.PRESET_TYPE_PRESET_PARAMETERS_BYTE;
import static se.tanke.novatool.util.NovaSystemConstants.PRESET_TYPE_SYSTEM_PARAMETERS_BYTE;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import se.tanke.novatool.Configuration;

/**
 * This class provides methods to create sysex messages to request data from the
 * Nova System.
 */
public final class SysexMessageRequests {

    private SysexMessageRequests() {
    }
    
    /**
     * Create a sysex message to request system parameters.
     * @param config The configuration
     * @return The sysex
     */
    public static SysexMessage createRequestSystemParametersSysex(final Configuration config) {
        byte[] data = { 
                MANUFACTURER_ID_BYTE1,
                MANUFACTURER_ID_BYTE2,
                MANUFACTURER_ID_BYTE3,
                config.getSysExIdByte(),
                MODEL_ID_BYTE,
                PRESET_REQUEST_COMMAND_BYTE,
                PRESET_TYPE_SYSTEM_PARAMETERS_BYTE,
                0x00, 0x00 // Dummy
        };
        try {
            return new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, data, data.length);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Failed to create sysex message", e);
        }
    }

    /**
     * Create a sysex message to request the currently active patch.
     * @param config The configuration
     * @return The sysex
     */
    public static SysexMessage createRequestCurrentPatchSysex(final Configuration config) {
        return createRequestPatchSysex(config, 0);
    }

    /**
     * Create a sysex message to request a factory preset/patch.
     * @param config The configuration
     * @param factoryPresetNumber A number from 0 to 29
     * @return The sysex
     */
    public static SysexMessage createRequestFactoryPresetSysex(final Configuration config, final int factoryPresetNumber) {
        if (factoryPresetNumber < 0 || factoryPresetNumber >= NUMBER_OF_FACTORY_PATCHES) {
            throw new IllegalArgumentException("Factory preset number out of range: " + factoryPresetNumber);
        }
        return createRequestPatchSysex(config, factoryPresetNumber + 1);
    }

    /**
     * Create a sysex message to request a user preset/patch.
     * @param config The configuration
     * @param userPresetNumber A number from 0 to 59
     * @return The sysex
     */
    public static SysexMessage createRequestUserPatchSysex(final Configuration config, final int userPresetNumber) {
        if (userPresetNumber < 0 || userPresetNumber >= NUMBER_OF_USER_PATCHES) {
            throw new IllegalArgumentException("Patch number out of range: " + userPresetNumber);
        }
        return createRequestPatchSysex(config, userPresetNumber + 1 + NUMBER_OF_FACTORY_PATCHES);
    }
    
    /**
     * Create a sysex message to request a variation patch.
     * @param config The configuration
     * @param variationNumber A number from 0 to 27
     * @return The sysex
     */
    public static SysexMessage createRequestVariationSysex(final Configuration config, final int variationNumber) {
        if (variationNumber < 0 || variationNumber >= NUMBER_OF_VARIATIONS) {
            throw new IllegalArgumentException("Variation number out of range: " + variationNumber);
        }
        return createRequestPatchSysex(config, variationNumber + 1 + NUMBER_OF_FACTORY_PATCHES + NUMBER_OF_USER_PATCHES);
    }

    private static SysexMessage createRequestPatchSysex(final Configuration config, final int presetNumber) {
        final byte[] presetNumberBytes = numberAsTwo7BitBytes(presetNumber);
        final byte[] data = { 
                MANUFACTURER_ID_BYTE1,
                MANUFACTURER_ID_BYTE2,
                MANUFACTURER_ID_BYTE3,
                config.getSysExIdByte(),
                MODEL_ID_BYTE,
                PRESET_REQUEST_COMMAND_BYTE,
                PRESET_TYPE_PRESET_PARAMETERS_BYTE,
                presetNumberBytes[0],
                presetNumberBytes[1],
        };
        try {
            return new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, data, data.length);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Failed to create sysex message", e);
        }
    }
}
