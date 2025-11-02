package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Lesson's name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidLessonName(String)}
 */
public class LessonName {

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullLessonName;

    /**
     * Constructs a {@code LessonName}.
     *
     * @param lessonName A valid lesson name.
     */
    public LessonName(String lessonName) {
        requireNonNull(lessonName);
        checkArgument(isValidLessonName(lessonName), MESSAGE_CONSTRAINTS);
        fullLessonName = lessonName;
    }

    /**
     * Returns true if a given string is a valid name.
     *
     * @param test Lesson name to be tested.
     * @return True if lesson name is valid.
     */
    public static boolean isValidLessonName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullLessonName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LessonName)) {
            return false;
        }

        LessonName otherLessonName = (LessonName) other;
        return fullLessonName.equals(otherLessonName.fullLessonName);
    }

    @Override
    public int hashCode() {
        return fullLessonName.hashCode();
    }

}
