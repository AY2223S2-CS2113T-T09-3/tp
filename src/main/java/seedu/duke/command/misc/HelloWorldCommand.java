package seedu.duke.command.misc;

import seedu.duke.DukeSession;
import seedu.duke.command.ExecutableCommand;

/**
 * Represents the Easter egg "hello world" command.
 */
public class HelloWorldCommand extends ExecutableCommand {
    String name;

    public HelloWorldCommand(String name) {
        this.name = name;
    }

    public void execute(DukeSession dukeSession) {
        if (this.name == null) {
            dukeSession.getUi().printMessage("Hello, world!");
            return;
        }
        dukeSession.getUi().printMessage(String.format("Hello, %s!", this.name));
    }
}
