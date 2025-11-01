package seedu.address.model.finance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Finance record in the address book.
 * Tracks the amount owed and payment history for a student.
 */
public class Finance {

    private final FinanceAmount owedAmount;
    private final List<PaymentEntry> history;

    /**
     * Constructs a {@code Finance} with the specified owed amount.
     *
     * @param owedAmount The current outstanding amount.
     */
    public Finance(FinanceAmount owedAmount) {
        requireAllNonNull(owedAmount);
        this.owedAmount = owedAmount;
        this.history = new ArrayList<>();
    }

    /**
     * Constructs a {@code Finance} with an owed amount and payment history.
     *
     * @param owedAmount The current outstanding amount.
     * @param history The list of past payments.
     */
    public Finance(FinanceAmount owedAmount, List<PaymentEntry> history) {
        requireAllNonNull(owedAmount);
        this.owedAmount = owedAmount;
        this.history = new ArrayList<>(history == null ? List.of() : history);
    }

    /**
     * Constructs a {@code Finance} with zero owed amount.
     */
    public Finance() {
        this.owedAmount = new FinanceAmount(0);
        this.history = new ArrayList<>();
    }

    /**
     * Returns the current owed amount.
     *
     * @return The {@code FinanceAmount} representing the outstanding balance.
     */
    public FinanceAmount getOwedAmount() {
        return owedAmount;
    }

    /**
     * Returns an unmodifiable view of the payment history.
     *
     * @return A list of {@code PaymentEntry} objects.
     */
    public List<PaymentEntry> getHistory() {
        return Collections.unmodifiableList(history);
    }

    /**
     * Returns a new {@code Finance} object with the owed amount replaced by the specified amount.
     *
     * @param newAmount The new outstanding amount to set.
     * @return A new {@code Finance} with the updated owed amount.
     */
    public Finance withOutstandingAmount(FinanceAmount newAmount) {
        requireAllNonNull(newAmount);
        return new Finance(newAmount, this.history);
    }

    /**
     * Returns a new {@code Finance} object with the specified amount added to the owed amount.
     *
     * @param amountToAdd The amount to add to the owed amount.
     * @return A new {@code Finance} object with the updated owed amount.
     */
    public Finance add(FinanceAmount amountToAdd) {
        requireAllNonNull(amountToAdd);
        double newAmount = this.owedAmount.getAmount() + amountToAdd.getAmount();
        return new Finance(new FinanceAmount(newAmount), this.history);
    }

    /**
     * Returns a new {@code Finance} object with the specified amount deducted from the owed amount.
     *
     * @param amountToPay The amount to reduce from the owed amount.
     * @return A new {@code Finance} object with the updated owed amount.
     */
    public Finance pay(FinanceAmount amountToPay) {
        requireAllNonNull(amountToPay);
        double newAmount = Math.max(0, this.owedAmount.getAmount() - amountToPay.getAmount());
        List<PaymentEntry> newHistory = new ArrayList<>(history);
        newHistory.add(new PaymentEntry(LocalDate.now(), LocalTime.now(), amountToPay, ""));
        return new Finance(new FinanceAmount(newAmount), newHistory);
    }

    /**
     * Returns true if this {@code Finance} is equal to another object.
     *
     * @param other The object to compare with.
     * @return True if both objects represent the same finance record.
     */
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
                && history.equals(otherFinance.history);
    }

    /**
     * Returns the hash code for this {@code Finance}.
     *
     * @return Hash code of the Finance object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(owedAmount, history);
    }

    /**
     * Returns a string representation of this {@code Finance}.
     *
     * @return A formatted string showing the owed amount and history count.
     */
    @Override
    public String toString() {
        return String.format("[Owed Amount: %.2f, Payments: %d]",
                owedAmount.getAmount(), history.size());
    }
}
