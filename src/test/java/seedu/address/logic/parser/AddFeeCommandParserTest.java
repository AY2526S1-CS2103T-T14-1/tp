package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddFeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.FinanceType;

public class AddFeeCommandParserTest {

    private final AddFeeCommandParser parser = new AddFeeCommandParser();

    @Test
    public void parse_validArgs_returnsAddFeeCommand() throws Exception {
        AddFeeCommand command = parser.parse(" s/Alice t/lesson a/50");
        assertEquals(new AddFeeCommand("Alice", FinanceType.PER_LESSON, new FinanceAmount("50")), command);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" s/Alice t/lesson"));
    }

    @Test
    public void parse_invalidType_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse(" s/Alice t/weekly a/50"));
    }
}
