package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedLesson.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.MATH;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.LessonName;
import seedu.address.model.lesson.Location;
import seedu.address.model.lesson.Time;

public class JsonAdaptedLessonTest {
    private static final String INVALID_LESSON_NAME = "M@th";
    private static final String INVALID_DATE = "Day";
    private static final String INVALID_TIME = "12pm";
    private static final String INVALID_LOCATION = "Room@";

    private static final String VALID_LESSON_NAME = MATH.getLessonName().toString();
    private static final String VALID_DATE = MATH.getDate().toString();
    private static final String VALID_TIME = MATH.getTime().toString();
    private static final String VALID_LOCATION = MATH.getLocation().toString();
    private static final JsonAdaptedAttendance VALID_ATTENDANCE = new JsonAdaptedAttendance(MATH.getAttendance());

    @Test
    public void toModelType_validLessonDetails_returnsLesson() throws Exception {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(MATH);
        assertEquals(MATH, lesson.toModelType());
    }

    @Test
    public void toModelType_invalidLessonName_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(INVALID_LESSON_NAME, VALID_DATE, VALID_TIME, VALID_LOCATION, VALID_ATTENDANCE);
        String expectedMessage = LessonName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(null, VALID_DATE, VALID_TIME, VALID_LOCATION,
                VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_LESSON_NAME, INVALID_DATE, VALID_TIME, VALID_LOCATION, VALID_ATTENDANCE);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_LESSON_NAME, null, VALID_TIME, VALID_LOCATION,
                VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, INVALID_TIME, VALID_LOCATION, VALID_ATTENDANCE);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, null, VALID_LOCATION,
                VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedLesson lesson =
                new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, VALID_TIME, INVALID_LOCATION, VALID_ATTENDANCE);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedLesson lesson = new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, VALID_TIME, null,
                VALID_ATTENDANCE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, lesson::toModelType);
    }

}
