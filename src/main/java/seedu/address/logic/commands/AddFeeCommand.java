package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.person.Person;

/**
 * Updates the outstanding amount owed by a student identified by index in the address book.
 */
public class AddFeeCommand extends Command {

    public static final String COMMAND_WORD = "addfee";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates the outstanding amount owed by a student.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_AMOUNT + "150";

    private static final String MESSAGE_SUCCESS =
            "Outstanding amount for %s updated to %s.";
    private static final String MESSAGE_INVALID_INDEX =
            "Error: Invalid student index.";

    private final Index index;
    private final FinanceAmount amount;

    /**
     * Creates an {@code AddFeeCommand} to update the outstanding amount for a student.
     *
     * @param index Index of the student in the displayed list.
     * @param amount The new outstanding amount.
     */
    public AddFeeCommand(Index index, FinanceAmount amount) {
        requireNonNull(index);
        requireNonNull(amount);
        this.index = index;
        this.amount = amount;
    }

    /**
     * Executes the {@code AddFeeCommand} by finding the student and updating their outstanding amount.
     *
     * @param model The model containing the student data.
     * @return A {@code CommandResult} indicating the outcome of the command.
     * @throws CommandException If the specified index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person student = lastShownList.get(index.getZeroBased());

        // Create or update Finance object
        Finance updatedFinance = student.getFinance()
                .orElse(new Finance())
                .add(amount);

        Person updatedPerson = new Person(
                student.getName(),
                student.getPhone(),
                student.getEmail(),
                student.getAddress(),
                student.getTags(),
                student.getLesson(),
                Optional.of(updatedFinance)
        );

        model.setPerson(student, updatedPerson);
        model.commitAddressBook();

        return new CommandResult(String.format(MESSAGE_SUCCESS,
                student.getName(), amount.toString()));
    }

    /**
     * Returns true if both {@code AddFeeCommand} objects have the same index and amount.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddFeeCommand)) {
            return false;
        }
        AddFeeCommand o = (AddFeeCommand) other;
        return index.equals(o.index)
                && amount.equals(o.amount);
    }
}
