package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a student's attendance status for a lesson.
 * Guarantees: immutable; value is either "present" or "absent"
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance must be either 'present' or 'absent'.";
    public static final String VALIDATION_REGEX = "(?i)^(present|absent)$";

    public final String status;

    /**
     * Constructs an {@code Attendance}. The status is stored in lowercase.
     * @param status A valid attendance status.
     */
    public Attendance(String status) {
        requireNonNull(status);
        checkArgument(isValidAttendance(status), MESSAGE_CONSTRAINTS);
        this.status = status.toLowerCase();
    }

    public static boolean isValidAttendance(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        // Display with first letter capitalized for user-friendliness
        return status.substring(0, 1).toUpperCase() + status.substring(1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Attendance)) {
            return false;
        }
        Attendance otherAttendance = (Attendance) other;
        return status.equals(otherAttendance.status);
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
