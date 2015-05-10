package se.tanke.cicero.midi;


/** A representation of a midi message. */
public class MidiMessage {
	/**
	 * Create a new build for creating midi messages.
	 * @return A builder
	 */
	public static MidiMessageBuilder newBuilder() {
		return new MidiMessageBuilder();
	}

	private byte[] data;

	protected MidiMessage(final byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}
	
	/**
	 * Return a reader that can be used to inspect and read the contents of this
	 * message.
	 * @return A reader
	 */
	public MidiMessageReader reader() {
		return new MidiMessageReader(this);
	}
	
	/**
	 * Return a matcher that can be used to inspect the contents of this message.
	 * @return A matcher
	 */
	public MidiMessageMatcher matcher() {
		return new MidiMessageReader(this);
	}

	/**
	 * Check if this message is a valid sysex message.
	 * @return <code>true</code> if it is a valid sysex message
	 */
	public boolean isValidSysexMessage() {
		// TODO: Improve this
		return (data.length >= 2)
				&& (MidiConstants.SYSTEM_EXCLUSIVE == Byte.toUnsignedInt(data[0]))
				&& (MidiConstants.SYSTEM_EXCLUSIVE_END == Byte.toUnsignedInt(data[data.length - 1]));
	}
	
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		final String hexDigits = "0123456789abcdef";
		
		builder.append("<msg lenght=");
		builder.append(Integer.toString(data.length));
		builder.append(", data={ ");
		for (int i = 0; i < data.length; i++) {
			final int n = Byte.toUnsignedInt(data[i]);
			int highNibble = (n >> 4) & 0x0f;
			int lowNibble = n & 0x0f;
			builder.append("0x");
			builder.append(hexDigits.charAt(highNibble));
			builder.append(hexDigits.charAt(lowNibble));
			builder.append(", ");
		}
		builder.append("}>");
		
		return builder.toString();
	}
}
