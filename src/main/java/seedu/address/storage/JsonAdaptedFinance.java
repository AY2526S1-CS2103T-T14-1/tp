package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;

/**
 * Jackson-friendly version of {@link Finance}.
 */
class JsonAdaptedFinance {

    private final String owedAmount;

    /**
     * Constructs a {@code JsonAdaptedFinance} with the given finance details.
     */
    @JsonCreator
    public JsonAdaptedFinance(@JsonProperty("owedAmount") String owedAmount) {
        this.owedAmount = owedAmount;
    }

    /**
     * Converts a given {@code Finance} into this class for Jackson use.
     */
    public JsonAdaptedFinance(Finance source) {
        owedAmount = String.valueOf(source.getOwedAmount().getAmount());
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

        return new Finance(modelOwedAmount);
    }
}
