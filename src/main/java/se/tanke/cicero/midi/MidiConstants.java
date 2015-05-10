package se.tanke.cicero.midi;

/**
 * Class with nice to have constants when dealing with MIDI messages.
 */
public final class MidiConstants {

	/** Status byte for system exclusive messages. */
	public static final int SYSTEM_EXCLUSIVE = 0xf0;
	
	/** End byte for system exclusive messages. */
	public static final int SYSTEM_EXCLUSIVE_END = 0xf7;

	private MidiConstants() {
	}
}
