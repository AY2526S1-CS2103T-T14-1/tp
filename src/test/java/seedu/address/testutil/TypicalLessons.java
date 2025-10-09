package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.lesson.Lesson;

/**
 * A utility class containing a list of {@code Lesson} objects to be used in tests.
 */
public class TypicalLessons {

    public static final Lesson MATH = new LessonBuilder().withLessonName("Math")
            .withDate("Friday").withTime("14:00").withLocation("RoomA").build();
    public static final Lesson PHYSICS = new LessonBuilder().withLessonName("Physics")
            .withDate("Thursday").withTime("10:00").withLocation("RoomB").build();

    private TypicalLessons() {} // prevents instantiation

    public static List<Lesson> getTypicalLessons() {
        return new ArrayList<>(Arrays.asList(MATH, PHYSICS));
    }
}
