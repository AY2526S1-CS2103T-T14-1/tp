package seedu.address.model.lesson;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Lesson in the address book.
 * Guarantees:
 *  immutable;
 *  name is valid as declared in {@link #isValidLessonName(String)};
 *  time is valid as declared in {@link #isValidTime(String)};
 *  location is valid as declared in {@link #isValidLocation(String)};
 */
public class Lesson {

    public static final String MESSAGE_CONSTRAINTS = "Lessons names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public static final String TIME_MESSAGE_CONSTRAINTS = "Lesson time should be in 24-hour format (HH:MM)";
    public static final String TIME_VALIDATION_REGEX = "([01]\\d|2[0-3]):[0-5]\\d";

    public final String lessonName;
    public final String time;
    public final String location;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param lessonName A valid lesson name.
     * @param time A valid time.
     * @param location A valid location.
     */
    public Lesson(String lessonName, String time, String location) {
        requireAllNonNull(lessonName, time, location);
        checkArgument(isValidLessonName(lessonName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidTime(time), TIME_MESSAGE_CONSTRAINTS);
        checkArgument(isValidLocation(location), MESSAGE_CONSTRAINTS);
        this.lessonName = lessonName;
        this.time = time;
        this.location = location;
    }

    /**
     * Returns true if a given string is a valid lesson name.
     */
    public static boolean isValidLessonName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid time in 24-hour format.
     */
    public static boolean isValidTime(String test) {
        return test.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Returns true if a given string is a valid location.
     */
    public static boolean isValidLocation(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Lesson)) {
            return false;
        }

        Lesson otherLesson = (Lesson) other;
        return lessonName.equals(otherLesson.lessonName)
                && time.equals(otherLesson.time)
                && location.equals(otherLesson.location);
    }

    @Override
    public int hashCode() {
        return (lessonName + time + location).hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[Lesson: %s | Time: %s | Location: %s]", lessonName, time, location);
    }

}

