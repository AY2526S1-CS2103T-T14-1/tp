package seedu.address.testutil;

import java.util.Optional;

import seedu.address.logic.commands.AddLessonCommand.AddLessonDescriptor;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonName;
import seedu.address.model.lesson.Location;
import seedu.address.model.lesson.Time;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building AddLessonDescriptor objects.
 */
public class AddLessonDescriptorBuilder {

    private AddLessonDescriptor descriptor;

    public AddLessonDescriptorBuilder() {
        descriptor = new AddLessonDescriptor();
    }

    public AddLessonDescriptorBuilder(AddLessonDescriptor descriptor) {
        this.descriptor = new AddLessonDescriptor(descriptor);
    }

    /**
     * Returns an {@code AddLessonDescriptor} with fields containing {@code person}'s details
     */
    public AddLessonDescriptorBuilder(Person person) {
        descriptor = new AddLessonDescriptor();
        descriptor.setLesson(person.getLesson());
    }

    /**
     * Sets the {@code Lesson} of the {@code AddLessonDescriptor} that we are building.
     */
    public AddLessonDescriptorBuilder withLesson(String lessonName, String date, String time, String location) {
        descriptor.setLesson(Optional.of(new Lesson(new LessonName(lessonName), new Date(date), new Time(time),
                new Location(location))));
        return this;
    }

    public AddLessonDescriptor build() {
        return descriptor;
    }
}
