package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_MATH;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_NAME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddLessonCommand.AddLessonDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;
import seedu.address.testutil.AddLessonDescriptorBuilder;

public class AddLessonCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_unfilteredList_success() {
        Person editedPerson = model.getFilteredPersonList().get(0);
        AddLessonDescriptor descriptor = new AddLessonDescriptorBuilder(editedPerson)
                .withLesson(VALID_LESSON_NAME_MATH, VALID_DATE, VALID_TIME, VALID_LOCATION).build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddLessonCommand.MESSAGE_ADD_LESSON_SUCCESS,
                editedPerson.getName(), editedPerson.getLesson().map(Lesson::toString).orElse(""));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0),
                editedPerson.setLesson(VALID_LESSON_NAME_MATH, VALID_DATE, VALID_TIME, VALID_LOCATION));

        assertCommandSuccess(addLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        AddLessonDescriptor descriptor = new AddLessonDescriptorBuilder(editedPerson)
                .withLesson(VALID_LESSON_NAME_MATH, VALID_DATE, VALID_TIME, VALID_LOCATION).build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(AddLessonCommand.MESSAGE_ADD_LESSON_SUCCESS,
                editedPerson.getName(), editedPerson.getLesson().map(Lesson::toString).orElse(""));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addLessonCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddLessonDescriptor descriptor = new AddLessonDescriptorBuilder().build();
        AddLessonCommand addLessonCommand = new AddLessonCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(addLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        AddLessonCommand addLessonCommand = new AddLessonCommand(outOfBoundIndex,
                new AddLessonDescriptorBuilder().build());

        assertCommandFailure(addLessonCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final AddLessonCommand standardCommand = new AddLessonCommand(INDEX_FIRST_PERSON, LESSON_MATH);

        // same values -> returns true
        AddLessonDescriptor copyDescriptor = new AddLessonDescriptor(LESSON_MATH);
        AddLessonCommand commandWithSameValues = new AddLessonCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new AddLessonCommand(INDEX_SECOND_PERSON, LESSON_MATH)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new AddLessonCommand(INDEX_FIRST_PERSON, LESSON_PHYSICS)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        AddLessonDescriptor addLessonDescriptor = new AddLessonDescriptor();
        AddLessonCommand addLessonCommand = new AddLessonCommand(index, addLessonDescriptor);
        String expected = AddLessonCommand.class.getCanonicalName() + "{index=" + index + ", addLessonDescriptor="
                + addLessonDescriptor + "}";
        assertEquals(expected, addLessonCommand.toString());
    }

}
