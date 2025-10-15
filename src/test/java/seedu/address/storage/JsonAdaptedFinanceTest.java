package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;

public class JsonAdaptedFinanceTest {
    private static final String INVALID_OWED_AMOUNT = "abc";
    private static final String INVALID_OWED_AMOUNT_NEGATIVE = "-100.00";
    private static final String INVALID_OWED_AMOUNT_TOO_LARGE = "2000000.00";

    private static final String VALID_OWED_AMOUNT = "100.00";

    @Test
    public void toModelType_validFinanceDetails_returnsFinance() throws Exception {
        Finance finance = new Finance(new FinanceAmount(VALID_OWED_AMOUNT));
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance(finance);
        Finance result = jsonAdaptedFinance.toModelType();
        assertEquals(finance.getOwedAmount().getAmount(), result.getOwedAmount().getAmount(), 0.001);
    }

    @Test
    public void toModelType_validOwedAmountString_returnsFinance() throws Exception {
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance(VALID_OWED_AMOUNT);
        Finance result = jsonAdaptedFinance.toModelType();
        assertEquals(100.00, result.getOwedAmount().getAmount(), 0.001);
    }

    @Test
    public void toModelType_invalidOwedAmount_throwsIllegalValueException() {
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance(INVALID_OWED_AMOUNT);
        String expectedMessage = FinanceAmount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedFinance::toModelType);
    }

    @Test
    public void toModelType_negativeOwedAmount_throwsIllegalValueException() {
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance(INVALID_OWED_AMOUNT_NEGATIVE);
        String expectedMessage = FinanceAmount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedFinance::toModelType);
    }

    @Test
    public void toModelType_tooLargeOwedAmount_throwsIllegalValueException() {
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance(INVALID_OWED_AMOUNT_TOO_LARGE);
        String expectedMessage = FinanceAmount.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedFinance::toModelType);
    }

    @Test
    public void toModelType_nullOwedAmount_throwsIllegalValueException() {
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance((String) null);
        String expectedMessage = "Finance owed amount cannot be null!";
        assertThrows(IllegalValueException.class, expectedMessage, jsonAdaptedFinance::toModelType);
    }

    @Test
    public void constructor_validFinance_success() throws Exception {
        Finance finance = new Finance(new FinanceAmount("50.00"));
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance(finance);
        Finance result = jsonAdaptedFinance.toModelType();
        assertEquals(50.00, result.getOwedAmount().getAmount(), 0.001);
    }

    @Test
    public void constructor_zeroOwedAmount_success() throws Exception {
        JsonAdaptedFinance jsonAdaptedFinance = new JsonAdaptedFinance("0.00");
        Finance result = jsonAdaptedFinance.toModelType();
        assertEquals(0.00, result.getOwedAmount().getAmount(), 0.001);
    }
}
