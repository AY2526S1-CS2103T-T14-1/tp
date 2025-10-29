package seedu.address.storage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.PaymentEntry;

/**
 * Jackson-friendly version of {@link PaymentEntry}.
 */
public class JsonAdaptedPaymentEntry {
    private final String date;
    private final String amount;
    private final String note;

    /**
     * Constructs a {@code JsonAdaptedPaymentEntry} from individual JSON properties.
     *
     * @param date   payment date in ISO-8601 (yyyy-MM-dd)
     * @param amount payment amount as string with up to 2 dp
     * @param note   optional note; may be null
     */
    @JsonCreator
    public JsonAdaptedPaymentEntry(@JsonProperty("date") String date,
                                    @JsonProperty("amount") String amount,
                                    @JsonProperty("note") String note) {
        this.date = date;
        this.amount = amount;
        this.note = note == null ? "" : note;
    }

    /**
     * Converts a given {@code PaymentEntry} into this class for Jackson use.
     *
     * @param src model payment entry
     */
    public JsonAdaptedPaymentEntry(PaymentEntry src) {
        this.date = src.getDate().toString();
        this.amount = src.getAmount().toString();
        this.note = src.getNote();
    }

    /**
     * Converts this Jackson-friendly object into the model's {@link PaymentEntry}.
     *
     * @return model {@code PaymentEntry}
     * @throws IllegalValueException if any field fails validation
     */
    public PaymentEntry toModelType() throws IllegalValueException {
        if (date == null || date.isBlank()) {
            throw new IllegalValueException("PaymentEntry date cannot be null.");
        }
        final LocalDate modelDate;
        try {
            modelDate = LocalDate.parse(date);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid payment date: " + date);
        }

        if (amount == null || !FinanceAmount.isValidAmount(amount)) {
            throw new IllegalValueException(FinanceAmount.MESSAGE_CONSTRAINTS);
        }
        final FinanceAmount modelAmount = new FinanceAmount(amount);

        final String modelNote = note == null ? "" : note;

        return new PaymentEntry(modelDate, modelAmount, modelNote);
    }
}
