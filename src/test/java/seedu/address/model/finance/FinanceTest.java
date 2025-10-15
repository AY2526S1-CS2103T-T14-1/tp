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

        // should not throw an exception
        assertDoesNotThrow(() -> new Finance(validAmount));

        Finance finance = new Finance(validAmount);
        assertEquals(validAmount, finance.getOwedAmount());
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Finance(null));
    }

    @Test
    public void constructor_noArgs_createsZeroOwedAmount() {
        Finance finance = new Finance();
        assertEquals(0.0, finance.getOwedAmount().getAmount());
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidAmount1 = "";
        String invalidAmount2 = "2000000.00";

        assertThrows(IllegalArgumentException.class, () -> new FinanceAmount(invalidAmount1));
        assertThrows(IllegalArgumentException.class, () -> new FinanceAmount(invalidAmount2));
    }

    @Test
    public void isValidAmount() {
        // null amount
        assertThrows(NullPointerException.class, () -> FinanceAmount.isValidAmount(null));
    }

    @Test
    public void add_validAmount_success() {
        Finance finance = new Finance(new FinanceAmount("10.00"));
        Finance newFinance = finance.add(new FinanceAmount("5.00"));

        assertEquals(15.0, newFinance.getOwedAmount().getAmount());
        // Original finance should be unchanged (immutable)
        assertEquals(10.0, finance.getOwedAmount().getAmount());
    }

    @Test
    public void pay_validAmount_success() {
        Finance finance = new Finance(new FinanceAmount("10.00"));
        Finance newFinance = finance.pay(new FinanceAmount("5.00"));

        assertEquals(5.0, newFinance.getOwedAmount().getAmount());
        // Original finance should be unchanged (immutable)
        assertEquals(10.0, finance.getOwedAmount().getAmount());
    }

    @Test
    public void pay_amountGreaterThanOwed_returnsZero() {
        Finance finance = new Finance(new FinanceAmount("10.00"));
        Finance newFinance = finance.pay(new FinanceAmount("15.00"));

        assertEquals(0.0, newFinance.getOwedAmount().getAmount());
    }

    @Test
    public void equalsTest() {
        FinanceAmount amount1 = new FinanceAmount("10.00");
        FinanceAmount amount2 = new FinanceAmount("10.00");
        FinanceAmount amount3 = new FinanceAmount("20.00");

        Finance finance1 = new Finance(amount1);
        Finance finance2 = new Finance(amount2);
        Finance finance3 = new Finance(amount3);

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

        Finance finance1 = new Finance(amount1);
        Finance finance2 = new Finance(amount2);
        Finance finance3 = new Finance(amount3);

        // consistent hashCode for equal objects
        assertEquals(finance1.hashCode(), finance2.hashCode());

        // typically should differ, though not guaranteed strictly by contract
        assertNotEquals(finance1.hashCode(), finance3.hashCode());
    }

    @Test
    public void toStringTest() {
        FinanceAmount amount = new FinanceAmount("10.00");

        Finance finance = new Finance(amount);
        String expected = "[Owed Amount: 10.00]";
        assertEquals(expected, finance.toString());
    }

    @Test
    public void getters_validFinance_success() {
        FinanceAmount amount = new FinanceAmount("100.50");

        Finance finance = new Finance(amount);

        assertEquals(amount, finance.getOwedAmount());
    }

}
