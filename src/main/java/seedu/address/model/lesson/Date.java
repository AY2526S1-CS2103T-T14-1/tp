package seedu.address.model.lesson;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.DayOfWeek;

/**
 * Represents a Lesson's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should be Monday, Tuesday, Wednesday, Thursday, Friday, Saturday or Sunday.";
    public static final String VALIDATION_REGEX =
            "^(?i)(Monday|Tuesday|Wednesday|Thursday|Friday|Saturday|Sunday)$";

    public final String value;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        value = date.toLowerCase();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidDate(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns this date as a {@link DayOfWeek}.
     */
    public DayOfWeek asDayOfWeek() {
        switch (value) {
        case "monday":
            return DayOfWeek.MONDAY;
        case "tuesday":
            return DayOfWeek.TUESDAY;
        case "wednesday":
            return DayOfWeek.WEDNESDAY;
        case "thursday":
            return DayOfWeek.THURSDAY;
        case "friday":
            return DayOfWeek.FRIDAY;
        case "saturday":
            return DayOfWeek.SATURDAY;
        case "sunday":
            return DayOfWeek.SUNDAY;
        default:
            throw new AssertionError("Invalid weekday: " + value);
        }
    }

    @Override
    public String toString() {
        return value.substring(0, 1).toUpperCase() + value.substring(1);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
