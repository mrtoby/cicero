package se.tanke.tcnova.midi;

/**
 * This class can be used to read midi data from a midi message.
 * 
 * All operations will make sure that there are bytes left in the message. An
 * IllegalStateException will be thrown if too few bytes are left etc.
 * 
 * This class is also a MidiMessageMatcher and will cast into such as soon
 * as a matching operation is used and chaining is possible.
 */
public class MidiMessageReader implements MidiMessageMatcher {

	private byte[] data;
	private int position = 0;

	/**
	 * Create a new message reader.
	 * @param msg Message to read
	 */
	public MidiMessageReader(final MidiMessage msg) {
		this.data = msg.getData();
	}

	/**
	 * Read a single byte.
	 * @return The read byte
	 */
	public int readByte() {
		assertBytesLeft(1);
		return Byte.toUnsignedInt(data[position++]);
	}

	/**
	 * Read some bytes.
	 * @param bytesToRead Number of byets to read
	 * @return The read bytes
	 */
	public int[] readBytes(final int bytesToRead) {
		assertBytesLeft(bytesToRead);
		final int[] result = new int[bytesToRead];
		for (int i = 0; i < bytesToRead; i++) {
			result[i] = Byte.toUnsignedInt(data[position++]);
		}
		return result;
	}

	/**
	 * Read a 14-bit number by combining two 7-bit numbers. The
	 * most-significant-byte is read first and then the least-significant-byte.
	 * @return The read number
	 */
	public int read14BitNumber() {
		assertBytesLeft(2);
		int msb = Byte.toUnsignedInt(data[position++]);
		int lsb = Byte.toUnsignedInt(data[position++]);
        return (msb << 7) | lsb;
    }

	@Override
	public MidiMessageMatcher skipByte() {
		skipBytes(1);
		return this;
	}
	
	@Override
	public MidiMessageMatcher skipBytes(final int bytesToSkip) {
		assertBytesLeft(bytesToSkip);
		position += bytesToSkip;
		return this;
	}

	@Override
	public MidiMessageMatcher matchByte(final int expectedByte) {
		assertBytesLeft(1);
		final int readByte = Byte.toUnsignedInt(data[position++]);
		if (readByte != expectedByte) {
			throw new IllegalStateException("Expected byte " + expectedByte + ", got: " + readByte);
		}
		return this;
	}

	@Override
	public MidiMessageMatcher matchBytes(final int ... expectedBytes) {
		assertBytesLeft(expectedBytes.length);
		for (int expectedByte : expectedBytes) {
			final int readByte = Byte.toUnsignedInt(data[position++]);
			if (readByte != expectedByte) {
				throw new IllegalStateException("Expected byte " + expectedByte + ", got: " + readByte);
			}
		}
		return this;
	}

	@Override
	public MidiMessageMatcher match14BitNumber(final int expectedNumber) {
        if (expectedNumber < 0 || expectedNumber > 0x3fff) {
            throw new IllegalArgumentException("Expected a 14-bit number, got: " + expectedNumber);
        }
        final int readNumber = read14BitNumber();
        if (readNumber != expectedNumber) {
			throw new IllegalStateException("Expected number " + expectedNumber + ", got: " + readNumber);
        }
        return this;
	}
	
	@Override
	public void done() {
		if (position < data.length) {
			throw new IllegalStateException("Expected end of message");
		}
	}
	
	private void assertBytesLeft(final int expectedBytesLeft) {
		if (position + expectedBytesLeft > data.length) {
			throw new IllegalStateException("Unexpected end of message");
		}
	}
}
