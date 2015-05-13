package se.tanke.cicero.midi;

/**
 * Message used to indicate that the matching/reading process of a midi message has failed.
 */
public class MidiMessageException extends Exception {

	private static final long serialVersionUID = 2208727837945516771L;

	/**
	 * Create a new exception.
	 * @param position Position in message
	 * @param msg Message
	 */
	public MidiMessageException(final int position, final String msg) {
		super("@" + position + ", " + msg);
	}
	
	/**
	 * Create a new exception.
	 * @param position Position in message
	 * @param msg Message
	 * @param cause Root cause
	 */
	public MidiMessageException(final int position, final String msg, final Throwable cause) {
		super("@" + position + ", " + msg, cause);
	}	
}
