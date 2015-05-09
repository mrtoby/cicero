package se.tanke.tcnova.midi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/** A builder class for creating midi messages. */
public class MidiMessageBuilder {
	
	private ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	
	/**
	 * Append a single byte of data to the end of the message.
	 * @param data A byte
	 * @return Builder for chaining
	 */
	public MidiMessageBuilder append(final int data) {
		bytes.write(data);
		return this;
	}
	
	/**
	 * Append a single byte of data to the end of the message.
	 * @param data A byte
	 * @return Builder for chaining
	 */
	public MidiMessageBuilder append(final byte data) {
		bytes.write(data);
		return this;
	}
	
	/**
	 * Append some bytes to the end of the message.
	 * @param data Some bytes
	 * @return Builder for chaining
	 */
	public MidiMessageBuilder append(final int ... data) {
		for (int d : data) {
			bytes.write(d);
		}
		return this;
	}
	
	/**
	 * Append some bytes to the end of the message.
	 * @param data Some bytes
	 * @return Builder for chaining
	 */
	public MidiMessageBuilder append(final byte ... data) {
		try {
			bytes.write(data);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return this;
	}

	/**
	 * Append a 14-bit number as two 7-bit numbers. The
	 * most-significant-byte is appended before the least-significant-byte.
	 * @param number A 14-bit number
	 * @return Builder for chaining
	 */
	public MidiMessageBuilder append14BitNumber(final int number) {
        if (number < 0 || number > 0x3fff) {
            throw new IllegalArgumentException("Expected a 14-bit number, got: " + number);
        }
        append((number >> 7) & 0x7f); // MSB
        append(number & 0x7f); // LSB
        return this;
    }    

	/**
	 * Build a midi message.
	 * @return The midi message
	 */
	public MidiMessage build() {
		final byte[] data = bytes.toByteArray();
		
		// TODO Verify that the MIDI data is ok
		
		return new MidiMessage(data);
	}
}
