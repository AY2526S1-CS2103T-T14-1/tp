package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LessonTest {

    @Test
    public void constructor_validInputs_success() {
        // valid lesson name, time, and location
        String validName = "test";
        String validTime = "00:00";
        String validLocation = "test";

        // should not throw an exception
        assertDoesNotThrow(() -> new Lesson(validName, validTime, validLocation));

        Lesson lesson = new Lesson(validName, validTime, validLocation);
        assertEquals(validName, lesson.lessonName);
        assertEquals(validTime, lesson.time);
        assertEquals(validLocation, lesson.location);
    }

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

    @Test
    public void equalsTest() {
        Lesson lesson1 = new Lesson("test", "00:00", "test");
        Lesson lesson2 = new Lesson("test", "00:00", "test");
        Lesson lesson3 = new Lesson("Different", "00:00", "test");
        Lesson lesson4 = new Lesson("test", "01:00", "test");
        Lesson lesson5 = new Lesson("test", "00:00", "Different");

        // same content → should be equal
        assertEquals(lesson1, lesson1);
        assertEquals(lesson1, lesson2);

        // different lesson name/time/location → not equal
        assertNotEquals(lesson1, lesson3);
        assertNotEquals(lesson1, lesson4);
        assertNotEquals(lesson1, lesson5);
        assertNotEquals(lesson1, null);
    }

    @Test
    public void hashCodeTest() {
        Lesson lesson1 = new Lesson("test", "00:00", "test");
        Lesson lesson2 = new Lesson("test", "00:00", "test");
        Lesson lesson3 = new Lesson("Different", "00:00", "test");

        // consistent hashCode for equal objects
        assertEquals(lesson1.hashCode(), lesson2.hashCode());

        // typically should differ, though not guaranteed strictly by contract
        assertNotEquals(lesson1.hashCode(), lesson3.hashCode());
    }

    @Test
    public void toStringTest() {
        Lesson lesson = new Lesson("test", "00:00", "test");
        String expected = "[Lesson: test | Time: 00:00 | Location: test]";
        assertEquals(expected, lesson.toString());
    }

}
