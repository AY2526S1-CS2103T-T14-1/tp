package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Unit tests for {@code AddFeeCommand}.
 */
public class AddFeeCommandTest {

    @Test
    public void execute_validIndex_updatesOutstandingAmount() throws CommandException {
        Person student = buildPerson("Alex Yeoh");
        List<Person> list = new ArrayList<>();
        list.add(student);

        ModelStubWithPersons model = new ModelStubWithPersons(list);
        AddFeeCommand command = new AddFeeCommand(Index.fromOneBased(1), new FinanceAmount("50"));

        CommandResult result = command.execute(model);
        assertTrue(result.getFeedbackToUser().contains("updated to 50"));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        ModelStubWithPersons model = new ModelStubWithPersons(new ArrayList<>());
        AddFeeCommand command = new AddFeeCommand(Index.fromOneBased(1), new FinanceAmount("50"));

        assertThrows(CommandException.class, () -> command.execute(model));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        AddFeeCommand cmd1 = new AddFeeCommand(Index.fromOneBased(1), new FinanceAmount("100"));
        AddFeeCommand cmd2 = new AddFeeCommand(Index.fromOneBased(1), new FinanceAmount("100"));
        assertTrue(cmd1.equals(cmd2));
    }

    @Test
    public void equals_differentValues_returnsFalse() {
        AddFeeCommand cmd1 = new AddFeeCommand(Index.fromOneBased(1), new FinanceAmount("100"));
        AddFeeCommand cmd2 = new AddFeeCommand(Index.fromOneBased(2), new FinanceAmount("50"));
        assertEquals(false, cmd1.equals(cmd2));
    }

    // ---------- Helpers ----------

    private static Person buildPerson(String name) {
        return new Person(
                new Name(name),
                new Phone("98765432"),
                new Email("alex@example.com"),
                new Address("123, Clementi Rd"),
                new java.util.HashSet<Tag>(),
                Optional.empty(),
                Optional.of(new Finance(new FinanceAmount("0.0")))
        );
    }

    /**
     * Minimal stub model for testing AddFeeCommand.
     */
    private static class ModelStubWithPersons implements Model {
        private final ObservableList<Person> persons;

        ModelStubWithPersons(List<Person> list) {
            this.persons = FXCollections.observableArrayList(list);
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return persons;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            int idx = persons.indexOf(target);
            if (idx >= 0) {
                persons.set(idx, editedPerson);
            }
        }

        @Override
        public void commitAddressBook() {
            // no-op for testing
        }

        // --- Unused methods for interface completeness ---
        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {}
        @Override
        public ReadOnlyAddressBook getAddressBook() { return null; }
        @Override
        public void addPerson(Person person) {}
        @Override
        public void deletePerson(Person target) {}
        @Override
        public boolean hasPerson(Person person) { return false; }
        @Override
        public void updateFilteredPersonList(java.util.function.Predicate<Person> predicate) {}
        @Override
        public java.nio.file.Path getAddressBookFilePath() { return null; }
        @Override
        public void setAddressBookFilePath(java.nio.file.Path path) {}
        @Override
        public seedu.address.commons.core.GuiSettings getGuiSettings() { return null; }
        @Override
        public void setGuiSettings(seedu.address.commons.core.GuiSettings guiSettings) {}
        @Override
        public seedu.address.model.ReadOnlyUserPrefs getUserPrefs() { return null; }
        @Override
        public void setUserPrefs(seedu.address.model.ReadOnlyUserPrefs userPrefs) {}
        @Override
        public Optional<Person> findPersonByName(String name) { return Optional.empty(); }
    }
}
