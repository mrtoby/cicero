package se.tanke.cicero.midi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MidiMessageReaderTest {

	@Test
	public void readByte_return_existing_byte() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x25);
		MidiMessageReader sut = msg.reader();
		sut.skipByte();
		
		int result = sut.readByte();
		
		sut.skipByte().done();
		assertEquals(0x25, result);
	}
	
	@Test(expected = MidiMessageException.class)
	public void readByte_fails_if_no_data_is_left() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x25);
		MidiMessageReader sut = msg.reader();
		sut.skipBytes(3);
		
		sut.readByte();
	}

	@Test
	public void readBytes_return_existing_bytes() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x25);
		MidiMessageReader sut = msg.reader();
		
		int[] result = sut.readBytes(3);
		
		sut.done();
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE, result[0]);
		assertEquals(0x25, result[1]);
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE_END, result[2]);
	}

	@Test(expected = MidiMessageException.class)
	public void readBytes_throws_if_to_few_bytes_is_left() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x25);
		msg.reader().readBytes(4);
	}

	@Test
	public void read14BitNumberBE_can_read_valid_bytes() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x24, 0x34);
		MidiMessageReader sut = msg.reader();
		sut.skipByte();
		
		int result = sut.read14BitNumberBE();
		
		sut.skipByte().done();
		assertEquals(0x1234, result);
	}
	
	@Test(expected = MidiMessageException.class)
	public void read14BitNumberBE_fails_for_status_byte_in_first_position() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x24, 0x34);
		msg.reader().read14BitNumberBE();
	}
	
	@Test(expected = MidiMessageException.class)
	public void read14BitNumberBE_fails_for_status_byte_in_second_position() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x24, 0x34);
		MidiMessageReader sut = msg.reader();
		sut.skipBytes(2);
		
		sut.read14BitNumberBE();
	}

	@Test
	public void read14BitNumberLE_can_read_valid_bytes() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x34, 0x24);
		MidiMessageReader sut = msg.reader();
		sut.skipByte();
		
		int result = sut.read14BitNumberLE();
		
		sut.skipByte().done();
		assertEquals(0x1234, result);
	}
	
	@Test(expected = MidiMessageException.class)
	public void read14BitNumberLE_fails_for_status_byte_in_first_position() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x24, 0x34);
		msg.reader().read14BitNumberLE();
	}
	
	@Test(expected = MidiMessageException.class)
	public void read14BitNumberLE_fails_for_status_byte_in_second_position() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(0x24, 0x34);
		MidiMessageReader sut = msg.reader();
		sut.skipBytes(2);
		
		sut.read14BitNumberLE();
	}

	@Test
	public void readString_can_read_valid_string() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes('a', 'b');
		MidiMessageReader sut = msg.reader();
		sut.skipByte();
		
		final String result = sut.readString(2);
		
		sut.skipByte().done();
		assertEquals("ab", result);
	}
	
	@Test
	public void readString_ends_string_at_null_char() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes('a', 'b', 0, '?');
		MidiMessageReader sut = msg.reader();
		sut.skipByte();
		
		final String result = sut.readString(4);
		
		sut.skipByte().done();
		assertEquals("ab", result);
	}
	
	@Test
	public void readString_trim_trailing_whitespace() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes('a', 'b', ' ', ' ');
		MidiMessageReader sut = msg.reader();
		sut.skipByte();
		
		final String result = sut.readString(4);
		
		sut.skipByte().done();
		assertEquals("ab", result);
	}
	
	@Test
	public void readString_trim_leading_whitespace() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes(' ', 'a', 'b', ' ');
		MidiMessageReader sut = msg.reader();
		sut.skipByte();
		
		final String result = sut.readString(4);
		
		sut.skipByte().done();
		assertEquals("ab", result);
	}
	
	@Test(expected = MidiMessageException.class)
	public void readString_fails_for_characters_using_bit8() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes('a', 'b');
		msg.reader().readString(4);
	}
	
	@Test(expected = MidiMessageException.class)
	public void skipByte_fails_if_no_bytes_left() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes('a', 'b');
		msg.reader()
			.skipBytes(4)
			.skipByte();
	}
	
	@Test(expected = MidiMessageException.class)
	public void skipBytes_fails_if_no_bytes_left() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes('a', 'b');
		msg.reader().skipBytes(5);
	}
	
	// TODO matchByte
	// TODO matchBytes
	// TODO match14BitNumberBE
	// TODO match14BitNumberLE
	
	@Test(expected = MidiMessageException.class)
	public void done_fails_if_there_is_bytes_left() throws MidiMessageException {
		MidiMessage msg = sysexWithBytes('a', 'b');
		msg.reader().done();
	}
	
	private MidiMessage sysexWithBytes(int ... bytes) {
		return MidiMessage.newBuilder()
				.append(MidiConstants.SYSTEM_EXCLUSIVE)
				.append(bytes)
				.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
				.build();
	}
}
