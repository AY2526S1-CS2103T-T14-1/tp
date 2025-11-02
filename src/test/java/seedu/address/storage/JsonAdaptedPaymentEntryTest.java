//Generate with AI-Assistance
package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.PaymentEntry;

/**
 * Tests for JsonAdaptedPaymentEntry.
 */
public class JsonAdaptedPaymentEntryTest {

    @Test
    public void toModelType_validFields_returnsPaymentEntry() throws Exception {
        JsonAdaptedPaymentEntry json =
                new JsonAdaptedPaymentEntry("2025-10-28", "10:00:00", "12.34", "note");
        PaymentEntry entry = json.toModelType();

        assertEquals(LocalDate.parse("2025-10-28"), entry.getDate());
        assertEquals(12.34, entry.getAmount().getAmount(), 1e-9);
        assertEquals("note", entry.getNote());
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedPaymentEntry json =
                new JsonAdaptedPaymentEntry("2025-13-40", "10:00:00", "5.00", "");
        assertThrows(IllegalValueException.class, json::toModelType);
    }

    @Test
    public void toModelType_invalidTime_throwsIllegalValueException() {
        JsonAdaptedPaymentEntry json =
                new JsonAdaptedPaymentEntry("2025-11-11", "60:00:00", "5.00", "");
        assertThrows(IllegalValueException.class, json::toModelType);
    }

    @Test
    public void toModelType_invalidAmount_throwsIllegalValueException() {
        JsonAdaptedPaymentEntry json =
                new JsonAdaptedPaymentEntry("2025-10-28", "10:00:00", "-3.00", "bad");
        assertThrows(IllegalValueException.class,
                FinanceAmount.MESSAGE_CONSTRAINTS, json::toModelType);
    }

    @Test
    public void toModelType_nullAmount_throwsIllegalValueException() {
        JsonAdaptedPaymentEntry json =
                new JsonAdaptedPaymentEntry("2025-10-28", "09:30:00", null, "");
        assertThrows(IllegalValueException.class, json::toModelType);
    }
}
