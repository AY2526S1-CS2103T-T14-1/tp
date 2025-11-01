package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalLessons.MATH;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Attendance;

public class JsonAdaptedAttendanceTest {
    private static final int INVALID_TOTAL_LESSONS = -1;
    private static final int INVALID_TOTAL_ATTENDANCES = -1;

    private static final int VALID_TOTAL_LESSONS = MATH.getAttendance().getTotalLessons();
    private static final int VALID_TOTAL_ATTENDANCES = MATH.getAttendance().getTotalAttendances();

    @Test
    public void toModelType_validAttendanceDetails_returnsAttendance() throws Exception {
        JsonAdaptedAttendance attendance = new JsonAdaptedAttendance(VALID_TOTAL_LESSONS, VALID_TOTAL_ATTENDANCES);
        assertEquals(MATH.getAttendance(), attendance.toModelType());
    }

    @Test
    public void toModelType_invalidTotalLessons_throwsIllegalValueException() {
        JsonAdaptedAttendance attendance =
                new JsonAdaptedAttendance(INVALID_TOTAL_LESSONS, VALID_TOTAL_ATTENDANCES);
        String expectedMessage = Attendance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, attendance::toModelType);
    }

    @Test
    public void toModelType_invalidTotalAttendances_throwsIllegalValueException() {
        JsonAdaptedAttendance attendance =
                new JsonAdaptedAttendance(VALID_TOTAL_LESSONS, INVALID_TOTAL_ATTENDANCES);
        String expectedMessage = Attendance.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, attendance::toModelType);
    }
}
