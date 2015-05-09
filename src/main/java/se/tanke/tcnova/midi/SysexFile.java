package se.tanke.tcnova.midi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * This class can read and write sysex files.
 * 
 * Sysex files often have the suffix .syx. It is simply a sequence of one or
 * more sequences of bytes that start with a system exlusive start byte and ends
 * with a system exclusive end byte.
 */
public final class SysexFile {
	
	/**
	 * Method that can be used to test the file methods.
	 * @param args Two filenames, the first is read from and the second is written to
	 * @throws IOException If something goes wrong
	 */
	public static void main(final String[] args) throws IOException {
		if (args.length != 2) {
			throw new RuntimeException("Expect two filenames as arguments, the first is read from and the second written to");
		}
		List<MidiMessage> result = SysexFile.read(new File(args[0]));
		SysexFile.write(new File(args[1]), result);
	}
	
	/**
	 * Read sysex midi messages from a file.
	 * @param file The file to read
	 * @return A list of all sysex messages in order
	 * @throws IOException If there is some problem reading the file
	 */
	public static List<MidiMessage> read(final File file) throws IOException {
		final FileInputStream input = new FileInputStream(file);
		final List<MidiMessage> result = read(input);
		input.close();
		return result;
	}

	/**
	 * Read sysex midi messages from an input stream.
	 * @param inputStream Input stream to read sysex messages from
	 * @return A list of all sysex messages in order
	 * @throws IOException If there is some problem reading the file
	 */
	public static List<MidiMessage> read(final InputStream inputStream) throws IOException {
		List<MidiMessage> result = new ArrayList<>();
		int position = 0;
		
		while (inputStream.available() > 0) {
			int sysexStartByte = inputStream.read();
			if (sysexStartByte != MidiConstants.SYSTEM_EXCLUSIVE) {
				throw new IOException("Expected sysex start byte at position: " + position);
			}
			position++;
			
			final MidiMessageBuilder builder = new MidiMessageBuilder();
			builder.append(sysexStartByte);
			
			int readByte = -1; 
			do {
				readByte = inputStream.read();
				if (readByte == -1) {
					throw new IOException("Unexpected EOF while reading sysex");
				}
				position++;
				builder.append(readByte);
			} while (readByte != MidiConstants.SYSTEM_EXCLUSIVE_END);
			
			result.add(builder.build());
		}
		return result;
	}
	
	/**
	 * Write sysex midi messages to a file.
	 * @param file The file to write to
	 * @param messages A list of one or more sysex messages to write
	 * @throws IOException If there is some problem writing the file
	 */
	public static void write(final File file, final List<MidiMessage> messages) throws IOException {
		final FileOutputStream output = new FileOutputStream(file);
		write(output, messages);
		output.close();
	}

	/**
	 * Write sysex midi messages to an output stream.
	 * @param outputStream The output stream to write to
	 * @param messages A list of one or more sysex messages to write
	 * @throws IOException If there is some problem writing the file
	 */
	public static void write(final OutputStream outputStream, final List<MidiMessage> messages) throws IOException {
		for (MidiMessage msg : messages) {
			if (!msg.isValidSysexMessage()) {
				throw new IOException("Message is not a valid sysex message: " + msg);
			}
			outputStream.write(msg.getData());
		}
	}
	
	private SysexFile() {
	}
}
