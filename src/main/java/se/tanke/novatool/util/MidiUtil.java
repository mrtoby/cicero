package se.tanke.novatool.util;

import javax.sound.midi.SysexMessage;

/**
 * Utils for doing MIDI stuff.
 */
public final class MidiUtil {

    private MidiUtil()  {
    }

    /**
     * Given a number, return a sequence of two bytes which represent the number.
     * @param number A 14-bit number
     * @return The 7-bit MSB in the first position and the 7-bit LSB in the last position.
     */
    public static byte[] numberAsTwo7BitBytes(final int number) {
        if (number < 0 || number > 0x3fff) {
            throw new IllegalArgumentException("Expected a 14-bit number, got: " + number);
        }
        final byte[] data = {
                (byte) ((number >> 7) & 0x7f),
                (byte) (number & 0x7f)
        };
        return data;
    }    

    /**
     * Given two 7-bit numbers, construct the 14-bit number represented by the two.
     * @param msb The first byte, the one with lowest index in the message
     * @param lsb The second byte, the one with highest index in the message
     * @return A 14-bit number
     */
    public static int numberFromTwo7BitBytes(final int msb, final int lsb) {
        if (msb < 0 || msb > 0x7f) {
            throw new IllegalArgumentException("Expected a 7-bit number, got: " + msb);
        }
        if (lsb < 0 || lsb > 0x7f) {
            throw new IllegalArgumentException("Expected a 7-bit number, got: " + lsb);
        }
        return (msb << 7) | lsb;
    }

    /**
     * Check if a sysex message matches a pattern.
     * @param sysex The sysex to compare
     * @param status The expected system exclusive status byte
     * @param pattern The expected data
     * @return <code>true</code> if the sysex matches the expected values
     */
    public static boolean matches(final SysexMessage sysex, final int status, final int ... pattern) {
        if (sysex.getStatus() != status) {
            return false;
        }
        final byte[] data = sysex.getData();
        if (data.length != pattern.length) {
            return false;
        }
        for (int i = 0; i < data.length; i++) {
            if (data[i] != pattern[i]) {
                return false;
            }
        }
        return true;
    }
}
