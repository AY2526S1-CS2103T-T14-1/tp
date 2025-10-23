package seedu.address.model.finance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Finance record in the address book.
 * Tracks the owed amount for a person.
 */
public class Finance {

    private final FinanceAmount owedAmount;
    private final List<PaymentEntry> history;
    private final TuitionPlan plan; // Optional (can be null)

    /**
     * Constructs a {@code Finance} with an initial owed amount.
     *
     * @param owedAmount A valid owed amount.
     */
    public Finance(FinanceAmount owedAmount, List<PaymentEntry> history, TuitionPlan plan) {
        requireAllNonNull(owedAmount);
        this.owedAmount = owedAmount;
        this.history = history;
        this.plan = plan;
    }

    /**
     * Constructs a {@code Finance} with only owed amount (no plan).
     */
    public Finance(FinanceAmount owedAmount) {
        requireAllNonNull(owedAmount);
        this.owedAmount = owedAmount;
        this.history = new ArrayList<>();
        this.plan = null;
    }

    /**
     * Constructs a {@code Finance} with zero owed amount.
     */
    public Finance() {
        this.owedAmount = new FinanceAmount(0);
        this.history = new ArrayList<>();
        this.plan = null;
    }

    // Private canonical ctor to keep immutability on operations
    private Finance(FinanceAmount owedAmount, List<PaymentEntry> history) {
        this.owedAmount = owedAmount;
        this.history = history;
        this.plan = null;
    }

    public FinanceAmount getOwedAmount() {
        return owedAmount;
    }

    public List<PaymentEntry> getHistory() {
        return history;
    }
      
    public TuitionPlan getPlan() {
        return plan;
    }

    public boolean hasPlan() {
        return plan != null;
    }

    /**
     * Returns a new Finance object with an updated tuition plan.
     */
    public Finance withPlan(TuitionPlan newPlan) {
        return new Finance(this.owedAmount, this.history, newPlan);
    }

    /**
     * Returns a new Finance object with the specified amount added to the owed amount.
     *
     * @param amountToAdd The amount to add to the owed amount.
     * @return A new Finance object with the updated owed amount.
     */
    public Finance add(FinanceAmount amountToAdd) {
        requireAllNonNull(amountToAdd);
        double newAmount = this.owedAmount.getAmount() + amountToAdd.getAmount();
        return new Finance(new FinanceAmount(newAmount), this.history, this.plan);
    }

    /**
     * Returns a new Finance object with the specified amount deducted from the owed amount.
     *
     * @param amountToPay The amount to pay/reduce from the owed amount.
     * @return A new Finance object with the updated owed amount.
     */
    public Finance pay(FinanceAmount amountToPay) {
        requireAllNonNull(amountToPay);
        double newAmount = Math.max(0, this.owedAmount.getAmount() - amountToPay.getAmount());
        List<PaymentEntry> newHistory = new ArrayList<>(history);
        newHistory.add(new PaymentEntry(LocalDate.now(), amountToPay, ""));
        return new Finance(new FinanceAmount(newAmount), newHistory, this.plan);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Finance)) {
            return false;
        }

        Finance otherFinance = (Finance) other;
        return owedAmount.equals(otherFinance.owedAmount)
                && ((plan == null && otherFinance.plan == null)
                || (plan != null && plan.equals(otherFinance.plan)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(owedAmount, plan);
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return plan == null
                ? String.format("[Owed Amount: %.2f]", owedAmount.getAmount())
                : String.format("[Owed Amount: %.2f, Plan: %s]", owedAmount.getAmount(), plan);
    }

}
