package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.FinanceType;
import seedu.address.model.person.Person;

/**
 * Contains integration tests for {@code AddFeeCommand}.
 */
public class AddFeeCommandTest {

    @Test
    public void execute_studentNotFound_throwsCommandException() {
        ModelStub modelStub = new ModelStub();
        AddFeeCommand command = new AddFeeCommand(
                "Nonexistent", FinanceType.PER_LESSON, new FinanceAmount("50"));

        assertThrows(CommandException.class,
                String.format(
                        "Error: No student found with the name \"%s\".",
                        "Nonexistent"
                ),
                () -> command.execute(modelStub)
        );
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AddFeeCommand first = new AddFeeCommand(
                "Alice", FinanceType.PER_LESSON, new FinanceAmount("50"));
        AddFeeCommand copy = new AddFeeCommand(
                "Alice", FinanceType.PER_LESSON, new FinanceAmount("50"));
        assertEquals(first, copy);
    }

    /**
     * Minimal ModelStub for testing AddFeeCommand.
     */
    private static class ModelStub implements Model {
        @Override
        public Optional<Person> findPersonByName(String name) {
            return Optional.empty();
        }

        // All other methods throw AssertionError — not needed for this test
        @Override
        public void setUserPrefs(seedu.address.model.ReadOnlyUserPrefs userPrefs) {
        }

        @Override
        public seedu.address.model.ReadOnlyUserPrefs getUserPrefs() {
            return null;
        }

        @Override
        public seedu.address.commons.core.GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            return null;
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path path) {
        }

        @Override
        public void setAddressBook(seedu.address.model.ReadOnlyAddressBook ab) {
        }

        @Override
        public seedu.address.model.ReadOnlyAddressBook getAddressBook() {
            return null;
        }

        @Override
        public boolean hasPerson(Person p) {
            return false;
        }

        @Override
        public void deletePerson(Person target) {
        }

        @Override
        public void addPerson(Person person) {
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }

        @Override
        public javafx.collections.ObservableList<Person> getFilteredPersonList() {
            return null;
        }

        @Override
        public void updateFilteredPersonList(java.util.function.Predicate<Person> predicate) {
        }
    }
}
