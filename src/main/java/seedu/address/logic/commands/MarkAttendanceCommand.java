package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.AttendanceStatus;
import seedu.address.model.person.Person;

/**
 * Marks the attendance of a student for their assigned lesson.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "mark";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of the student "
            + "identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_STUDENT + "STATUS (must be 'present' or 'absent')\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_STUDENT + "present";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Marked attendance for Student %1$s: %2$s";
    public static final String MESSAGE_PERSON_HAS_NO_LESSON = "Cannot mark attendance. "
            + "Student %1$s has no lesson assigned.";

    private static final Logger logger = LogsCenter.getLogger(MarkAttendanceCommand.class);

    private final Index index;
    private final AttendanceStatus attendanceStatus;

    /**
     * Creates an {@code MarkAttendanceCommand} to be executed.
     *
     * @param index of the person in the filtered person list to mark attendance for.
     * @param attendanceStatus status to mark the student with.
     */
    public MarkAttendanceCommand(Index index, AttendanceStatus attendanceStatus) {
        requireAllNonNull(index, attendanceStatus);
        assert index.getOneBased() > 0 : "Index must be positive";
        assert attendanceStatus != null : "AttendanceStatus must not be null";
        this.index = index;
        this.attendanceStatus = attendanceStatus;
        logger.log(Level.FINE, "Created MarkAttendanceCommand with index: {0}, status: {1}",
                new Object[]{index, attendanceStatus});
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing MarkAttendanceCommand for index: {0}", index);

        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null : "Filtered person list should not be null";

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.SEVERE, "Invalid person index {0}, list size is {1}",
                    new Object[]{index.getZeroBased(), lastShownList.size()});
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        assert personToEdit != null : "Person to edit must not be null";
        logger.log(Level.FINE, "Retrieved person for attendance marking: {0}", personToEdit.getName());

        if (personToEdit.getLesson().isEmpty()) {
            logger.log(Level.WARNING, "Attempted to mark attendance for {0} with no lesson assigned",
                    personToEdit.getName());
            throw new CommandException(String.format(MESSAGE_PERSON_HAS_NO_LESSON, personToEdit.getName()));
        }

        Person editedPerson;
        try {
            editedPerson = personToEdit.markAttendance(this.attendanceStatus);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to mark attendance for {0}: {1}",
                    new Object[]{personToEdit.getName(), e.getMessage()});
            throw new CommandException("Failed to mark attendance: " + e.getMessage(), e);
        }
        assert editedPerson != null : "Edited person must not be null";

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        logger.log(Level.INFO, String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS, editedPerson.getName(),
                editedPerson.getLesson().orElseThrow()));

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                editedPerson.getName(), editedPerson.getLesson().orElseThrow()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }
        MarkAttendanceCommand otherCommand = (MarkAttendanceCommand) other;
        return index.equals(otherCommand.index)
                && attendanceStatus.equals(otherCommand.attendanceStatus);
    }
}
