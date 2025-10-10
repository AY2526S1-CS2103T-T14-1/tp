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
        for (Person p : personList) {
            if (p.getFinance().get().isOverdue()) {
                overduePersonList.add(p);
            }
        }
        if (overduePersonList.isEmpty()) {
            return new CommandResult("No outstanding payments found.");
        }
        return new CommandResult("Listed " + overduePersonList.size() + " outstanding payments.");
    }
}
