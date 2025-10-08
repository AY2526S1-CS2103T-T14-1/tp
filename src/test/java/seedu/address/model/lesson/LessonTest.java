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
        assertThrows(IllegalArgumentException.class, () -> new Lesson(invalidLessonName, "00:00", "test"));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        String invalidTime = "";
        assertThrows(IllegalArgumentException.class, () -> new Lesson("test", invalidTime, "test"));
    }

    @Test
    public void constructor_invalidLocation_throwsIllegalArgumentException() {
        String invalidLocation = "";
        assertThrows(IllegalArgumentException.class, () -> new Lesson("test", "00:00", invalidLocation));
    }

    @Test
    public void isValidLessonName() {
        // null lesson name
        assertThrows(NullPointerException.class, () -> Lesson.isValidLessonName(null));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Lesson.isValidTime(null));
    }

    @Test
    public void isValidLocation() {
        // null location
        assertThrows(NullPointerException.class, () -> Lesson.isValidLocation(null));
    }

}
