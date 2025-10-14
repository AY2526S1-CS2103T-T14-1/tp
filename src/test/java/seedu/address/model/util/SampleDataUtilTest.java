package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;

public class SampleDataUtilTest {

    @Test
    public void getSamplePersons_returnsNonEmptyArray() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertNotNull(samplePersons);
        assertTrue(samplePersons.length > 0);
    }

    @Test
    public void getSamplePersons_allPersonsHaveValidData() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        for (Person person : samplePersons) {
            assertNotNull(person.getName());
            assertNotNull(person.getPhone());
            assertNotNull(person.getEmail());
            assertNotNull(person.getAddress());
            assertNotNull(person.getTags());
        }
    }

    @Test
    public void getSamplePersons_somePersonsHaveFinance() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        boolean hasFinance = false;
        for (Person person : samplePersons) {
            if (person.getFinance().isPresent()) {
                hasFinance = true;
                break;
            }
        }
        assertTrue(hasFinance, "At least one sample person should have finance data");
    }

    @Test
    public void getSamplePersons_somePersonsHaveLesson() {
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        boolean hasLesson = false;
        for (Person person : samplePersons) {
            if (person.getLesson().isPresent()) {
                hasLesson = true;
                break;
            }
        }
        assertTrue(hasLesson, "At least one sample person should have lesson data");
    }

    @Test
    public void getSampleAddressBook_returnsNonNull() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        assertNotNull(sampleAb);
    }

    @Test
    public void getSampleAddressBook_containsSamplePersons() {
        ReadOnlyAddressBook sampleAb = SampleDataUtil.getSampleAddressBook();
        Person[] samplePersons = SampleDataUtil.getSamplePersons();
        assertEquals(samplePersons.length, sampleAb.getPersonList().size());
    }

    @Test
    public void getTagSet_emptyStrings_returnsEmptySet() {
        assertTrue(SampleDataUtil.getTagSet().isEmpty());
    }

    @Test
    public void getTagSet_singleTag_returnsSetWithOneTag() {
        assertEquals(1, SampleDataUtil.getTagSet("friend").size());
    }

    @Test
    public void getTagSet_multipleTags_returnsSetWithMultipleTags() {
        assertEquals(2, SampleDataUtil.getTagSet("friend", "colleague").size());
    }

    @Test
    public void getSamplePersons_consistentData() {
        Person[] samplePersons1 = SampleDataUtil.getSamplePersons();
        Person[] samplePersons2 = SampleDataUtil.getSamplePersons();
        assertEquals(samplePersons1.length, samplePersons2.length);
    }
}
