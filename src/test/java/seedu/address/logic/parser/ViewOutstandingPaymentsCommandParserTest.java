package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewOutstandingPaymentsCommand;

public class ViewOutstandingPaymentsCommandParserTest {

    private final ViewOutstandingPaymentsCommandParser parser = new ViewOutstandingPaymentsCommandParser();

    @Test
    public void parse_emptyArgs_success() {
        assertParseSuccess(parser, "", new ViewOutstandingPaymentsCommand());
        assertParseSuccess(parser, "   \t  \n  ", new ViewOutstandingPaymentsCommand());
    }

    @Test
    public void parse_nonEmptyArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewOutstandingPaymentsCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "extra", expectedMessage);
        assertParseFailure(parser, "  extra tokens  ", expectedMessage);
        assertParseFailure(parser, "--flag", expectedMessage);
    }
}
