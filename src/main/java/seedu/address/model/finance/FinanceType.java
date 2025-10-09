package seedu.address.model.finance;

/**
 * Represents the type of finance.
 * Supported types: Per Lesson, Per Month.
 */
public enum FinanceType {
    PER_MONTH, PER_LESSON;

    @Override
    public String toString() {
        return switch (this) {
        case PER_MONTH -> "Monthly";
        case PER_LESSON -> "Per Lesson";
        default -> "invalid";
        };
    }
}
