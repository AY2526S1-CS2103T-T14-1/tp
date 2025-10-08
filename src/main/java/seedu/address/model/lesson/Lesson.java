package seedu.address.model.lesson;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Lesson in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidLessonName(String)}
 */
public class Lesson {

    public static final String MESSAGE_CONSTRAINTS = "Lessons names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    public final String lessonName;
    public final String time;
    public final String location;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param lessonName A valid lesson name.
     */
    public Lesson(String lessonName, String time, String location) {
        requireAllNonNull(lessonName, time, location);
        checkArgument(isValidLessonName(lessonName), MESSAGE_CONSTRAINTS);
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
        return lessonName.equals(otherLesson.lessonName);
    }

    @Override
    public int hashCode() {
        return lessonName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + lessonName + ']';
    }

}

