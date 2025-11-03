package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
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
    private final String time;
    private final String amount;

    /**
     * Constructs a {@code JsonAdaptedPaymentEntry} from individual JSON properties.
     *
     * @param date   payment date in ISO-8601 (yyyy-MM-dd)
     * @param time   payment time in ISO-8601 (HH:mm:ss); may be null
     * @param amount payment amount as string with up to 2 dp
     */
    @JsonCreator
    public JsonAdaptedPaymentEntry(@JsonProperty("date") String date,
                                    @JsonProperty("time") String time,
                                    @JsonProperty("amount") String amount) {
        this.date = date;
        this.time = time;
        this.amount = amount;
    }

    /**
     * Converts a given {@code PaymentEntry} into this class for Jackson use.
     *
     * @param src model payment entry
     */
    public JsonAdaptedPaymentEntry(PaymentEntry src) {
        this.date = src.getDate().toString();
        this.amount = src.getAmount().toString();
        this.time = src.getTime().toString();
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
        final LocalTime modelTime;
        try {
            modelDate = LocalDate.parse(date);
            modelTime = time == null || time.isBlank() ? LocalTime.now() : LocalTime.parse(time);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid payment date or time: " + date + " " + time);
        }

        if (amount == null || !FinanceAmount.isValidAmount(amount)) {
            throw new IllegalValueException(FinanceAmount.MESSAGE_CONSTRAINTS);
        }
        final FinanceAmount modelAmount = new FinanceAmount(amount);

        return new PaymentEntry(modelDate, modelTime, modelAmount);
    }
}
