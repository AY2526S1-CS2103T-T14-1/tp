package seedu.address.model.finance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.commands.exceptions.CommandException;
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
    public void execute_withOwedAmount_reducesOwedAmount() {
        // person with owed amount can make payment to reduce it
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithOwedAmount = new PersonBuilder().withName("Alice").withFinance(
                new Finance(new FinanceAmount("100.00"))).build();
        model.addPerson(personWithOwedAmount);
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

    @Test
    public void execute_noOwedAmount_throwsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithZeroOwedAmount = new PersonBuilder().withName("Alice").withFinance(
                new Finance(new FinanceAmount("0"))).build();
        model.addPerson(personWithZeroOwedAmount);
        PayCommand payCommand = new PayCommand(Index.fromOneBased(1), new FinanceAmount("100.00"));
        CommandException exception = assertThrows(CommandException.class, () -> payCommand.execute(model));
        assertEquals(Messages.MESSAGE_NO_OWED_AMOUNT, exception.getMessage());
    }

    @Test
    public void execute_TooHighIndex_throwsCommandException() {
        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        Person personWithOwedAmount = new PersonBuilder().withName("Alice").withFinance(
                new Finance(new FinanceAmount("100.00"))).build();
        model.addPerson(personWithOwedAmount);
        PayCommand payCommand = new PayCommand(Index.fromOneBased(2), new FinanceAmount("100.00"));
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        CommandException exception = assertThrows(CommandException.class, () -> payCommand.execute(model));
        assertEquals(expectedMessage, exception.getMessage());
    }
}
