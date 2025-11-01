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
    private final String note;

    /**
     * Constructs a {@code PaymentEntry}.
     *
     * @param date   payment date
     * @param amount payment amount
     * @param note   optional note; may be empty or null
     */
    public PaymentEntry(LocalDate date, LocalTime time, FinanceAmount amount, String note) {
        this.date = date;
        this.time = time;
        this.amount = amount;
        this.note = note;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getNote() {
        return note;
    }

    public FinanceAmount getAmount() {
        return amount;
    }
}
