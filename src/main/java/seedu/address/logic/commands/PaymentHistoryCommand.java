package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.PaymentEntry;
import seedu.address.model.person.Person;

/**
 * Shows all recorded payments across the currently filtered person list,
 * sorted newest first and grouped by date.
 */
public class PaymentHistoryCommand extends Command {

    public static final String COMMAND_WORD = "payments";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows the payment history for all students from newest to oldest.\n"
            + "Example: " + COMMAND_WORD;

    public static final String PAYMENT_EMPTY = "No payment records found.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Person> personList = model.getAddressBook().getPersonList();
        List<PaymentHistoryCommand.Row> rows = new ArrayList<>();

        for (Person p : personList) {
            Optional<Finance> optionalFinance = p.getFinance();
            if (optionalFinance.isEmpty()) {
                continue;
            }

            List<PaymentEntry> history = optionalFinance.get().getHistory();
            if (history.isEmpty()) {
                continue;
            }
            for (PaymentEntry pe : history) {
                rows.add(new Row(
                        pe.getDate(),
                        pe.getTime(),
                        pe.getAmount().getAmount(),
                        pe.getNote(),
                        p.getName().toString()
                ));
            }
        }

        if (rows.isEmpty()) {
            return new CommandResult(PAYMENT_EMPTY);
        }

        rows.sort(Comparator.comparing(Row::getDate).reversed().thenComparing(Row::getTime).reversed());

        String output = "Payment History (newest first): \n";
        LocalDate current = null;
        double total = 0.0;

        for (Row row : rows) {
            if (!row.getDate().equals(current)) {
                current = row.getDate();
                output += "\n" + current + "\n";
            }
            total += row.amount;

            output += "  - [" + row.student + "] " + String.format("%.2f", row.amount);
            if (!row.note.isEmpty()) {
                output += " - " + row.note;
            }
            output += "\n";
        }

        output += "\nTotal paid: " + String.format("%.2f", total);

        return new CommandResult(output, false, false, true);
    }

    private static final class Row {
        private final LocalDate date;
        private final LocalTime time;
        private final double amount;
        private final String note;
        private final String student;

        public Row(LocalDate date, LocalTime time, double amount, String note, String student) {
            this.date = date;
            this.time = time;
            this.amount = amount;
            this.note = note == null ? "" : note;
            this.student = student;
        }

        public LocalDate getDate() {
            return date;
        }

        public LocalTime getTime() {
            return time;
        }

        public String getStudent() {
            return student;
        }
    }
}
