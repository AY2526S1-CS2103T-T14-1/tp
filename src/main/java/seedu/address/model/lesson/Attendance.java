package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a student's attendance status for a lesson.
 * Guarantees: immutable; value is either "present" or "absent"
 */
public class Attendance {

    public static final String MESSAGE_CONSTRAINTS = "Attendance must be either 'present' or 'absent'.";
    public static final String VALIDATION_REGEX = "(?i)^(present|absent)$";
    public static final String PRINT_FORMAT = "%d/%d";

    private final int totalLessons;
    private final int totalAttendances;

    /**
     * Constructs a new empty {@code Attendance}.
     */
    public Attendance() {
        this.totalLessons = 0;
        this.totalAttendances = 0;
    }

    /**
     * Constructs a new {@code Attendance} with specified details.
     * @param totalLessons Total lessons.
     * @param totalAttendances Total attendances.
     */
    public Attendance(int totalLessons, int totalAttendances) {
        this.totalLessons = totalLessons;
        this.totalAttendances = totalAttendances;
    }

    /**
     * Returns a new {@code Attendance} updated based on the given status.
     * @param status A valid attendance status ("PRESENT" or "ABSENT").
     * @return A new Attendance object with updated counts.
     */
    public Attendance mark(AttendanceStatus status) {
        requireNonNull(status);
        return switch (status) {
        case PRESENT -> new Attendance(totalLessons + 1, totalAttendances + 1);
        case ABSENT -> new Attendance(totalLessons + 1, totalAttendances);
        default -> throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        };
    }

    public static boolean isValidAttendanceStatus(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public int getTotalLessons() {
        return totalLessons;
    }

    public int getTotalAttendances() {
        return totalAttendances;
    }

    @Override
    public String toString() {
        return String.format(PRINT_FORMAT, totalAttendances, totalLessons);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Attendance otherAttendance)) {
            return false;
        }
        return totalAttendances == otherAttendance.totalAttendances && totalLessons == otherAttendance.totalLessons;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalAttendances, totalLessons);
    }
}
