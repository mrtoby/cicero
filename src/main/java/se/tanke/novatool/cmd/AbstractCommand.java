package se.tanke.novatool.cmd;

import java.util.Optional;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

import se.tanke.novatool.Configuration;

/** An abstract class that can be used to create commands. */
public abstract class AbstractCommand {
    
    protected static final String INPUT_OPTION = "input";
    protected static final String OUTPUT_OPTION = "output";
    protected static final String SYSEX_ID_OPTION = "sysex";
    
    private String commandName = null;
    private Options options = new Options();
    private CommandLine line = null;
    
    protected void setCommandName(final String commandName) {
        this.commandName = commandName;
    }
    
    protected void setOptions(final Options options) {
        this.options = options;
    }
    
    protected CommandLine parse(final String[] args) {
        try {
            line = new PosixParser().parse(options, args);
        } catch (ParseException e) {
            die("Failed to parse command line", e);
        }
        
        final String[] nonOptionArgs = line.getArgs();
        if (nonOptionArgs.length != 0) {
            die("Unexpected non-option arguments");
        }
        return line;
    }
    
    /** Show usage. */
    protected void usage() {
        if (commandName != null && options != null) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar se.tanke.tcnova.Main " + commandName + " <options>",
                    "A tool for passing patches back-and-forth to the Nova System",
                    options,
                    "",
                    true);
            
        } else {
            System.out.println("java -jar se.tanke.tcnova.Main <command> <options>");
            System.out.println("Commands:");
            System.out.println("  list - List MIDI devices");
            System.out.println("  dump - Dump data from the Nova System");
        }
    }

    /**
     * Say no more.
     * @param msg Final message
     */
    protected void die(final String msg) {
        usage();
        System.err.println(msg);
        System.exit(1);
    }

    /**
     * Say no more.
     * @param msg Final message
     * @param t The last thing you saw
     */
    protected void die(final String msg, final Throwable t) {
        t.printStackTrace();
        die(msg);
    }

    @SuppressWarnings("static-access")
    protected static Option createInputOption(final boolean required) {
        return OptionBuilder.withLongOpt(INPUT_OPTION)
                .withDescription("The MIDI device used to receive data from the Nova System")
                .hasArg()
                .withArgName("device")
                .isRequired(required)
                .create('i');
    }
    
    @SuppressWarnings("static-access")
    protected static Option createOutputOption(final boolean required) {
        return OptionBuilder.withLongOpt(OUTPUT_OPTION)
                .withDescription("The MIDI device used to send data to the Nova System")
                .hasArg()
                .withArgName("device")
                .isRequired(required)
                .create('o');
    }

    @SuppressWarnings("static-access")
    protected static Option createSysExIdOption(final boolean required) {
        return OptionBuilder.withLongOpt(SYSEX_ID_OPTION)
                .withDescription("The MIDI SysEx ID to use with the Nova System")
                .hasArg()
                .withArgName("number")
                .isRequired(required)
                .create("s");
    }

    protected Optional<MidiDevice> getInput() {
        if (line == null) {
            throw new IllegalStateException("Command line has not been parsed yet");
        }
        if (!line.hasOption(INPUT_OPTION)) {
            return Optional.empty();
        }

        final MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        final int infoNumber = getIntOptionValue(INPUT_OPTION, 1, infos.length) - 1;
        MidiDevice device = null;
        try {
            device = MidiSystem.getMidiDevice(infos[infoNumber]);
        } catch (MidiUnavailableException e) {
            die("Failed to get input device", e);
        }
        try {
            Transmitter transmitter = device.getTransmitter();
            transmitter.close();
        } catch (MidiUnavailableException e) {
            device.close();
            die("Input device do not transmit MIDI-messages", e);
        }
        return Optional.of(device);
    }

    protected Optional<MidiDevice> getOutput() {
        if (line == null) {
            throw new IllegalStateException("Command line has not been parsed yet");
        }
        if (!line.hasOption(OUTPUT_OPTION)) {
            return Optional.empty();
        }

        final MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        final int infoNumber = getIntOptionValue(OUTPUT_OPTION, 1, infos.length) - 1;
        MidiDevice device = null;
        try {
            device = MidiSystem.getMidiDevice(infos[infoNumber]);
        } catch (MidiUnavailableException e) {
            die("Failed to get output device", e);
        }
        try {
            Receiver receiver = device.getReceiver();
            receiver.close();
        } catch (MidiUnavailableException e) {
            device.close();
            die("Output device do not receive MIDI-messages", e);
        }
        return Optional.of(device);
    }
    
    protected Configuration getConfiguration() {
        if (line == null) {
            throw new IllegalStateException("Command line has not been parsed yet");
        }
        final Configuration config = new Configuration();
        if (line.hasOption(SYSEX_ID_OPTION)) {
            config.setSysExId(getIntOptionValue(SYSEX_ID_OPTION, 0, 0x7f));
        }
        return config;
    }

    protected int getIntOptionValue(final String opt, final int minValue, final int maxValue) {
        final String str = line.getOptionValue(opt);
        int n = 0;
        try {
            n = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            die("Option '" + opt + "' is not a valid number: " + str);
        }
        if (n < minValue || n > maxValue) {
            die("Option '" + opt + "' is out of range: " + n);
        }
        return n;
    }
}
