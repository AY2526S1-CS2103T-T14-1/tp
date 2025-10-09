package seedu.address.testutil;

import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonName;
import seedu.address.model.lesson.Location;
import seedu.address.model.lesson.Time;

/**
 * A utility class to help with building Lesson objects.
 */
public class LessonBuilder {

    public static final String DEFAULT_LESSON_NAME = "Math";
    public static final String DEFAULT_DATE = "Monday";
    public static final String DEFAULT_TIME = "12:00";
    public static final String DEFAULT_LOCATION = "RoomA";

    private LessonName lessonName;
    private Date date;
    private Time time;
    private Location location;

    /**
     * Creates a {@code LessonBuilder} with the default details.
     */
    public LessonBuilder() {
        lessonName = new LessonName(DEFAULT_LESSON_NAME);
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        location = new Location(DEFAULT_LOCATION);
    }

    /**
     * Initializes the LessonBuilder with the data of {@code lessonToCopy}.
     */
    public LessonBuilder(Lesson lessonToCopy) {
        lessonName = lessonToCopy.getLessonName();
        date = lessonToCopy.getDate();
        time = lessonToCopy.getTime();
        location = lessonToCopy.getLocation();
    }

    /**
     * Sets the {@code LessonName} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLessonName(String lessonName) {
        this.lessonName = new LessonName(lessonName);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Lesson} that we are building.
     */
    public LessonBuilder withLocation(String location) {
        this.location = new Location(location);
        return this;
    }

    public Lesson build() {
        return new Lesson(lessonName, date, time, location);
    }

}
