package seedu.address.model.person;

/**
 * Represents the attendance status of a person for their assigned lesson.
 * Guarantees: immutable;
 */
public enum AttendanceStatus {
    PRESENT, ABSENT;

    public static final String MESSAGE_CONSTRAINTS = "Status must be either 'present' (or 'p') or 'absent' (or 'a').";

    /**
     * Returns the string representation of the attendance status.
     */
    @Override
    public String toString() {
        return this == PRESENT ? "Present" : "Absent";
    }

    /**
     * Parses the input string and returns the corresponding AttendanceStatus.
     * The input is case-insensitive and accepts abbreviations 'p' and 'a'.
     *
     * @param status The input string representing the status.
     * @return The corresponding AttendanceStatus.
     * @throws IllegalArgumentException if the input string is not a valid status.
     */
    public static AttendanceStatus fromString(String status) throws IllegalArgumentException {
        if (status == null) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
        String lowerCaseStatus = status.trim().toLowerCase();
        switch (lowerCaseStatus) {
        case "present":
        case "p":
            return PRESENT;
        case "absent":
        case "a":
            return ABSENT;
        default:
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }
}
