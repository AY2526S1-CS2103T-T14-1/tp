package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.FinanceAmount;

public class PayCommand extends Command {

    public static final String COMMAND_WORD = "pay";

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

        return null;
    }
}
