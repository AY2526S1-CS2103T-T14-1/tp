package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ATTENDANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;

import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.AttendanceStatus;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * Marks the attendance of a student for their assigned lesson.
 */
public class MarkCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks attendance for a student's assigned lesson.\n"
            + "Parameters: "
            + PREFIX_STUDENT + "STUDENT_NAME "
            + PREFIX_ATTENDANCE + "STATUS\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_STUDENT + "John Doe "
            + PREFIX_ATTENDANCE + "present";

    public static final String MESSAGE_SUCCESS = "Attendance marked for %1$s: %2$s";
    public static final String MESSAGE_STUDENT_NOT_FOUND = "No student found with the name \"%1$s\".";
    public static final String MESSAGE_NO_LESSON_ASSIGNED = "Student %1$s has no lesson assigned to mark attendance for.";

    private final Name studentName;
    private final AttendanceStatus status;

    /**
     * Creates a MarkCommand to mark attendance for the specified student.
     */
    public MarkCommand(Name studentName, AttendanceStatus status) {
        requireNonNull(studentName);
        requireNonNull(status);
        this.studentName = studentName;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        Optional<Person> personToMarkOpt = lastShownList.stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(studentName.fullName))
                .findFirst();

        if (personToMarkOpt.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_STUDENT_NOT_FOUND, studentName.fullName));
        }

        Person originalPerson = personToMarkOpt.get();
        if (originalPerson.getLesson().isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_LESSON_ASSIGNED, studentName.fullName));
        }

        Person editedPerson = originalPerson.withAttendance(status);
        model.setPerson(originalPerson, editedPerson);

        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson.getName(), status.toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkCommand)) {
            return false;
        }

        MarkCommand otherMarkCommand = (MarkCommand) other;
        return studentName.equals(otherMarkCommand.studentName)
                && status.equals(otherMarkCommand.status);
    }
}
