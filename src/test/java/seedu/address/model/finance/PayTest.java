package seedu.address.model.finance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
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
        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        Finance overdue = new Finance(new FinanceAmount("50.00"), FinanceType.PER_MONTH, FinanceStatus.OVERDUE);
        Person target = new PersonBuilder().withName("Alice Blue").withPhone("91231234")
                .withEmail("alice@example.com").withFinance(overdue).build();
        Person other = new PersonBuilder().withName("Bob Green").withPhone("92341234")
                .withEmail("bob@example.com").withFinance(
                        new Finance(new FinanceAmount("30.00"), FinanceType.PER_LESSON, FinanceStatus.UNPAID)).build();

        model.addPerson(target);
        model.addPerson(other);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Finance paidFinance = new Finance(overdue.getFinanceAmount(), overdue.getType(), FinanceStatus.PAID);
        Person paidTarget = new PersonBuilder(target).withFinance(paidFinance).build();
        expectedModel.setPerson(target, paidTarget);

        Index firstIndex = Index.fromOneBased(1);
        assertEquals(expectedModel, model);
    }

    @Test
    public void parse_indexWithExtraWhitespace_returnsPayCommand() {
        PayCommandParser parser = new PayCommandParser();
        String userInput = " 1 " + PREFIX_AMOUNT + "50.00";
        assertParseSuccess(parser, userInput, new PayCommand(Index.fromOneBased(1), new FinanceAmount("50.00")));
    }
}
