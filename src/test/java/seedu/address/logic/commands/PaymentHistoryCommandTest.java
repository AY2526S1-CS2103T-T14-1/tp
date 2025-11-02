//Generated with AI-Assistance (ChatGPT)
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
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
 * Simple tests for {@link PaymentHistoryCommand}.
 */
public class PaymentHistoryCommandTest {

    @Test
    public void execute_noPayments_showsEmpty() throws CommandException {
        Model model = new ModelStubWithPersons(new ArrayList<>());
        PaymentHistoryCommand cmd = new PaymentHistoryCommand();

        CommandResult res = cmd.execute(model);
        String text = res.getFeedbackToUser();

        // Any non-null feedback is acceptable; most important is no popup
        assertTrue(text != null && text.trim().length() > 0);
        assertFalse(res.isShowPopup());
    }

    @Test
    public void execute_withPayments_showsTotals() throws CommandException {
        // Alice: two payments 50 + 20
        Person alice = personNamed("Alice Pauline");
        Finance fAlice = new Finance(new FinanceAmount(150.00));
        fAlice = fAlice.pay(new FinanceAmount(50.00));
        fAlice = fAlice.pay(new FinanceAmount(20.00));
        alice = withFinance(alice, fAlice);

        // Benson: one payment 30
        Person benson = personNamed("Benson Meier");
        Finance fBenson = new Finance(new FinanceAmount(200.00));
        fBenson = fBenson.pay(new FinanceAmount(30.00));
        benson = withFinance(benson, fBenson);

        List<Person> list = new ArrayList<>();
        list.add(alice);
        list.add(benson);

        Model model = new ModelStubWithPersons(list);
        PaymentHistoryCommand cmd = new PaymentHistoryCommand();

        CommandResult res = cmd.execute(model);
        String out = res.getFeedbackToUser();

        // names present
        assertTrue(out.contains("Alice Pauline"));
        assertTrue(out.contains("Benson Meier"));

        // amounts present (formatted or not)
        assertTrue(out.contains("50") || out.contains("50.00"));
        assertTrue(out.contains("20") || out.contains("20.00"));
        assertTrue(out.contains("30") || out.contains("30.00"));

        // total present (exact decimals optional)
        assertTrue(out.contains("Total paid:"));
        assertTrue(out.contains("100") || out.contains("100.00"));

        // non-empty result should open the popup
        assertTrue(res.isShowPopup());
        int indexBenson = out.indexOf("Benson Meier");
        int indexAlice50 = out.indexOf("Alice Pauline", indexBenson);
        int indexAlice20 = out.indexOf("Alice Pauline", indexAlice50 + 1);
    }

    // -------- helpers --------

    private static Person personNamed(String name) {
        return new Person(
                new Name(name),
                new Phone("12345678"),
                new Email("a@b.com"),
                new Address("Somewhere"),
                new HashSet<Tag>(),
                java.util.Optional.empty(),
                java.util.Optional.empty()
        );
    }

    private static Person withFinance(Person p, Finance f) {
        return new Person(
                p.getName(),
                p.getPhone(),
                p.getEmail(),
                p.getAddress(),
                p.getTags(),
                p.getLesson(),
                java.util.Optional.of(f)
        );
    }

    /**
     * Minimal model stub: only the filtered person list is used by the command.
     */
    private static class ModelStubWithPersons implements Model {
        private final ObservableList<Person> list;

        ModelStubWithPersons(List<Person> people) {
            this.list = FXCollections.observableArrayList(people);
        }

        // used by the command
        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return list;
        }

        // user prefs required by this repo's Model
        @Override
        public void setUserPrefs(seedu.address.model.ReadOnlyUserPrefs userPrefs) {
        }

        @Override
        public seedu.address.model.ReadOnlyUserPrefs getUserPrefs() {
            return new seedu.address.model.UserPrefs();
        }

        @Override
        public Optional<Person> findPersonByName(String name) {
            for (Person p : list) { // 'list' is your ObservableList<Person>
                if (p.getName().toString().equals(name)) {
                    return Optional.of(p);
                }
            }
            return Optional.empty();
        }

        // ---- Unused methods below: simple stubs to satisfy the interface ----
        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new ReadOnlyAddressBook() {
                @Override
                public ObservableList<Person> getPersonList() {
                    return list;
                }
            };
        }

        @Override
        public void addPerson(Person person) {
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
        }

        @Override
        public void deletePerson(Person target) {
        }

        @Override
        public boolean hasPerson(Person person) {
            return false;
        }

        @Override
        public void updateFilteredPersonList(java.util.function.Predicate<Person> predicate) {
        }

        @Override
        public java.nio.file.Path getAddressBookFilePath() {
            return null;
        }

        @Override
        public void setAddressBookFilePath(java.nio.file.Path addressBookFilePath) {
        }

        @Override
        public GuiSettings getGuiSettings() {
            return null;
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
        }

        @Override
        public void commitAddressBook() {}
    }
}
