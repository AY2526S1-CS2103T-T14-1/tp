package seedu.address.model.finance;

/**
 * Represents the status of a finance entry.
 * Supported statuses: Paid, Unpaid, Overdue.
 */
public enum FinanceStatus {
    PAID, UNPAID, OVERDUE;

    @Override
    public String toString() {
        return switch (this) {
        case PAID -> "Paid";
        case UNPAID -> "Unpaid";
        case OVERDUE -> "Overdue";
        default -> "invalid";
        };
    }
}
