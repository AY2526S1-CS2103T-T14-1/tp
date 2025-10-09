package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.finance.Finance;
import seedu.address.model.Model;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class ViewOutstandingPaymentsCommand extends Command {
    public static final String COMMAND_WORD = "outstanding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all outstanding payments.\n"
            + "Example: " + COMMAND_WORD;
    
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Finance> outstandingPaymentsList = model.getOutstandingPaymentsList();
        if (outstandingPaymentsList.isEmpty()) {
            return new CommandResult("No outstanding payments found.");
        }
        return new CommandResult("Listed " + outstandingPaymentsList.size() + " outstanding payments.");
    }
}
