package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.FinanceStatus;
import seedu.address.model.finance.FinanceType;

/**
 * Jackson-friendly version of {@link Finance}.
 */
class JsonAdaptedFinance {

    private final String amount;
    private final String type;
    private final String status;

    /**
     * Constructs a {@code JsonAdaptedFinance} with the given finance details.
     */
    @JsonCreator
    public JsonAdaptedFinance(@JsonProperty("amount") String amount,
                              @JsonProperty("type") String type,
                              @JsonProperty("status") String status) {
        this.amount = amount;
        this.type = type;
        this.status = status;
    }

    /**
     * Converts a given {@code Finance} into this class for Jackson use.
     */
    public JsonAdaptedFinance(Finance source) {
        amount = String.valueOf(source.getFinanceAmount().getAmount());
        type = source.getType().name();
        status = source.getStatus().name();
    }

    /**
     * Converts this Jackson-friendly adapted finance object into the model's {@code Finance} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Finance toModelType() throws IllegalValueException {
        if (amount == null) {
            throw new IllegalValueException("Finance amount cannot be null!");
        }
        if (!FinanceAmount.isValidAmount(amount)) {
            throw new IllegalValueException(FinanceAmount.MESSAGE_CONSTRAINTS);
        }
        final FinanceAmount modelAmount = new FinanceAmount(amount);

        if (type == null) {
            throw new IllegalValueException("Finance type cannot be null!");
        }
        final FinanceType modelType;
        try {
            modelType = FinanceType.valueOf(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Finance type must be either PER_MONTH or PER_LESSON.");
        }

        if (status == null) {
            throw new IllegalValueException("Finance status cannot be null!");
        }
        final FinanceStatus modelStatus;
        try {
            modelStatus = FinanceStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new IllegalValueException("Finance status must be either PAID, UNPAID, or OVERDUE.");
        }

        return new Finance(modelAmount, modelType, modelStatus);
    }
}
