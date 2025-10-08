package seedu.address.model.lesson;

import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Lesson(null, null, null));
    }

    @Test
    public void constructor_invalidLessonName_throwsIllegalArgumentException() {
        String invalidLessonName = "";
        assertThrows(IllegalArgumentException.class, () -> new Lesson(invalidLessonName, "test", "test"));
    }

    @Test
    public void isValidLessonName() {
        // null lesson name
        assertThrows(NullPointerException.class, () -> Lesson.isValidLessonName(null));
    }

}
