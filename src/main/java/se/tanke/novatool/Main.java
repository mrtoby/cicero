package se.tanke.novatool;

import java.util.Arrays;

import se.tanke.novatool.cmd.AbstractCommand;
import se.tanke.novatool.cmd.DumpCommand;
import se.tanke.novatool.cmd.ListCommand;

/**
 * Main class that implement the command thingie.
 */
public final class Main extends AbstractCommand {

    private Main() {
    }
    
    /**
     * Main method.
     * @param args Arguments
     */
    public static void main(final String[] args) {
        new Main().run(args);
    }
    
    public void run(final String[] args) {
        if (args.length == 0) {
            die("No command specified");
        }
        final String command = args[0];
        final String[] argsAfterCommand = Arrays.copyOfRange(args, 1, args.length);
        
        if (command.equalsIgnoreCase(ListCommand.NAME)) {
            new ListCommand().run(argsAfterCommand);
        } else if (command.equalsIgnoreCase(DumpCommand.NAME)) {
            new DumpCommand().run(argsAfterCommand);
        } else {
            die("Unknown command: " + command.toLowerCase());
        }
    }
}
