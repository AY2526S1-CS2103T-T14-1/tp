package seedu.address.model.finance;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Finance's amount in the finance log.
 * Guarantees: immutable; is valid as declared in {@link #isValidAmount(String)}
 */
public class FinanceAmount {
    public static final String MESSAGE_CONSTRAINTS =
            "Amount must be a positive number up to 2 decimal places, between 0 and 1,000,000.00.";
    private static final String VALIDATION_REGEX = "^\\s*(\\d{1,7})(\\.\\d{1,2})?\\s*$";

    private final String amount;

    /**
     * Constructs a {@code FinanceAmount}.
     * @param amount
     */
    public FinanceAmount(String amount) {
        requireNonNull(amount);
        checkArgument(isValidAmount(amount), MESSAGE_CONSTRAINTS);
        this.amount = amount;
    }
    public FinanceAmount(double amount) {
        this.amount = String.format("%.2f", amount);
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
        return value <= 1000000;
    }

    /**
     * Returns the amount as a double.
     * @return amount is always valid
     */
    public double getAmount() {
        return Double.parseDouble(amount);
    }

    @Override
    public String toString() {
        return amount;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FinanceAmount otherAmount)) {
            return false;
        }

        return amount.equals(otherAmount.amount);
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

}
