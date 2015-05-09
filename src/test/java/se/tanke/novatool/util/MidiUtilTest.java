package se.tanke.novatool.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.SysexMessage;

import org.junit.Test;

import se.tanke.novatool.util.MidiUtil;

public class MidiUtilTest {

    @Test
    public void numberAsTwo7BitBytes_works() {
        assertTwoBytes(0x00, 0x00, MidiUtil.numberAsTwo7BitBytes(0x0000));
        assertTwoBytes(0x00, 0x01, MidiUtil.numberAsTwo7BitBytes(0x0001));
        assertTwoBytes(0x00, 0x02, MidiUtil.numberAsTwo7BitBytes(0x0002));
        assertTwoBytes(0x02, 0x00, MidiUtil.numberAsTwo7BitBytes(0x0100));
        assertTwoBytes(0x02, 0x13, MidiUtil.numberAsTwo7BitBytes(0x0113));
        assertTwoBytes(0x0e, 0x13, MidiUtil.numberAsTwo7BitBytes(0x0713));
        assertTwoBytes(0x1c, 0x15, MidiUtil.numberAsTwo7BitBytes(0x0e15));
        assertTwoBytes(0x1c, 0x7f, MidiUtil.numberAsTwo7BitBytes(0x0e7f));
        assertTwoBytes(0x7f, 0x7f, MidiUtil.numberAsTwo7BitBytes(0x3fff));
    }

    @Test(expected = IllegalArgumentException.class)
    public void numberAsTwo7BitBytes_fails_for_negative_numbers() {
        MidiUtil.numberAsTwo7BitBytes(-1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void numberAsTwo7BitBytes_fails_for_15bit_number() {
        MidiUtil.numberAsTwo7BitBytes(0x4000);
    }

    @Test
    public void numberFromTwo7BitBytes_works() {
        assertEquals(0x0000, MidiUtil.numberFromTwo7BitBytes(0x00, 0x00));
        assertEquals(0x0001, MidiUtil.numberFromTwo7BitBytes(0x00, 0x01));
        assertEquals(0x0002, MidiUtil.numberFromTwo7BitBytes(0x00, 0x02));
        assertEquals(0x0100, MidiUtil.numberFromTwo7BitBytes(0x02, 0x00));
        assertEquals(0x0113, MidiUtil.numberFromTwo7BitBytes(0x02, 0x13));
        assertEquals(0x0713, MidiUtil.numberFromTwo7BitBytes(0x0e, 0x13));
        assertEquals(0x0e15, MidiUtil.numberFromTwo7BitBytes(0x1c, 0x15));
        assertEquals(0x0e7f, MidiUtil.numberFromTwo7BitBytes(0x1c, 0x7f));
        assertEquals(0x3fff, MidiUtil.numberFromTwo7BitBytes(0x7f, 0x7f));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void numberFromTwo7BitBytes_fails_if_msb_is_negative() {
        MidiUtil.numberFromTwo7BitBytes(-1, 0x00);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void numberFromTwo7BitBytes_fails_if_msb_is_8bit() {
        MidiUtil.numberFromTwo7BitBytes(0xff, 0x00);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void numberFromTwo7BitBytes_fails_if_lsb_is_negative() {
        MidiUtil.numberFromTwo7BitBytes(0x00, -1);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void numberFromTwo7BitBytes_fails_if_lsb_is_8bit() {
        MidiUtil.numberFromTwo7BitBytes(0x00, 0xff);
    }
    
    @Test
    public void matches_returns_true_for_expected_status_and_pattern() throws InvalidMidiDataException {
        byte[] data = { 0x7e, 0x11, 0x43, 0x64 };
        boolean result = MidiUtil.matches(new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, data, data.length),
                SysexMessage.SYSTEM_EXCLUSIVE,
                0x7e, 0x11, 0x43, 0x64);
        assertTrue(result);
    }
    
    @Test
    public void matches_returns_false_for_wrong_status() throws InvalidMidiDataException {
        byte[] data = { 0x7e, 0x11, 0x43, 0x64 };
        boolean result = MidiUtil.matches(new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, data, data.length),
                SysexMessage.SPECIAL_SYSTEM_EXCLUSIVE,
                0x7e, 0x11, 0x43, 0x64);
        assertFalse(result);
    }

    @Test
    public void matches_returns_false_for_wrong_pattern() throws InvalidMidiDataException {
        byte[] data = { 0x7e, 0x11, 0x43, 0x64 };
        boolean result = MidiUtil.matches(new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, data, data.length),
                SysexMessage.SYSTEM_EXCLUSIVE,
                0x7e, 0x7f, 0x43, 0x64);
        assertFalse(result);
    }
    
    @Test
    public void matches_returns_false_for_too_short_pattern() throws InvalidMidiDataException {
        byte[] data = { 0x7e, 0x11, 0x43, 0x64 };
        boolean result = MidiUtil.matches(new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, data, data.length),
                SysexMessage.SYSTEM_EXCLUSIVE,
                0x7e, 0x7f);
        assertFalse(result);
    }

    @Test
    public void matches_returns_false_for_too_long_pattern() throws InvalidMidiDataException {
        byte[] data = { 0x7e, 0x11, 0x43, 0x64 };
        boolean result = MidiUtil.matches(new SysexMessage(SysexMessage.SYSTEM_EXCLUSIVE, data, data.length),
                SysexMessage.SYSTEM_EXCLUSIVE,
                0x7e, 0x7f, 0x43, 0x64, 0x7f, 0x7f);
        assertFalse(result);
    }

    private void assertTwoBytes(int expectedMsb, int expectedLsb, byte[] result) {
        assertEquals(2, result.length);
        assertEquals(expectedMsb, result[0]);
        assertEquals(expectedLsb, result[1]);
    }
    
}
