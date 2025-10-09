package seedu.address.testutil;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.FinanceStatus;
import seedu.address.model.finance.FinanceType;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonName;
import seedu.address.model.lesson.Location;
import seedu.address.model.lesson.Time;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final Optional<Finance> DEFAULT_FINANCE = Optional.of(new Finance(
            new FinanceAmount("0"),
            FinanceType.PER_MONTH,
            FinanceStatus.UNPAID
    ));
    public static final Lesson DEFAULT_LESSON =
            new Lesson(new LessonName("Math"), new Date("Monday"), new Time("12:00"), new Location("Online"));

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Optional<Finance> finance;
    private Lesson lesson;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        finance = DEFAULT_FINANCE;
        lesson = new Lesson(DEFAULT_LESSON);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        finance = personToCopy.getFinance();
        lesson = personToCopy.getLesson().orElse(null);
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Finance} of the {@code Person} that we are building.
     */
    public PersonBuilder withFinance(Finance finance) {
        this.finance = Optional.of(finance);
        return this;
    }

    /**
     * Sets the {@code Finance} of the {@code Person} that we are building to empty.
     */
    public PersonBuilder withoutFinance() {
        this.finance = Optional.empty();
        return this;
    }

    /**
     * Sets the {@code Lesson} of the {@code Person} that we are building.
     */
    public PersonBuilder withLesson(String lessonName, String date, String time, String location) {
        this.lesson = new Lesson(new LessonName(lessonName), new Date(date), new Time(time), new Location(location));
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, tags, Optional.ofNullable(lesson), finance);
    }

}
