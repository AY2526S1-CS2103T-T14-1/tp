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
    public final Attendance attendance;

    /**
     * Constructs a {@code Lesson}.
     *
     * @param lessonName A valid lesson name.
     * @param date A valid date.
     * @param time A valid time.
     * @param location A valid location.
     */
    public Lesson(LessonName lessonName, Date date, Time time, Location location) {
        requireAllNonNull(lessonName, date, time, location);
        this.lessonName = lessonName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.attendance = new Attendance();
    }

    /**
     * Constructs a {@code Lesson} with specified attendance.
     */
    public Lesson(LessonName lessonName, Date date, Time time, Location location, Attendance attendance) {
        requireAllNonNull(lessonName, date, time, location, attendance);
        this.lessonName = lessonName;
        this.date = date;
        this.time = time;
        this.location = location;
        this.attendance = attendance;
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
        this.attendance = lesson.attendance;
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

    public Attendance getAttendance() {
        return attendance;
    }

    /**
     * Returns a new Lesson instance with updated attendance.
     */
    public Lesson markAttendance(AttendanceStatus attendanceStatus) {
        Attendance updated = this.attendance.mark(attendanceStatus);
        return new Lesson(lessonName, date, time, location, updated);
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
                && location.equals(otherLesson.location)
                && attendance.equals(otherLesson.attendance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonName, date, time, location, attendance);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[Lesson Name: %s | Date: %s | Time: %s | Location: %s | Attendance: %s]",
                lessonName, date, time, location, attendance);
    }

}
