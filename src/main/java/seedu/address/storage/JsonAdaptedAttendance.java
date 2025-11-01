package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.lesson.Attendance;

/**
 * Jackson-friendly version of {@link Attendance}.
 */
class JsonAdaptedAttendance {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Attendance's %s field is missing!";

    private final int totalLessons;
    private final int totalAttendances;

    /**
     * Constructs a {@code JsonAdaptedAttendance} with the given attendance details.
     */
    @JsonCreator
    public JsonAdaptedAttendance(@JsonProperty("totalLessons") int totalLessons,
                             @JsonProperty("totalAttendances") int totalAttendances) {
        this.totalLessons = totalLessons;
        this.totalAttendances = totalAttendances;
    }

    /**
     * Converts a given {@code Attendance} into this class for Jackson use.
     */
    public JsonAdaptedAttendance(Attendance source) {
        totalLessons = source.getTotalLessons();
        totalAttendances = source.getTotalAttendances();
    }

    /**
     * Converts this Jackson-friendly adapted lesson object into the model's {@code Attendance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted lesson.
     */
    public Attendance toModelType() throws IllegalValueException {
        if (totalLessons < 0 || totalAttendances < 0) {
            throw new IllegalValueException(Attendance.MESSAGE_CONSTRAINTS);
        }
        if (totalAttendances > totalLessons) {
            throw new IllegalValueException(Attendance.MESSAGE_INVALID_ATTENDANCE);
        }
        return new Attendance(totalLessons, totalAttendances);
    }

}
