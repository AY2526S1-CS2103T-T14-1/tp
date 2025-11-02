package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.person.Person;

public class AddFeeCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_amountExceedsLimit_throwsCommandException() {
        // Set up a person with an existing high balance
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Finance highBalance = new Finance(new FinanceAmount(999999.99));
        Person personWithHighBalance = new Person(
                firstPerson.getName(),
                firstPerson.getPhone(),
                firstPerson.getEmail(),
                firstPerson.getAddress(),
                firstPerson.getTags(),
                firstPerson.getLesson(),
                Optional.of(highBalance)
        );
        model.setPerson(firstPerson, personWithHighBalance);

        // Try to add an amount that would exceed the limit
        FinanceAmount amountToAdd = new FinanceAmount(10.00);
        AddFeeCommand addFeeCommand = new AddFeeCommand(INDEX_FIRST_PERSON, amountToAdd);

        // Should throw CommandException with appropriate message
        assertThrows(CommandException.class, () -> addFeeCommand.execute(model));
    }

    @Test
    public void execute_validAmount_success() throws Exception {
        // Add a normal amount that won't exceed the limit
        FinanceAmount amount = new FinanceAmount(100.00);
        AddFeeCommand addFeeCommand = new AddFeeCommand(INDEX_FIRST_PERSON, amount);

        // Should execute successfully without throwing exception
        addFeeCommand.execute(model);
    }
}
