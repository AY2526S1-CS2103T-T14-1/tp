package seedu.address.model.finance;

import static java.util.Objects.requireNonNull;

/**
 * Represents a student's tuition plan.
 * Specifies whether the plan is per lesson or per month.
 */
public class TuitionPlan {

    private final FinanceType type;
    private final FinanceAmount rate;

    /**
     * Constructs a {@code TuitionPlan}.
     *
     * @param type The fee type (PER_LESSON or PER_MONTH).
     * @param rate The rate amount.
     */
    public TuitionPlan(FinanceType type, FinanceAmount rate) {
        requireNonNull(type);
        requireNonNull(rate);
        this.type = type;
        this.rate = rate;
    }

    public FinanceType getType() {
        return type;
    }

    public FinanceAmount getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return String.format("%s at %s", type.toString(), rate.toString());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TuitionPlan)) {
            return false;
        }

        TuitionPlan otherPlan = (TuitionPlan) other;
        return type.equals(otherPlan.type) && rate.equals(otherPlan.rate);
    }

    @Override
    public int hashCode() {
        return type.hashCode() + rate.hashCode();
    }
}
