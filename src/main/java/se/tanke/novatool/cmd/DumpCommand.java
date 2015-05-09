package se.tanke.novatool.cmd;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.SysexMessage;
import javax.sound.midi.Transmitter;

import org.apache.commons.cli.Options;

import se.tanke.novatool.Configuration;
import se.tanke.novatool.util.SingleMessageReceiver;
import se.tanke.novatool.util.SysexMessageRequests;

public class DumpCommand extends AbstractCommand {

    public static final String NAME = "dump";

    public void run(final String[] args) {
        final Options options = createOptions();

        setCommandName(NAME);
        setOptions(options);
        parse(args);

        final Configuration config = getConfiguration();

        try (MidiDevice input = getInput().get();
                Transmitter inputTransmitter = input.getTransmitter();
                MidiDevice output = getOutput().get();
                Receiver outputReceiver = output.getReceiver()) {

            SysexMessage requestCurrentPatch = SysexMessageRequests.createRequestCurrentPatchSysex(config);
            SingleMessageReceiver replyReceiver = new SingleMessageReceiver();

            inputTransmitter.setReceiver(replyReceiver);
            outputReceiver.send(requestCurrentPatch, -1);
            
            final boolean messageReceived = replyReceiver.waitForMessage(2000L);
            if (messageReceived) {
                System.out.println("Got reply!");
            } else {
                die("Timeout while waiting for reply");
            }
        } catch (MidiUnavailableException e) {
            die("Failed to send message", e);
        } catch (InterruptedException e) {
            die("Interrupted while waiting for reply", e);
        }
    }

    private static Options createOptions() {
        final Options options = new Options();

        options.addOption(AbstractCommand.createInputOption(true));
        options.addOption(AbstractCommand.createOutputOption(true));
        options.addOption(AbstractCommand.createSysExIdOption(false));

        return options;
    }
}
