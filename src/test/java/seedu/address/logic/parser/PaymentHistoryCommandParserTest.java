//Generated with AI-Assistance (ChatGPT)
package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PaymentHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests for {@link PaymentHistoryCommandParser}.
 */
public class PaymentHistoryCommandParserTest {

    private final PaymentHistoryCommandParser parser = new PaymentHistoryCommandParser();

    @Test
    public void parse_empty_success() throws ParseException {
        PaymentHistoryCommand cmd = parser.parse("");
        assertNotNull(cmd);
        assertTrue(cmd instanceof PaymentHistoryCommand);
    }

    @Test
    public void parse_whitespace_success() throws ParseException {
        PaymentHistoryCommand cmd = parser.parse("   \t  ");
        assertNotNull(cmd);
        assertTrue(cmd instanceof PaymentHistoryCommand);
    }

    @Test
    public void parse_ignoresExtraWhitespace_success() throws ParseException {
        PaymentHistoryCommand cmd = parser.parse("   ");
        assertNotNull(cmd);
        assertTrue(cmd instanceof PaymentHistoryCommand);
    }
}
