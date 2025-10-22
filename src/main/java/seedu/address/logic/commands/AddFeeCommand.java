package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.FinanceType;
import seedu.address.model.finance.TuitionPlan;
import seedu.address.model.person.Person;

/**
 * Adds or updates a tuition fee plan for a student.
 */
public class AddFeeCommand extends Command {

    public static final String COMMAND_WORD = "addfee";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or updates a tuition fee plan for a student.\n"
            + "Parameters: s/STUDENT_NAME t/[lesson|month] a/AMOUNT\n"
            + "Example: addfee s/John Doe t/lesson a/50";

    private static final String MESSAGE_SUCCESS =
            "Tuition fee set: %s pays %s per %s.";
    private static final String MESSAGE_OVERWRITE_WARNING =
            "Warning: Fee plan already exists for this student. Overwriting the existing plan.";
    private static final String MESSAGE_NOT_FOUND =
            "Error: No student found with the name \"%s\".";

    private final String studentName;
    private final FinanceType type;
    private final FinanceAmount amount;

    public AddFeeCommand(String studentName, FinanceType type, FinanceAmount amount) {
        this.studentName = studentName;
        this.type = type;
        this.amount = amount;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Optional<Person> optionalPerson = model.findPersonByName(studentName);
        if (optionalPerson.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NOT_FOUND, studentName));
        }

        Person student = optionalPerson.get();
        boolean hasExistingPlan = student.getFinance().isPresent()
                && student.getFinance().get().hasPlan();

        TuitionPlan plan = new TuitionPlan(type, amount);
        Finance updatedFinance = student.getFinance()
                .orElse(new Finance())
                .withPlan(plan);

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

        String message = String.format(MESSAGE_SUCCESS,
                studentName, amount.toString(),
                type == FinanceType.PER_LESSON ? "lesson" : "month");

        if (hasExistingPlan) {
            message = MESSAGE_OVERWRITE_WARNING + "\n" + message;
        }

        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddFeeCommand)) {
            return false;
        }
        AddFeeCommand o = (AddFeeCommand) other;
        return studentName.equalsIgnoreCase(o.studentName)
                && type.equals(o.type)
                && amount.equals(o.amount);
    }
}
