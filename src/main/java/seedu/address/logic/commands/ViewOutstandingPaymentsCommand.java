package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.finance.Finance;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class ViewOutstandingPaymentsCommand extends Command {
    public static final String COMMAND_WORD = "outstanding";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": View all outstanding payments.\n"
            + "Example: " + COMMAND_WORD;
    
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> personList = model.getFilteredPersonList();
        if (personList.isEmpty()) {
            return new CommandResult("No outstanding payments found.");
        }
        // filter those who have outstanding payments
        List<Person> overduePersonList = new ArrayList<>();
        StringBuilder outstandingPayments = new StringBuilder();
        int overdueCount = 0;
        for (Person p : personList) {
            if (p.getFinance().get().isOverdue()) {
                overdueCount++;
                overduePersonList.add(p);
                outstandingPayments.append(overdueCount + ":").append("\n");
                outstandingPayments.append(p.getName()).append("\n");
                outstandingPayments.append(p.getEmail()).append("\n");
                outstandingPayments.append(p.getPhone()).append("\n");
                outstandingPayments.append(p.getFinance().get()).append("\n");
            }
        }
        if (overduePersonList.isEmpty()) {
            return new CommandResult("No outstanding payments found.");
        }

        return new CommandResult(outstandingPayments.toString());
    }
}
