package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class ViewOutstandingPaymentsCommandTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_withOutstandingPersons_showsFormattedOutstandingList() {
        // Prepare persons with owed amounts
        Finance owedAmount1 = new Finance(new FinanceAmount("120.50"));
        Finance owedAmount2 = new Finance(new FinanceAmount("30"));
        Finance owedAmount3 = new Finance(new FinanceAmount("45.00"));

        Person p1 = new PersonBuilder().withName("John Doe").withPhone("91234567")
                .withEmail("john@example.com").withFinance(owedAmount1).build();
        Person p2 = new PersonBuilder().withName("Jane Roe").withPhone("92345678")
                .withEmail("jane@example.com").withFinance(owedAmount2).build();
        Person p3 = new PersonBuilder().withName("Alex Poe").withPhone("93456789")
                .withEmail("alex@example.com").withFinance(owedAmount3).build();

        model.addPerson(p1);
        model.addPerson(p2);
        model.addPerson(p3);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        // Build expected output according to command's formatting
        StringBuilder expected = new StringBuilder();
        int count = 0;
        for (Person p : model.getFilteredPersonList()) {
            if (p.getFinance().isPresent() && p.getFinance().get().getOwedAmount().getAmount() > 0) {
                count++;
                expected.append(count).append(":").append("\n");
                expected.append(p.getName()).append("\n");
                expected.append(p.getEmail()).append("\n");
                expected.append(p.getPhone()).append("\n");
                expected.append(p.getFinance().get()).append("\n");
            }
        }

        assertCommandSuccess(new ViewOutstandingPaymentsCommand(), model, expected.toString(), expectedModel);
    }

    @Test
    public void execute_noOutstandingPersons_showsNoOutstandingMessage() {
        Finance noOwedAmount1 = new Finance(new FinanceAmount("0"));
        Finance noOwedAmount2 = new Finance(new FinanceAmount("0.00"));

        Person p1 = new PersonBuilder().withName("Sam Smith").withPhone("94561234")
                .withEmail("sam@example.com").withFinance(noOwedAmount1).build();
        Person p2 = new PersonBuilder().withName("Pat Kim").withPhone("95671234")
                .withEmail("pat@example.com").withFinance(noOwedAmount2).build();

        model.addPerson(p1);
        model.addPerson(p2);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = "No outstanding payments found.";

        assertCommandSuccess(new ViewOutstandingPaymentsCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyList_showsNoOutstandingMessage() {
        Model expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        String expectedMessage = "No outstanding payments found.";

        assertCommandSuccess(new ViewOutstandingPaymentsCommand(), model, expectedMessage, expectedModel);
    }
}
