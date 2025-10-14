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
import seedu.address.model.finance.FinanceStatus;
import seedu.address.model.person.Person;

/**
 * Records a payment made by a student, reducing their outstanding finance amount.
 */
public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";
    public static final String MESSAGE_SUCCESS = "Payment of %1$s recorded for %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicate as student paid that amount.\n"
            + "Parameters: " + COMMAND_WORD
            + " INDEX (must be a positive integer) " + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_AMOUNT + "50";

    private final Index index;
    private final FinanceAmount payment;

    /**
     * Constructs a {@code PayCommand} to record a payment for the person at the given index.
     *
     * @param index index of the person in the current filtered person list (1-based as entered by the user).
     * @param payment amount to deduct from the person's outstanding finance.
    */
    public PayCommand(Index index, FinanceAmount payment) {
        this.index = index;
        this.payment = payment;
    }

    /**
     * Executes the pay command by deducting the payment from the target person's outstanding finance
     * and updating the model with the new finance state.
     *
     * @param model the model holding the filtered person list and supporting updates.
     * @return a {@link CommandResult} describing the successful payment recording.
     * @throws CommandException if the provided index is out of bounds of the filtered person list.
     * @throws NullPointerException if {@code model} is {@code null}.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        Person personToAddPayment = lastShownList.get(index.getZeroBased());
        //update Finance object for this person based on the payment
        model.setPerson(personToAddPayment, new Person(
                personToAddPayment.getName(), personToAddPayment.getPhone(), personToAddPayment.getEmail(),
                personToAddPayment.getAddress(), personToAddPayment.getTags(), personToAddPayment.getLesson(),
                Optional.ofNullable(updateFinance(personToAddPayment, payment)), personToAddPayment.getAttendance()
        ));
        return new CommandResult(String.format(MESSAGE_SUCCESS, payment, personToAddPayment.getName()));
    }

    /**
     * Produces a new {@link Finance} reflecting the deduction of the given payment from the person's
     * current outstanding amount. The finance type and status are preserved.
     *
     * Note: This method does not validate against negative results; callers should ensure the operation is valid.
     *
     * @param p the person whose finance is to be updated; must have a present finance.
     * @param payment the payment amount to deduct.
     * @return a new {@link Finance} instance with the updated outstanding amount.
     * @throws NullPointerException if any argument is {@code null}.
     * @throws java.util.NoSuchElementException if the person has no finance present.
     */
    public Finance updateFinance(Person p, FinanceAmount payment) {
        Finance oldFinance = p.getFinance().get();
        FinanceAmount newAmount = new FinanceAmount(oldFinance.getFinanceAmount().getAmount() - payment.getAmount());
        FinanceStatus newStatus = oldFinance.getStatus();
        if (newAmount.getAmount() == 0) {
            newStatus = FinanceStatus.PAID;
        }
        return new Finance(newAmount, oldFinance.getType(), newStatus);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PayCommand)) {
            return false;
        }
        PayCommand otherPayCommand = (PayCommand) other;
        return index.equals(otherPayCommand.index)
                && payment.equals(otherPayCommand.payment);
    }
}
