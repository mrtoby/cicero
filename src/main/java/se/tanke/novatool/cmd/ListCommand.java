package se.tanke.novatool.cmd;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;

import org.apache.commons.cli.Options;

/**
 * Command for listing midi devices.
 */
public final class ListCommand extends AbstractCommand {

    public static final String NAME = "list";

    public void run(final String[] args) {
        setCommandName(NAME);
        
        parse(args);
        
        final MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        System.out.println("Available MIDI devices:");
        int number = 1;
        for (MidiDevice.Info info : infos) {
            System.out.println("  [" + number + "] " + info);
            number++;
        }
    }
}
