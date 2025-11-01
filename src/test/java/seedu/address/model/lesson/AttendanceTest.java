package seedu.address.model.lesson;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @Test
    public void isValidAttendanceStatus() {
        // null attendance status
        assertThrows(NullPointerException.class, () -> Attendance.isValidAttendanceStatus(null));

        // invalid attendance status
        assertFalse(Attendance.isValidAttendanceStatus("")); // empty string
        assertFalse(Attendance.isValidAttendanceStatus(" ")); // spaces only

        // valid attendance status
        assertTrue(Attendance.isValidAttendanceStatus("present"));
    }

    @Test
    public void constructor_invalidAttendance_throwsIllegalArgumentException() {
        // totalAttendances > totalLessons
        assertThrows(IllegalArgumentException.class, () -> new Attendance(0, 4));
        assertThrows(IllegalArgumentException.class, () -> new Attendance(3, 5));

        // negative values
        assertThrows(IllegalArgumentException.class, () -> new Attendance(-1, 0));
        assertThrows(IllegalArgumentException.class, () -> new Attendance(0, -1));
        assertThrows(IllegalArgumentException.class, () -> new Attendance(-1, -1));
    }

    @Test
    public void constructor_validAttendance_success() {
        // totalAttendances <= totalLessons
        new Attendance(0, 0); // should not throw
        new Attendance(5, 3); // should not throw
        new Attendance(10, 10); // should not throw
    }

    @Test
    public void equals() {
        Attendance attendance = new Attendance();

        // same values -> returns true
        assertTrue(attendance.equals(new Attendance()));

        // same object -> returns true
        assertTrue(attendance.equals(attendance));

        // null -> returns false
        assertFalse(attendance.equals(null));

        // different types -> returns false
        assertFalse(attendance.equals(5.0f));

        // different values -> returns false
        assertFalse(attendance.equals(new Attendance(1, 1)));
    }
}
