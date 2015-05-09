package se.tanke.tcnova.midi;

/**
 * A matcher that can be used to check the contents of a midi message.
 */
public interface MidiMessageMatcher {

	/** 
	 * Skip a single byte.
	 * @return The matcher for chaining 
	 */
	MidiMessageMatcher skipByte();
	
	/**
	 * Skip some bytes.
	 * @param bytesToSkip Number of bytes to skip
	 * @return The matcher for chaining 
	 */
	MidiMessageMatcher skipBytes(final int bytesToSkip);

	/**
	 * Read a byte and verify that it matches the argument.
	 * @param expectedByte The expected value of the read byte
	 * @return The matcher for chaining 
	 */
	MidiMessageMatcher matchByte(final int expectedByte);
	
	/**
	 * Read some bytes and verify that they matches the argument.
	 * @param expectedBytes The expected values of the read bytes
	 * @return The matcher for chaining 
	 */
	MidiMessageMatcher matchBytes(final int ... expectedBytes);
	
	/**
	 * Read a 14-bit number and verify that it matches the argument.
	 * @param expectedNumber Expected 14-bit number
	 * @return The matcher for chaining
	 */
	MidiMessageMatcher match14BitNumber(final int expectedNumber);
	
	/** Verify that the end of the message has been reached. */
	void done();
}
