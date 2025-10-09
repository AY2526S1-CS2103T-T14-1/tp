package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LessonName(null));
    }

    @Test
    public void constructor_invalidLessonName_throwsIllegalArgumentException() {
        String invalidLessonName = "M@th";
        assertThrows(IllegalArgumentException.class, () -> new LessonName(invalidLessonName));
    }

    @Test
    public void isValidLessonName() {
        // null lesson name
        assertThrows(NullPointerException.class, () -> LessonName.isValidLessonName(null));

        // invalid lesson names
        assertFalse(LessonName.isValidLessonName("")); // empty string
        assertFalse(LessonName.isValidLessonName(" ")); // spaces only

        // valid lesson name
        assertTrue(LessonName.isValidLessonName("Math"));
    }

    @Test
    public void equals() {
        LessonName lessonName = new LessonName("Math");

        // same values -> returns true
        assertTrue(lessonName.equals(new LessonName("Math")));

        // same object -> returns true
        assertTrue(lessonName.equals(lessonName));

        // null -> returns false
        assertFalse(lessonName.equals(null));

        // different types -> returns false
        assertFalse(lessonName.equals(5.0f));

        // different values -> returns false
        assertFalse(lessonName.equals(new LessonName("Physics")));
    }
}
