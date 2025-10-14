package seedu.address.model.finance;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a Finance record in the address book.
 * Tracks the owed amount for a person.
 */
public class Finance {

    private final FinanceAmount owedAmount;



    /**
     * Constructs a {@code Finance} with an initial owed amount.
     *
     * @param owedAmount A valid owed amount.
     */
    public Finance(FinanceAmount owedAmount) {
        requireAllNonNull(owedAmount);
        this.owedAmount = owedAmount;
    }

    /**
     * Constructs a {@code Finance} with zero owed amount.
     */
    public Finance() {
        this.owedAmount = new FinanceAmount(0);
    }

    public FinanceAmount getOwedAmount() {
        return owedAmount;
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
        return new Finance(new FinanceAmount(newAmount));
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
        return new Finance(new FinanceAmount(newAmount));
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

        return owedAmount.equals(otherFinance.owedAmount);
    }

    @Override
    public int hashCode() {
        return owedAmount.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return String.format("[Owed Amount: %.2f]", owedAmount.getAmount());
    }

}
