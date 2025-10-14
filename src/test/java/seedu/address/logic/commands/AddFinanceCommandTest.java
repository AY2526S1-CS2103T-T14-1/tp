package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddFinanceCommand.AddFinanceDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for AddFinanceCommand.
 */
public class AddFinanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addFinanceUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Finance financeToAdd = new Finance(new FinanceAmount("100.00"));

        // Calculate expected finance: add to existing or create new
        Finance expectedFinance = firstPerson.getFinance()
                .map(existing -> existing.add(financeToAdd.getOwedAmount()))
                .orElse(financeToAdd);
        Person editedPerson = new PersonBuilder(firstPerson).withFinance(expectedFinance).build();

        AddFinanceDescriptor descriptor = new AddFinanceDescriptor();
        descriptor.setFinance(financeToAdd);
        AddFinanceCommand addFinanceCommand = new AddFinanceCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddFinanceCommand.MESSAGE_ADD_FINANCE_SUCCESS,
                editedPerson.getName(), expectedFinance.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(addFinanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Finance financeToAdd = new Finance(new FinanceAmount("200.50"));

        // Calculate expected finance: add to existing or create new
        Finance expectedFinance = personInFilteredList.getFinance()
                .map(existing -> existing.add(financeToAdd.getOwedAmount()))
                .orElse(financeToAdd);
        Person editedPerson = new PersonBuilder(personInFilteredList).withFinance(expectedFinance).build();

        AddFinanceDescriptor descriptor = new AddFinanceDescriptor();
        descriptor.setFinance(financeToAdd);
        AddFinanceCommand addFinanceCommand = new AddFinanceCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddFinanceCommand.MESSAGE_ADD_FINANCE_SUCCESS,
                editedPerson.getName(), expectedFinance.toString());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addFinanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Finance finance = new Finance(new FinanceAmount("100.00"));
        AddFinanceDescriptor descriptor = new AddFinanceDescriptor();
        descriptor.setFinance(finance);
        AddFinanceCommand addFinanceCommand = new AddFinanceCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addFinanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        Finance finance = new Finance(new FinanceAmount("100.00"));
        AddFinanceDescriptor descriptor = new AddFinanceDescriptor();
        descriptor.setFinance(finance);
        AddFinanceCommand addFinanceCommand = new AddFinanceCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addFinanceCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Finance finance1 = new Finance(new FinanceAmount("100.00"));
        Finance finance2 = new Finance(new FinanceAmount("200.00"));

        AddFinanceDescriptor descriptor1 = new AddFinanceDescriptor();
        descriptor1.setFinance(finance1);
        AddFinanceDescriptor descriptor2 = new AddFinanceDescriptor();
        descriptor2.setFinance(finance2);

        final AddFinanceCommand standardCommand = new AddFinanceCommand(INDEX_FIRST_PERSON, descriptor1);

        // same values -> returns true
        AddFinanceDescriptor copyDescriptor = new AddFinanceDescriptor(descriptor1);
        AddFinanceCommand commandWithSameValues = new AddFinanceCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddFinanceCommand(INDEX_SECOND_PERSON, descriptor1)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddFinanceCommand(INDEX_FIRST_PERSON, descriptor2)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        Finance finance = new Finance(new FinanceAmount("100.00"));
        AddFinanceDescriptor descriptor = new AddFinanceDescriptor();
        descriptor.setFinance(finance);
        AddFinanceCommand addFinanceCommand = new AddFinanceCommand(index, descriptor);
        String expected = AddFinanceCommand.class.getCanonicalName() + "{index=" + index
                + ", addFinanceDescriptor=" + descriptor + "}";
        assertEquals(expected, addFinanceCommand.toString());
    }
}
