package seedu.address.model.lesson;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Lesson in the address book.
 * Guarantees:
 *  immutable; name, date, time and location are valid;
 */
public class Lesson {

    public final LessonName lessonName;
    public final Date date;
    public final Time time;
    public final Location location;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param lessonName A valid lesson name.
     * @param date A valid date.
     * @param time A valid time.
     * @param location A valid location.
     */
    public Lesson(LessonName lessonName, Date date, Time time, Location location) {
        this.lessonName = lessonName;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    /**
     * Constructs a {@code Lesson}.
     *
     * @param lesson A valid lesson.
     */
    public Lesson(Lesson lesson) {
        requireAllNonNull(lesson);
        this.lessonName = lesson.lessonName;
        this.date = lesson.date;
        this.time = lesson.time;
        this.location = lesson.location;
    }

    public LessonName getLessonName() {
        return lessonName;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public Location getLocation() {
        return location;
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
                && date.equals(otherLesson.date)
                && time.equals(otherLesson.time)
                && location.equals(otherLesson.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonName, date, time, location);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[Lesson Name: %s | Date: %s | Time: %s | Location: %s]",
                lessonName.toString(), date.toString(), time.toString(), location.toString());
    }

}

