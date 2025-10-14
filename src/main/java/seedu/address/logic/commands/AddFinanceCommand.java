package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds finance information to an existing person in the address book.
 */
public class AddFinanceCommand extends Command {

    public static final String COMMAND_WORD = "addfinance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an owed amount to the person identified "
            + "by the index number used in the displayed person list. "
            + "The amount will be added to any existing owed amount.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "100.00";

    public static final String MESSAGE_ADD_FINANCE_SUCCESS = "Added Finance to %1$s: %2$s";

    private final Index index;
    private final AddFinanceDescriptor addFinanceDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param addFinanceDescriptor details to add finance with
     */
    public AddFinanceCommand(Index index, AddFinanceDescriptor addFinanceDescriptor) {
        requireNonNull(index);
        requireNonNull(addFinanceDescriptor);

        this.index = index;
        this.addFinanceDescriptor = new AddFinanceDescriptor(addFinanceDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddFinance = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToAddFinance, addFinanceDescriptor);

        model.setPerson(personToAddFinance, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_FINANCE_SUCCESS, editedPerson.getName(),
                editedPerson.getFinance().map(Finance::toString).orElse("")));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddFinance}
     * edited with {@code addFinanceDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, AddFinanceDescriptor addFinanceDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        Optional<Lesson> updatedLesson = personToEdit.getLesson();

        // Add the new amount to existing owed amount, or create new Finance if none exists
        Optional<Finance> updatedFinance;
        if (personToEdit.getFinance().isPresent()) {
            Finance existingFinance = personToEdit.getFinance().get();
            updatedFinance = Optional.of(existingFinance.add(addFinanceDescriptor.getFinance().getOwedAmount()));
        } else {
            updatedFinance = Optional.of(addFinanceDescriptor.getFinance());
        }

        Optional<AttendanceStatus> updatedAttendance = personToEdit.getAttendance();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedLesson,
                updatedFinance, updatedAttendance);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddFinanceCommand otherAddFinanceCommand)) {
            return false;
        }

        return index.equals(otherAddFinanceCommand.index)
                && addFinanceDescriptor.equals(otherAddFinanceCommand.addFinanceDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("addFinanceDescriptor", addFinanceDescriptor)
                .toString();
    }

    /**
     * Stores the details to add finance.
     */
    public static class AddFinanceDescriptor {
        private Finance finance;

        public AddFinanceDescriptor() {}

        /**
         * Copy constructor.
         */
        public AddFinanceDescriptor(AddFinanceDescriptor toCopy) {
            setFinance(toCopy.finance);
        }

        public void setFinance(Finance finance) {
            this.finance = finance;
        }

        public Finance getFinance() {
            return finance;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddFinanceDescriptor)) {
                return false;
            }

            AddFinanceDescriptor otherAddFinanceDescriptor = (AddFinanceDescriptor) other;
            return Objects.equals(finance, otherAddFinanceDescriptor.finance);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("finance", finance)
                    .toString();
        }
    }
}
