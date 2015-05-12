package se.tanke.cicero;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import se.tanke.cicero.midi.MidiMessage;
import se.tanke.cicero.midi.SysexFile;
import se.tanke.cicero.tc.TCNovaSystem;

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
		final DeviceTypeRegistry registry = new DeviceTypeRegistry();
		registry.register(new TCNovaSystem());
		
		if (args.length != 1) {
			throw new RuntimeException("Expect a filename as argument");
		}
		final List<MidiMessage> messages = SysexFile.read(new File(args[0]));
		
		if (!messages.isEmpty()) {
			final DeviceType type = getDeviceTypeFor(registry, messages.get(0));
			
			for (MidiMessage msg : messages) {
				final Optional<Patch> patch = type.decodePatch(msg);
				System.out.println(patch.toString());
			}
		}
	}

	private static DeviceType getDeviceTypeFor(final DeviceTypeRegistry registry, final MidiMessage firstMessage) {
		final List<DeviceType> deviceTypes = registry.findByPatchMessage(firstMessage);
		if (deviceTypes.size() == 0) {
			throw new RuntimeException("No device type recognize the first message");
		}
		if (deviceTypes.size() > 1) {
			throw new RuntimeException("More than one device type recognize the first message");
		}
		return deviceTypes.get(0);
	}
}
