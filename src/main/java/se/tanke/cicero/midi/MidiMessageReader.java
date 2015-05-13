package se.tanke.cicero.midi;

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
	 * @throws MidiMessageException If the the message is out of bytes 
	 */
	public int readByte() throws MidiMessageException {
		assertBytesLeft(1);
		return Byte.toUnsignedInt(data[position++]);
	}

	/**
	 * Read some bytes.
	 * @param bytesToRead Number of byets to read
	 * @return The read bytes
	 * @throws MidiMessageException If the the message is out of bytes 
	 */
	public int[] readBytes(final int bytesToRead) throws MidiMessageException {
		assertBytesLeft(bytesToRead);
		final int[] result = new int[bytesToRead];
		for (int i = 0; i < bytesToRead; i++) {
			result[i] = Byte.toUnsignedInt(data[position++]);
		}
		return result;
	}

	/**
	 * Read a 14-bit number by combining two 7-bit numbers in big endian byte order. The
	 * most-significant-byte is read first and then the least-significant-byte.
	 * @return The read number
	 * @throws MidiMessageException If the the message is out of bytes 
	 */
	public int read14BitNumberBE() throws MidiMessageException {
		assertBytesLeft(2);
		int msb = Byte.toUnsignedInt(data[position]);
		int lsb = Byte.toUnsignedInt(data[position + 1]);
		if (msb > 0x7f) {
			throw new MidiMessageException(position, "Number byte is more than 7-bits");
		}
		if (lsb > 0x7f) {
			throw new MidiMessageException(position + 1, "Number byte is more than 7-bits");
		}
		position += 2;
        return (msb << 7) | lsb;
    }

	/**
	 * Read a 14-bit number by combining two 7-bit numbers in little endian byte order. The
	 * least-significant-byte is read first and then the most-significant-byte.
	 * @return The read number
	 * @throws MidiMessageException If the the message is out of bytes 
	 */
	public int read14BitNumberLE() throws MidiMessageException {
		assertBytesLeft(2);
		int lsb = Byte.toUnsignedInt(data[position]);
		int msb = Byte.toUnsignedInt(data[position + 1]);
		if (lsb > 0x7f) {
			throw new MidiMessageException(position, "Number byte is more than 7-bits");
		}
		if (msb > 0x7f) {
			throw new MidiMessageException(position + 1, "Number byte is more than 7-bits");
		}
		position += 2;
        return (msb << 7) | lsb;
    }

	/**
	 * Read a fixed length string.
	 * @param numberOfChars Number of characters to read
	 * @return A string of the expected length
	 * @throws MidiMessageException If the the message is out of bytes 
	 */
	public String readString(final int numberOfChars) throws MidiMessageException {
		assertBytesLeft(numberOfChars);
		final StringBuilder builder = new StringBuilder();
		boolean collectingChars = true;
		for (int i = 0; i < numberOfChars; i++) {
			final int value = Byte.toUnsignedInt(data[position + i]);
			if (value > 0x7f) {
				throw new MidiMessageException(position + i, "Character use more than 7 bytes");
			}
			if (value == 0) {
				collectingChars = false;
			}
			if (collectingChars) {
				builder.append((char) value);
			}
		}
		position += numberOfChars;
		return builder.toString().trim();
	}

	@Override
	public MidiMessageMatcher skipByte() throws MidiMessageException {
		skipBytes(1);
		return this;
	}
	
	@Override
	public MidiMessageMatcher skipBytes(final int bytesToSkip) throws MidiMessageException {
		assertBytesLeft(bytesToSkip);
		position += bytesToSkip;
		return this;
	}

	@Override
	public MidiMessageMatcher matchByte(final int expectedByte) throws MidiMessageException {
		assertBytesLeft(1);
		final int readByte = Byte.toUnsignedInt(data[position++]);
		if (readByte != expectedByte) {
			throw new MidiMessageException(position - 1, "Expected byte " + expectedByte + ", got: " + readByte);
		}
		return this;
	}

	@Override
	public MidiMessageMatcher matchBytes(final int ... expectedBytes) throws MidiMessageException {
		assertBytesLeft(expectedBytes.length);
		for (int expectedByte : expectedBytes) {
			final int readByte = Byte.toUnsignedInt(data[position++]);
			if (readByte != expectedByte) {
				throw new MidiMessageException(position - 1, "Expected byte " + expectedByte + ", got: " + readByte);
			}
		}
		return this;
	}

	@Override
	public MidiMessageMatcher match14BitNumberBE(final int expectedNumber) throws MidiMessageException {
        if (expectedNumber < 0 || expectedNumber > 0x3fff) {
            throw new IllegalArgumentException("Argument should be a 14-bit BE number, got: " + expectedNumber);
        }
        final int readNumber = read14BitNumberBE();
        if (readNumber != expectedNumber) {
			throw new MidiMessageException(position - 2, "Expected number " + expectedNumber + ", got: " + readNumber);
        }
        return this;
	}
	
	@Override
	public MidiMessageMatcher match14BitNumberLE(final int expectedNumber) throws MidiMessageException {
        if (expectedNumber < 0 || expectedNumber > 0x3fff) {
            throw new IllegalArgumentException("Argument should be a 14-bit LE number, got: " + expectedNumber);
        }
        final int readNumber = read14BitNumberLE();
        if (readNumber != expectedNumber) {
			throw new MidiMessageException(position - 2, "Expected number " + expectedNumber + ", got: " + readNumber);
        }
        return this;
	}
	
	@Override
	public void done() throws MidiMessageException {
		if (position < data.length) {
			throw new MidiMessageException(position, "Expected end of message");
		}
	}
	
	private void assertBytesLeft(final int expectedBytesLeft) throws MidiMessageException {
		if (position + expectedBytesLeft > data.length) {
			throw new MidiMessageException(position, "Unexpected end of message, expected " + expectedBytesLeft + " bytes more");
		}
	}
}
