package seedu.duke.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommandArgumentsTest {
    @Test
    public void parseEmpty() {
        CommandArguments args = new CommandArguments(new CommandTokens(""));
        assertNull(args.getPositionalArgument());
    }

    @Test
    public void parseArgumentsPositionalOnlyOneWord() {
        CommandArguments args = new CommandArguments(new CommandTokens("test"));
        assertEquals(args.getPositionalArgument(), "test");
    }
    @Test
    public void parseArgumentsPositionalOnlyMultipleWords() {
        CommandArguments args = new CommandArguments(new CommandTokens("test test2"));
        assertEquals(args.getPositionalArgument(), "test test2");
    }

    @Test
    public void parseFlagArgumentOnlyOneWord() {
        CommandArguments args = new CommandArguments(new CommandTokens("/flag test"));
        assertEquals(args.getFlagArgument("flag"), "test");
    }

    @Test
    public void parseFlagArgumentOnlyMultipleWords() {
        CommandArguments args = new CommandArguments(new CommandTokens("/flag test test2"));
        assertEquals(args.getFlagArgument("flag"), "test test2");
    }

    @Test
    public void parseFlagArgumentsOnlyMultipleWords() {
        CommandArguments args = new CommandArguments(new CommandTokens("/flag test test2 /flag2 test3"));
        assertEquals(args.getFlagArgument("flag"), "test test2");
        assertEquals(args.getFlagArgument("flag2"), "test3");
    }

    @Test
    public void parseMixedArguments() {
        CommandArguments args = new CommandArguments(new CommandTokens("testArg /flag test test2 /flag2 test3"));
        assertEquals(args.getPositionalArgument(), "testArg");
        assertEquals(args.getFlagArgument("flag"), "test test2");
        assertEquals(args.getFlagArgument("flag2"), "test3");
    }
}
