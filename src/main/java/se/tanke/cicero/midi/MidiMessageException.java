package se.tanke.cicero.midi;

/**
 * Message used to indicate that the matching/reading process of a midi message has failed.
 */
public class NotMatchingMessageException extends Exception {

	private static final long serialVersionUID = 2208727837945516771L;

	/**
	 * Create a new exception.
	 * @param msg Message
	 */
	public NotMatchingMessageException(final String msg) {
		super(msg);
	}
	
	/**
	 * Create a new exception.
	 * @param msg Message
	 * @param cause Root cause
	 */
	public NotMatchingMessageException(final String msg, final Throwable cause) {
		super(msg, cause);
	}	
}
