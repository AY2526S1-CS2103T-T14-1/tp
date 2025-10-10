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

    @Test
    public void toModelType_validPersonDetails_returnsPerson() throws Exception {
        JsonAdaptedLesson person = new JsonAdaptedLesson(MATH);
        assertEquals(MATH, person.toModelType());
    }

    @Test
    public void toModelType_invalidLessonName_throwsIllegalValueException() {
        JsonAdaptedLesson person =
                new JsonAdaptedLesson(INVALID_LESSON_NAME, VALID_DATE, VALID_TIME, VALID_LOCATION);
        String expectedMessage = LessonName.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedLesson person = new JsonAdaptedLesson(null, VALID_DATE, VALID_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, LessonName.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedLesson person =
                new JsonAdaptedLesson(VALID_LESSON_NAME, INVALID_DATE, VALID_TIME, VALID_LOCATION);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedLesson person = new JsonAdaptedLesson(VALID_LESSON_NAME, null, VALID_TIME, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedLesson person =
                new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, INVALID_TIME, VALID_LOCATION);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullTime_throwsIllegalValueException() {
        JsonAdaptedLesson person = new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, null, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedLesson person =
                new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, VALID_TIME, INVALID_LOCATION);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedLesson person = new JsonAdaptedLesson(VALID_LESSON_NAME, VALID_DATE, VALID_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, person::toModelType);
    }

}
