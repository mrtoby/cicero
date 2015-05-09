package se.tanke.tcnova.midi;


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
}
