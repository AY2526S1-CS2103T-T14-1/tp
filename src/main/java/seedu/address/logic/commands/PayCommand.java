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

public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";
    public static final String MESSAGE_SUCCESS = "Payment of %1$s recorded for %2$s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Indicate as student paid that amount.\n"
            + "Parameters: " + COMMAND_WORD
            + " INDEX (must be a positive integer) " + PREFIX_AMOUNT + "AMOUNT\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_AMOUNT + "50";

    private final Index index;
    private final FinanceAmount payment;

    public PayCommand(Index index, FinanceAmount payment) {
        this.index = index;
        this.payment = payment;
    }

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

    public Finance updateFinance(Person p, FinanceAmount payment) {
        Finance oldFinance = p.getFinance().get();
        return new Finance(new FinanceAmount(oldFinance.getFinanceAmount().getAmount() - payment.getAmount()),
                oldFinance.getType(), oldFinance.getStatus());
    }
}
