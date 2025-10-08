package seedu.address.model.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Finance record in the address book.
 * Guarantees: immutable; amount is valid as declared in {@link #isValidAmount(String)}
 */
public class Finance {

    public static final String MESSAGE_CONSTRAINTS =
            "Amount must be a positive number up to 2 decimal places, between 0 and 1,000,000.00.";
    private static final String VALIDATION_REGEX = "^\\s*(\\d{1,7})(\\.\\d{1,2})?\\s*$";

    public final double amount;

    /**
     * Constructs a {@code Finance}.
     *
     * @param amount A valid amount.
     */
    public Finance(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = Double.parseDouble(amount.trim());
    }

    /**
     * Returns true if a given string is a valid amount.
     */
    public static boolean isValidAmount(String test) {
        String trimmed = test.trim();
        if (!trimmed.matches(VALIDATION_REGEX)) {
            return false;
        }
        double value = Double.parseDouble(trimmed);
        return value >= 0 && value <= 1000000;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Finance)) {
            return false;
        }

        Finance otherFinance = (Finance) other;
        return amount == otherFinance.amount;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(amount);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[Amount: %.2f]", amount);
    }

}
