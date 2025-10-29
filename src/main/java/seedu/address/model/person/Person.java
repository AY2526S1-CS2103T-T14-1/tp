package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.finance.Finance;
import seedu.address.model.lesson.AttendanceStatus;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonName;
import seedu.address.model.lesson.Location;
import seedu.address.model.lesson.Time;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Optional<Lesson> lesson;
    private final Optional<Finance> finance;

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Optional<Lesson> lesson, Optional<Finance> finance) {
        requireAllNonNull(name, phone, email, address, tags, lesson, finance);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.lesson = lesson;
        this.finance = finance;
    }


    /**
     * Convenience constructor for Person without lesson and finance information.
     */
    public Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        this(name, phone, email, address, tags, Optional.empty(), Optional.empty());
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public Optional<Finance> getFinance() {
        return finance;
    }

    public Optional<Lesson> getLesson() {
        return lesson;
    }

    /**
     * Returns a new Person object with a new Lesson assigned to them.
     * The attendance for the new lesson will automatically be set to "Absent".
     */
    public Person setLesson(String lessonName, String date, String time, String location) {
        Optional<Lesson> updatedLesson = Optional.of(new Lesson(new LessonName(lessonName), new Date(date),
                new Time(time), new Location(location)));
        Optional<Finance> updatedFinance = finance.or(() -> Optional.of(new Finance()));
        return new Person(name, phone, email, address, tags, updatedLesson, updatedFinance);
    }

    /**
     * Returns a new Person object with the attendance of their lesson updated.
     * Throws an IllegalStateException if the person has no lesson assigned.
     * @param attendanceStatus The attendance status ("PRESENT" or "ABSENT").
     * @return A new Person object with the updated lesson attendance.
     */
    public Person markAttendance(AttendanceStatus attendanceStatus) {
        if (lesson.isEmpty()) {
            throw new IllegalStateException(
                    "This person has no lesson assigned to mark attendance for.");
        }
        Lesson updatedLesson = this.lesson.get().markAttendance(attendanceStatus);
        return new Person(this.name, this.phone, this.email, this.address, this.tags,
                Optional.of(updatedLesson), this.finance);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person otherPerson)) {
            return false;
        }

        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && Objects.equals(lesson, otherPerson.lesson)
                && Objects.equals(finance, otherPerson.finance);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, lesson, finance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("tags", tags)
                .add("lesson", lesson.orElse(null))
                .add("finance", finance)
                .toString();
    }

}
