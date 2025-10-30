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
     * @param index of the person in the filtered person list to edit
     * @param addLessonDescriptor details to add lesson with
     */
    public AddLessonCommand(Index index, AddLessonDescriptor addLessonDescriptor) {
        requireNonNull(index);
        requireNonNull(addLessonDescriptor);

        this.index = index;
        this.addLessonDescriptor = new AddLessonDescriptor(addLessonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToAddLesson = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToAddLesson, addLessonDescriptor);

        String feedback = "";
        if (personToAddLesson.getLesson().isPresent()) {
            Lesson originalLesson = personToAddLesson.getLesson().orElseThrow();
            logger.log(Level.WARNING, String.format(MESSAGE_OVERWRITING, editedPerson.getName(), originalLesson));
            feedback = "Warning: " + String.format(MESSAGE_OVERWRITING, editedPerson.getName(), originalLesson) + "\n";
        }

        model.setPerson(personToAddLesson, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        feedback += String.format(MESSAGE_ADD_LESSON_SUCCESS, editedPerson.getName(), editedPerson.getLesson()
                .map(Lesson::toString).orElse(""));

        return new CommandResult(feedback);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToAddLesson}
     * edited with {@code addLessonDescriptor}.
     */
    private static Person createEditedPerson(Person personToEdit, AddLessonDescriptor addLessonDescriptor) {
        assert personToEdit != null;

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
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public AddLessonDescriptor(AddLessonDescriptor toCopy) {
            setLesson(toCopy.lesson);
        }

        public void setLesson(Lesson lesson) {
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
