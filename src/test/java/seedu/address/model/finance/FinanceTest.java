package seedu.address.model.finance;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FinanceTest {

    @Test
    public void constructor_validInputs_success() {
        // valid amount
        FinanceAmount validAmount = new FinanceAmount("10.00");
        FinanceType type = FinanceType.PER_MONTH;
        FinanceStatus status = FinanceStatus.UNPAID;

        // should not throw an exception
        assertDoesNotThrow(() -> new Finance(validAmount, type, status));

        Finance finance = new Finance(validAmount, type, status);
        assertEquals(validAmount, finance.getAmount());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        FinanceAmount validAmount = new FinanceAmount("10.00");
        FinanceType type = FinanceType.PER_MONTH;
        FinanceStatus status = FinanceStatus.UNPAID;

        assertThrows(NullPointerException.class, () -> new Finance(null, type, status));
        assertThrows(NullPointerException.class, () -> new Finance(validAmount, null, status));
        assertThrows(NullPointerException.class, () -> new Finance(validAmount, type, null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount1 = "";
        String invalidAmount2 = "2000000.00";
        FinanceType type = FinanceType.PER_MONTH;
        FinanceStatus status = FinanceStatus.UNPAID;

        assertThrows(IllegalArgumentException.class, () -> new FinanceAmount(invalidAmount1));
        assertThrows(IllegalArgumentException.class, () -> new FinanceAmount(invalidAmount2));
    }

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> FinanceAmount.isValidAmount(null));
    }

    @Test
    public void equalsTest() {
        FinanceAmount amount1 = new FinanceAmount("10.00");
        FinanceAmount amount2 = new FinanceAmount("10.00");
        FinanceAmount amount3 = new FinanceAmount("20.00");
        FinanceType type = FinanceType.PER_MONTH;
        FinanceStatus status = FinanceStatus.UNPAID;

        Finance finance1 = new Finance(amount1, type, status);
        Finance finance2 = new Finance(amount2, type, status);
        Finance finance3 = new Finance(amount3, type, status);

        // same content → should be equal
        assertEquals(finance1, finance1);
        assertEquals(finance1, finance2);

        // different amount → not equal
        assertNotEquals(finance1, finance3);
        assertNotEquals(finance1, null);
    }

    @Test
    public void hashCodeTest() {
        FinanceAmount amount1 = new FinanceAmount("10.00");
        FinanceAmount amount2 = new FinanceAmount("10.00");
        FinanceAmount amount3 = new FinanceAmount("20.00");
        FinanceType type = FinanceType.PER_MONTH;
        FinanceStatus status = FinanceStatus.UNPAID;

        Finance finance1 = new Finance(amount1, type, status);
        Finance finance2 = new Finance(amount2, type, status);
        Finance finance3 = new Finance(amount3, type, status);

        // consistent hashCode for equal objects
        assertEquals(finance1.hashCode(), finance2.hashCode());

        // typically should differ, though not guaranteed strictly by contract
        assertNotEquals(finance1.hashCode(), finance3.hashCode());
    }

    @Test
    public void toStringTest() {
        FinanceAmount amount = new FinanceAmount("10.00");
        FinanceType type = FinanceType.PER_MONTH;
        FinanceStatus status = FinanceStatus.UNPAID;

        Finance finance = new Finance(amount, type, status);
        String expected = "[Amount: 10.00, Type: Monthly, Status: Unpaid]";
        assertEquals(expected, finance.toString());
    }

    @Test
    public void getters_validFinance_success() {
        FinanceAmount amount = new FinanceAmount("100.50");
        FinanceType type = FinanceType.PER_LESSON;
        FinanceStatus status = FinanceStatus.PAID;

        Finance finance = new Finance(amount, type, status);

        assertEquals(amount, finance.getAmount());
        assertEquals(type, finance.getType());
        assertEquals(status, finance.getStatus());
    }

    @Test
    public void equals_differentType_notEqual() {
        FinanceAmount amount = new FinanceAmount("10.00");
        Finance finance1 = new Finance(amount, FinanceType.PER_MONTH, FinanceStatus.UNPAID);
        Finance finance2 = new Finance(amount, FinanceType.PER_LESSON, FinanceStatus.UNPAID);

        assertNotEquals(finance1, finance2);
    }

    @Test
    public void equals_differentStatus_notEqual() {
        FinanceAmount amount = new FinanceAmount("10.00");
        FinanceType type = FinanceType.PER_MONTH;
        Finance finance1 = new Finance(amount, type, FinanceStatus.UNPAID);
        Finance finance2 = new Finance(amount, type, FinanceStatus.PAID);

        assertNotEquals(finance1, finance2);
    }

}
