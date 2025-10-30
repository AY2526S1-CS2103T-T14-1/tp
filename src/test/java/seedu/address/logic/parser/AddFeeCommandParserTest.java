package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.FinanceAmount;

/**
 * Unit tests for {@code AddFeeCommandParser}.
 */
public class AddFeeCommandParserTest {

    private final AddFeeCommandParser parser = new AddFeeCommandParser();

    @Test
    public void parse_validArgs_returnsAddFeeCommand() throws Exception {
        AddFeeCommand command = parser.parse("1 amt/50");
        assertEquals(new AddFeeCommand(Index.fromOneBased(1), new FinanceAmount("50")), command);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        // Missing amount parameter
        assertThrows(ParseException.class, () -> parser.parse("1"));
    }

    @Test
    public void parse_invalidAmount_throwsParseException() {
        // Invalid amount (non-numeric)
        assertThrows(ParseException.class, () -> parser.parse("1 amt/abc"));
    }

    @Test
    public void parse_invalidIndex_throwsParseException() {
        // Invalid index (non-integer)
        assertThrows(ParseException.class, () -> parser.parse("one amt/50"));
    }

    @Test
    public void parse_negativeAmount_throwsParseException() {
        // Negative amount
        assertThrows(ParseException.class, () -> parser.parse("1 amt/-10"));
    }
}
