package seedu.address.model.finance;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * One recorded payment entry stored inside a {@link Finance} history.
 */
public final class PaymentEntry {
    private final LocalDate date;
    private final LocalTime time;
    private final FinanceAmount amount;

    /**
     * Constructs a {@code PaymentEntry}.
     *
     * @param date   payment date
     * @param amount payment amount
     */
    public PaymentEntry(LocalDate date, LocalTime time, FinanceAmount amount) {
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public FinanceAmount getAmount() {
        return amount;
    }
}
