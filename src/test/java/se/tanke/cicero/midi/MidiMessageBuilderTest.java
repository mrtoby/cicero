package se.tanke.cicero.midi;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.tanke.cicero.midi.MidiConstants;
import se.tanke.cicero.midi.MidiMessage;

public class MidiMessageBuilderTest {

	@Test
	public void build_creates_expected_message() {
		final MidiMessage result = MidiMessage.newBuilder()
				.append(MidiConstants.SYSTEM_EXCLUSIVE)
				.append(0x3f, 0x00)
				.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
				.build();
		
		final byte[] data = result.getData();
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE, Byte.toUnsignedInt(data[0]));
		assertEquals(0x3f, Byte.toUnsignedInt(data[1]));
		assertEquals(0x00, Byte.toUnsignedInt(data[2]));
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE_END, Byte.toUnsignedInt(data[3]));
	}
	
	@Test
	public void append14BitNumber_works_with_max_value() {
		final MidiMessage result = MidiMessage.newBuilder()
				.append(MidiConstants.SYSTEM_EXCLUSIVE)
				.append14BitNumberBE(0x3fff)
				.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
				.build();
		
		final byte[] data = result.getData();
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE, Byte.toUnsignedInt(data[0]));
		assertEquals(0x7f, Byte.toUnsignedInt(data[1]));
		assertEquals(0x7f, Byte.toUnsignedInt(data[2]));
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE_END, Byte.toUnsignedInt(data[3]));
	}

	@Test
	public void append14BitNumber_works_with_zero_value() {
		final MidiMessage result = MidiMessage.newBuilder()
				.append(MidiConstants.SYSTEM_EXCLUSIVE)
				.append14BitNumberBE(0x0000)
				.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
				.build();
		
		final byte[] data = result.getData();
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE, Byte.toUnsignedInt(data[0]));
		assertEquals(0x00, Byte.toUnsignedInt(data[1]));
		assertEquals(0x00, Byte.toUnsignedInt(data[2]));
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE_END, Byte.toUnsignedInt(data[3]));
	}

	@Test
	public void append14BitNumber_works_with_some_value() {
		final MidiMessage result = MidiMessage.newBuilder()
				.append(MidiConstants.SYSTEM_EXCLUSIVE)
				.append14BitNumberBE(0x1234)
				.append(MidiConstants.SYSTEM_EXCLUSIVE_END)
				.build();
		
		final byte[] data = result.getData();
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE, Byte.toUnsignedInt(data[0]));
		assertEquals(0x24, Byte.toUnsignedInt(data[1]));
		assertEquals(0x34, Byte.toUnsignedInt(data[2]));
		assertEquals(MidiConstants.SYSTEM_EXCLUSIVE_END, Byte.toUnsignedInt(data[3]));
	}
}
