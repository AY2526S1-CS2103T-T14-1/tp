package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));

        // invalid tag names
        assertFalse(Tag.isValidTagName("")); // empty string
        assertFalse(Tag.isValidTagName(" ")); // spaces only
        assertFalse(Tag.isValidTagName("^")); // only non-alphanumeric characters
        assertFalse(Tag.isValidTagName("peter*")); // contains non-alphanumeric characters
        assertFalse(Tag.isValidTagName("a".repeat(16))); // exceeds max length of 15

        // valid tag names
        assertTrue(Tag.isValidTagName("friend")); // alphabets only
        assertTrue(Tag.isValidTagName("12345")); // numbers only
        assertTrue(Tag.isValidTagName("friend2")); // alphanumeric characters
        assertTrue(Tag.isValidTagName("a".repeat(15))); // exactly max length of 15
    }

}
