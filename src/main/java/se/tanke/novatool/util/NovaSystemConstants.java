package se.tanke.novatool.util;

/**
 * Constants for the nova system.
 */
public final class NovaSystemConstants {

    /** Number of factory patches. */
    public static final int NUMBER_OF_FACTORY_PATCHES = 30;
    
    /** Number of user patches. */
    public static final int NUMBER_OF_USER_PATCHES = 60;
    
    /** Number of variations 7 x 4. */
    public static final int NUMBER_OF_VARIATIONS = 28;

    /** The first byte in the manufacturer id. */
    public static final int MANUFACTURER_ID_BYTE1 = 0x00;

    /** The second byte in the manufacturer id. */
    public static final int MANUFACTURER_ID_BYTE2 = 0x20;

    /** The third (and last) byte in the manufacturer id. */
    public static final int MANUFACTURER_ID_BYTE3 = 0x1f;

    /** The model id for the Nova System. */
    public static final int MODEL_ID_BYTE = 0x63;
    
    /** The sysex command for requesting presets (and system parameters). */
    public static final int PRESET_REQUEST_COMMAND_BYTE = 0x45;
    
    /** The preset type for requesting preset parameters. */
    public static final int PRESET_TYPE_PRESET_PARAMETERS_BYTE = 0x01;

    /** The preset type for requesting system parameters. */
    public static final int PRESET_TYPE_SYSTEM_PARAMETERS_BYTE = 0x02;

    /** Used in replies that not contains real time data. */
    public static final int REPLY_NON_REAL_TIME_BYTE = 0x7e;

    /** Used to indicate an error in replies. */
    public static final int NAK = 0x7e;
    
    private NovaSystemConstants() {
    }
}
