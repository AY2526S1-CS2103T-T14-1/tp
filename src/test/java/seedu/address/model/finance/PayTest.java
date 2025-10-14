package seedu.address.model.finance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.PayCommandParser;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PayTest {
    @Test
    public void parse_nonIntegerIndex_throwsParseException() {
        PayCommandParser parser = new PayCommandParser();
        assertParseFailure(parser, "one",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PayCommand.MESSAGE_USAGE));
    }

    @Test
    public void execute_withOverdueFinance_marksPersonAsPaid() {
        // even if the person has overdue finance, they can still be marked as paid
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithOverdueFinance = new PersonBuilder().withName("Alice").withFinance(
                new Finance(new FinanceAmount("100.00"), FinanceType.PER_MONTH, FinanceStatus.OVERDUE)).build();
        model.addPerson(personWithOverdueFinance);
        PayCommand payCommand = new PayCommand(Index.fromOneBased(1), new FinanceAmount("100.00"));
        String expectedMessage = String.format(PayCommand.MESSAGE_SUCCESS, "100.00", "Alice");
        CommandResult result = assertDoesNotThrow(() -> payCommand.execute(model));
        assertEquals(new CommandResult(expectedMessage), result);
    }

    @Test
    public void parse_indexWithExtraWhitespace_returnsPayCommand() {
        PayCommandParser parser = new PayCommandParser();
        String userInput = " 1 " + PREFIX_AMOUNT + "50.00";
        assertParseSuccess(parser, userInput, new PayCommand(Index.fromOneBased(1), new FinanceAmount("50.00")));
    }
}
