package seedu.address.model.finance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Finance record in the address book.
 * Guarantees: immutable;
 */
public class Finance {

    private final FinanceAmount amount;
    private final FinanceType type;
    private final FinanceStatus status;


    /**
     * Constructs a {@code Finance}.
     *
     * @param amount A valid amount.
     */
    public Finance(FinanceAmount amount, FinanceType type, FinanceStatus status) {
        requireAllNonNull(amount, type, status);
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    public FinanceAmount getAmount() {
        return amount;
    }

    public FinanceType getType() {
        return type;
    }

    public FinanceStatus getStatus() {
        return status;
    }

    /**
     * Returns true if this finance record is overdue.
     */
    public boolean isOverdue() {
        if (this.getStatus().equals(FinanceStatus.OVERDUE)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Finance otherFinance)) {
            return false;
        }

        return amount.equals(otherFinance.amount)
                && type.equals(otherFinance.type)
                && status.equals(otherFinance.status);
    }

    @Override
    public int hashCode() {
        return Long.hashCode((long) amount.hashCode() + (long) type.hashCode() + (long) status.hashCode());
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[Amount: %.2f, Type: %s, Status: %s]",
                amount.getAmount(), type.toString(), status.toString());
    }

}
