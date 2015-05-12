package se.tanke.cicero.midi;

/**
 * A matcher that can be used to check the contents of a midi message.
 */
public interface MidiMessageMatcher {

	/** 
	 * Skip a single byte.
	 * @return The matcher for chaining
	 * @throws MidiMessageException If the the message is out of bytes 
	 */
	MidiMessageMatcher skipByte() throws MidiMessageException;
	
	/**
	 * Skip some bytes.
	 * @param bytesToSkip Number of bytes to skip
	 * @return The matcher for chaining 
	 * @throws MidiMessageException If the the message is out of bytes 
	 */
	MidiMessageMatcher skipBytes(final int bytesToSkip) throws MidiMessageException;

	/**
	 * Read a byte and verify that it matches the argument.
	 * @param expectedByte The expected value of the read byte
	 * @return The matcher for chaining 
	 * @throws MidiMessageException If the the message is out of bytes or different than expected 
	 */
	MidiMessageMatcher matchByte(final int expectedByte) throws MidiMessageException;
	
	/**
	 * Read some bytes and verify that they matches the argument.
	 * @param expectedBytes The expected values of the read bytes
	 * @return The matcher for chaining 
	 * @throws MidiMessageException If the the message is out of bytes or different than expected 
	 */
	MidiMessageMatcher matchBytes(final int ... expectedBytes) throws MidiMessageException;
	
	/**
	 * Read a 14-bit number in big endian byte order and verify that it matches the argument.
	 * @param expectedNumber Expected 14-bit number
	 * @return The matcher for chaining
	 * @throws MidiMessageException If the the message is out of bytes or different than expected 
	 */
	MidiMessageMatcher match14BitNumberBE(final int expectedNumber) throws MidiMessageException;
	
	/**
	 * Read a 14-bit number in little endian byte order and verify that it matches the argument.
	 * @param expectedNumber Expected 14-bit number
	 * @return The matcher for chaining
	 * @throws MidiMessageException If the the message is out of bytes or different than expected 
	 */
	MidiMessageMatcher match14BitNumberLE(final int expectedNumber) throws MidiMessageException;
	
	/** 
	 * Verify that the end of the message has been reached. 
	 * @throws MidiMessageException If the the message has bytes left 
	 */
	void done() throws MidiMessageException;
}
