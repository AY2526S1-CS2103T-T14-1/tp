package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.AttendanceStatus;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexAndStatusUnfilteredList_success() {
        // Create a new person with a lesson for testing purposes.
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithLesson = new PersonBuilder(firstPerson).withLesson("Math", "Monday", "10:00", "Room1").build();
        model.setPerson(firstPerson, personWithLesson);

        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                INDEX_FIRST_PERSON, AttendanceStatus.PRESENT);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person expectedPerson = new PersonBuilder(personWithLesson).build().markAttendance(AttendanceStatus.PRESENT);
        expectedModel.setPerson(personWithLesson, expectedPerson);
        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS,
                expectedPerson.getName(), expectedPerson.getLesson().orElseThrow());

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexAndStatusFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        // Create a new person with a lesson for testing purposes.
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person personWithLesson = new PersonBuilder(firstPerson).withLesson("Math", "Monday", "10:00", "Room1").build();
        model.setPerson(firstPerson, personWithLesson);

        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                INDEX_FIRST_PERSON, AttendanceStatus.PRESENT);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person expectedPerson = new PersonBuilder(personWithLesson).build().markAttendance(AttendanceStatus.PRESENT);
        expectedModel.setPerson(personWithLesson, expectedPerson);
        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDANCE_SUCCESS,
                expectedPerson.getName(), expectedPerson.getLesson().orElseThrow());

        assertCommandSuccess(markAttendanceCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_personWithoutLesson_throwsCommandException() {
        MarkAttendanceCommand markAttendanceCommand = new MarkAttendanceCommand(
                INDEX_SECOND_PERSON, AttendanceStatus.PRESENT);
        Person personWithoutLesson = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_PERSON_HAS_NO_LESSON,
                personWithoutLesson.getName());

        assertCommandFailure(markAttendanceCommand, model, expectedMessage);
    }
}
