package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.PaymentEntry;

/**
 * Jackson-friendly version of {@link Finance}.
 */
class JsonAdaptedFinance {

    private final String owedAmount;
    private final List<JsonAdaptedPaymentEntry> history = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedFinance} with the given finance details.
     */
    public JsonAdaptedFinance(String owedAmount) {
        this.owedAmount = owedAmount;
    }

    @JsonCreator
    public JsonAdaptedFinance(@JsonProperty("owedAmount") String owedAmount,
                              @JsonProperty("history") List<JsonAdaptedPaymentEntry> history) {
        this.owedAmount = owedAmount;
        if (history != null) {
            this.history.addAll(history);
        }
    }

    /**
     * Converts a given {@code Finance} into this class for Jackson use.
     */
    public JsonAdaptedFinance(Finance source) {
        owedAmount = String.valueOf(source.getOwedAmount().getAmount());
        for (PaymentEntry entry : source.getHistory()) {
            this.history.add(new JsonAdaptedPaymentEntry(entry));
        }
    }

    /**
     * Converts this Jackson-friendly adapted finance object into the model's {@code Finance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Finance toModelType() throws IllegalValueException {
        if (owedAmount == null) {
            throw new IllegalValueException("Finance owed amount cannot be null!");
        }
        if (!FinanceAmount.isValidAmount(owedAmount)) {
            throw new IllegalValueException(FinanceAmount.MESSAGE_CONSTRAINTS);
        }

        final FinanceAmount modelOwedAmount = new FinanceAmount(owedAmount);

        final List<PaymentEntry> modelHistory = new ArrayList<>();
        for (JsonAdaptedPaymentEntry jsonAdaptedPaymentEntry : history) {
            modelHistory.add(jsonAdaptedPaymentEntry.toModelType());
        }

        return new Finance(modelOwedAmount, modelHistory);
    }
}
