package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_NAME_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.testutil.TypicalLessons.MATH;
import static seedu.address.testutil.TypicalLessons.PHYSICS;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LessonBuilder;

public class LessonTest {

    @Test
    public void equals() {
        // same values -> returns true
        Lesson mathCopy = new LessonBuilder(MATH).build();
        assertTrue(MATH.equals(mathCopy));

        // same object -> returns true
        assertTrue(MATH.equals(MATH));

        // null -> returns false
        assertFalse(MATH.equals(null));

        // different type -> returns false
        assertFalse(MATH.equals(5));

        // different lesson -> returns false
        assertFalse(MATH.equals(PHYSICS));

        // different lesson name -> returns false
        Lesson editedMath = new LessonBuilder(MATH).withLessonName(VALID_LESSON_NAME_PHYSICS).build();
        assertFalse(MATH.equals(editedMath));

        // different date -> returns false
        editedMath = new LessonBuilder(MATH).withDate(VALID_DATE).build();
        assertFalse(MATH.equals(editedMath));

        // different time -> returns false
        editedMath = new LessonBuilder(MATH).withTime(VALID_TIME).build();
        assertFalse(MATH.equals(editedMath));

        // different location -> returns false
        editedMath = new LessonBuilder(MATH).withLocation(VALID_LOCATION).build();
        assertFalse(MATH.equals(editedMath));
    }

    @Test
    public void toStringMethod() {
        String expected = String.format("[Lesson: %s | Date: %s | Time: %s | Location: %s | Status: %s]",
                MATH.getLessonName(), MATH.getDate(), MATH.getTime(), MATH.getLocation(), MATH.getAttendance());
        assertEquals(expected, MATH.toString());
    }
}
