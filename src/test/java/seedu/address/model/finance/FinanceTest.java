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
        String validAmount = "10.00";

        // should not throw an exception
        assertDoesNotThrow(() -> new Finance(validAmount));

        Finance lesson = new Finance(validAmount);
        assertEquals(Double.parseDouble(validAmount), lesson.amount);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Finance(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount1 = "";
        String invalidAmount2 = "-10.00";
        String invalidAmount3 = "2000000.00";
        assertThrows(IllegalArgumentException.class, () -> new Finance(invalidAmount1));
        assertThrows(IllegalArgumentException.class, () -> new Finance(invalidAmount2));
        assertThrows(IllegalArgumentException.class, () -> new Finance(invalidAmount3));
    }

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> Finance.isValidAmount(null));
    }

    @Test
    public void equalsTest() {
        Finance finance1 = new Finance("10.00");
        Finance finance2 = new Finance("10.00");
        Finance finance3 = new Finance("20.00");

        // same content → should be equal
        assertEquals(finance1, finance1);
        assertEquals(finance1, finance2);

        // different amount → not equal
        assertNotEquals(finance1, finance3);
        assertNotEquals(finance1, null);
    }

    @Test
    public void hashCodeTest() {
        Finance finance1 = new Finance("10.00");
        Finance finance2 = new Finance("10.00");
        Finance finance3 = new Finance("20.00");

        // consistent hashCode for equal objects
        assertEquals(finance1.hashCode(), finance2.hashCode());

        // typically should differ, though not guaranteed strictly by contract
        assertNotEquals(finance1.hashCode(), finance3.hashCode());
    }

    @Test
    public void toStringTest() {
        Finance finance = new Finance("10.00");
        String expected = "[Amount: 10.00]";
        assertEquals(expected, finance.toString());
    }

}
