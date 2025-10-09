//Generated with AI-Assistance (ChatGPT)
package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class ScheduleCommandParserTest {

    private final ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_noArgs_success() {
        assertDoesNotThrow(() -> parser.parse(""));
        assertDoesNotThrow(() -> parser.parse("   "));
    }

    @Test
    public void parse_extraArgs_failure() {
        assertThrows(ParseException.class, () -> parser.parse(" x"));
    }
}

