package se.tanke.cicero;

import java.io.File;
import java.io.IOException;
import java.util.List;

import se.tanke.cicero.midi.MidiMessage;
import se.tanke.cicero.midi.SysexFile;

/**
 * This is a sysex librarian tool for managing patches of some kind.
 * 
 * The name is inspired from the wikipedia page on librarian: "Many of these
 * aristocrats, such as Cicero, kept the contents of their private libraries to
 * themselves, only boasting of the enormity of his collection.". Not a perfect fit
 * of course, but least it is related and it sounds cultural.
 */
public class Cicero {

	public static void main(final String[] args) throws IOException {
		if (args.length != 1) {
			throw new RuntimeException("Expect a filename as argument");
		}
		final List<MidiMessage> result = SysexFile.read(new File(args[0]));
		for (MidiMessage msg : result) {
			//final Patch patch = Patch.fromMessage(msg);
			System.out.println(msg.toString());
		}
	}
}
