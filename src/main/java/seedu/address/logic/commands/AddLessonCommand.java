package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.finance.Finance;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Adds a lesson to an existing person in the address book.
 */
public class AddLessonCommand extends Command {

    public static final String COMMAND_WORD = "addlesson";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a lesson to the person identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + PREFIX_LOCATION + "LOCATION\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Math "
            + PREFIX_DATE + "Monday "
            + PREFIX_TIME + "12:00 "
            + PREFIX_LOCATION + "RoomA";

    public static final String MESSAGE_ADD_LESSON_SUCCESS = "Added Lesson to %1$s: %2$s";
    public static final String MESSAGE_OVERWRITING = "Overwriting existing lesson for %1$s with %2$s";

    private static final Logger logger = LogsCenter.getLogger(AddLessonCommand.class);

    private final Index index;
    private final AddLessonDescriptor addLessonDescriptor;

    /**
     * Creates an {@code AddLessonCommand} to be executed.
     *
     * @param index of the person in the filtered person list to edit
     * @param addLessonDescriptor details to add lesson with
     */
    public AddLessonCommand(Index index, AddLessonDescriptor addLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(addLessonDescriptor);

        assert index.getOneBased() > 0 : "Index must be positive";
        assert addLessonDescriptor.getLesson() != null : "Lesson in descriptor should not be null";

        this.index = index;
        this.addLessonDescriptor = new AddLessonDescriptor(addLessonDescriptor);
        logger.log(Level.FINE, "Created AddLessonCommand with index: {0} and descriptor: {1}",
                new Object[]{index, addLessonDescriptor});
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        logger.log(Level.INFO, "Executing AddLessonCommand for index: {0}", index);

        List<Person> lastShownList = model.getFilteredPersonList();
        assert lastShownList != null : "Filtered person list should not be null";

        if (index.getZeroBased() >= lastShownList.size()) {
            logger.log(Level.SEVERE, "Invalid index: {0}, list size: {1}",
                    new Object[]{index.getZeroBased(), lastShownList.size()});
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddLesson = lastShownList.get(index.getZeroBased());
        assert personToAddLesson != null : "Person to edit must not be null";
        logger.log(Level.FINE, "Target person before adding lesson: {0}", personToAddLesson);

        Person editedPerson = createEditedPerson(personToAddLesson, addLessonDescriptor);

        String feedback = "";
        if (personToAddLesson.getLesson().isPresent()) {
            Lesson originalLesson = personToAddLesson.getLesson().orElseThrow();
            logger.log(Level.WARNING, String.format(MESSAGE_OVERWRITING, editedPerson.getName(), originalLesson));
            feedback = "Warning: " + String.format(MESSAGE_OVERWRITING, editedPerson.getName(), originalLesson) + "\n";
        }

        try {
            model.setPerson(personToAddLesson, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            logger.log(Level.INFO, "Lesson successfully added to {0}", editedPerson.getName());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to update person in model: {0}", e.getMessage());
            throw new CommandException("Failed to add lesson: " + e.getMessage(), e);
        }

        feedback += String.format(MESSAGE_ADD_LESSON_SUCCESS, editedPerson.getName(), editedPerson.getLesson()
                .map(Lesson::toString).orElse(""));

        return new CommandResult(feedback);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit} edited with
     * {@code addLessonDescriptor}.
     *
     * @param personToEdit Person to be edited.
     * @param addLessonDescriptor Lesson details.
     * @return A {@code Person} with the details of {@code personToEdit} edited with {@code addLessonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, AddLessonDescriptor addLessonDescriptor) {
        requireNonNull(personToEdit);
        requireNonNull(addLessonDescriptor);
        assert addLessonDescriptor.getLesson() != null : "Lesson in descriptor must not be null";


        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = personToEdit.getTags();
        Optional<Lesson> updatedLesson = Optional.of(addLessonDescriptor.getLesson());
        Optional<Finance> updatedFinance = personToEdit.getFinance().or(() -> Optional.of(new Finance()));

        return new Person(updatedName, updatedPhone, updatedEmail,
                updatedAddress, updatedTags, updatedLesson, updatedFinance);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddLessonCommand)) {
            return false;
        }

        AddLessonCommand otherAddLessonCommand = (AddLessonCommand) other;
        return index.equals(otherAddLessonCommand.index)
                && addLessonDescriptor.equals(otherAddLessonCommand.addLessonDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("addLessonDescriptor", addLessonDescriptor)
                .toString();
    }

    /**
     * Stores the details to add lesson.
     */
    public static class AddLessonDescriptor {
        private Lesson lesson;

        public AddLessonDescriptor() {}

        /**
         * Copies an {@code AddLessonDescriptor} and return an {@code AddLessonDescriptor}.
         *
         * @param toCopy {@code AddLessonDescriptor} to be copied.
         */
        public AddLessonDescriptor(AddLessonDescriptor toCopy) {
            requireNonNull(toCopy);
            setLesson(toCopy.lesson);
        }

        public void setLesson(Lesson lesson) {
            requireNonNull(lesson);
            this.lesson = lesson;
        }

        public Lesson getLesson() {
            return lesson;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof AddLessonDescriptor)) {
                return false;
            }

            AddLessonDescriptor otherAddLessonDescriptor = (AddLessonDescriptor) other;
            return Objects.equals(lesson, otherAddLessonDescriptor.lesson);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("lesson", lesson)
                    .toString();
        }
    }
}
