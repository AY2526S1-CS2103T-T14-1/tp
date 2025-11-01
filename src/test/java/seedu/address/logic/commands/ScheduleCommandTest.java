//Generated with AI-assistance (ChatGPT)
package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.lesson.LessonName;
import seedu.address.model.lesson.Location;
import seedu.address.model.lesson.Time;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Tests for {@link ScheduleCommand}.
 *
 * These tests:
 *  - do not assert exact calendar dates (ScheduleCommand uses LocalDate.now());
 *  - check headers (day names) and ordering within a day by time;
 *  - verify presence of lesson details in the output.
 */
public class ScheduleCommandTest {

    // -------- helpers --------

    private static Person withLesson(String studentName,
                                     String weekday,
                                     String hhmm,
                                     String location,
                                     String lessonName) {
        return new Person(
                new Name(studentName),
                new Phone("99999999"),
                new Email(studentName.toLowerCase().replace(" ", "") + "@example.com"),
                new Address("blk 1"),
                new HashSet<>(),
                Optional.of(new Lesson(
                        new LessonName(lessonName),
                        new Date(weekday),
                        new Time(hhmm),
                        new Location(location)
                )),
                Optional.empty()
        );
    }

    private static Model emptyModel() {
        return new ModelManager(new AddressBook(), new UserPrefs());
    }

    @Test
    public void execute_noLessons_printsEmptyMsg() throws Exception {
        Model model = emptyModel();
        // ensure filtered list is applied (empty here anyway)
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        CommandResult result = new ScheduleCommand().execute(model);
        String out = result.getFeedbackToUser();

        assertTrue(out.startsWith("NO LESSONS FOUND THIS WEEK!"),
                "Should print the empty-week message.");
    }

    @Test
    public void execute_sortsWithinSameDay_byTimeAscending() throws Exception {
        Model model = emptyModel();
        // Two on Wednesday, out of chronological insertion order
        model.addPerson(withLesson("Cara",
                "Wednesday",
                "10:30",
                "Home",
                "English"));
        model.addPerson(withLesson("Dan",
                "Wednesday",
                "08:45",
                "RoomB",
                "Physics"));
        model.addPerson(withLesson("Eli",
                "Wednesday",
                "09:15",
                "Cafe",
                "Geog"));
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        CommandResult result = new ScheduleCommand().execute(model);
        String out = result.getFeedbackToUser();

        // Ensure all three are present
        int iDan = out.indexOf("[Dan]");
        int iEli = out.indexOf("[Eli]");
        int iCara = out.indexOf("[Cara]");

        // All must exist and be ordered by time: Dan (08:45) < Eli (09:15) < Cara (10:30)
        assertTrue(iDan != -1 && iEli != -1 && iCara != -1,
                "All three entries should be printed.");
        assertTrue(iDan < iEli && iEli < iCara,
                "Entries should be ordered by time within the same day.");
    }

    @Test
    public void execute_includesTimeNameLessonLocation() throws Exception {
        Model model = emptyModel();
        model.addPerson(withLesson("Faye",
                "Friday",
                "18:20",
                "Studio",
                "Art"));
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        CommandResult result = new ScheduleCommand().execute(model);
        String out = result.getFeedbackToUser();

        // Check presence of time, student name, lesson name and location
        assertTrue(out.contains("18:20"), "Should show lesson time.");
        assertTrue(out.contains("[Faye]"), "Should show student name in brackets.");
        assertTrue(out.contains("Art"), "Should show lesson name.");
        assertTrue(out.contains("Studio"), "Should show location.");
    }

    @Test
    public void execute_withActiveFilter_showsAllLessons() throws Exception {
        Model model = emptyModel();
        // Add multiple students with lessons
        model.addPerson(withLesson("Alex Yeoh",
                "Monday",
                "09:00",
                "Room A",
                "Math"));
        model.addPerson(withLesson("Bernice Yu",
                "Tuesday",
                "14:00",
                "Room B",
                "Science"));
        model.addPerson(withLesson("Charlotte Oliveiro",
                "Wednesday",
                "16:00",
                "Room C",
                "English"));

        // Apply filter to show only "Alex"
        model.updateFilteredPersonList(person -> person.getName().fullName.contains("Alex"));

        // Execute schedule command
        CommandResult result = new ScheduleCommand().execute(model);
        String out = result.getFeedbackToUser();

        // Verify ALL lessons are shown, not just filtered ones
        assertTrue(out.contains("[Alex Yeoh]"), "Should show Alex's lesson.");
        assertTrue(out.contains("[Bernice Yu]"), "Should show Bernice's lesson despite filter.");
        assertTrue(out.contains("[Charlotte Oliveiro]"), "Should show Charlotte's lesson despite filter.");
        assertTrue(out.contains("Math"), "Should show Math lesson.");
        assertTrue(out.contains("Science"), "Should show Science lesson.");
        assertTrue(out.contains("English"), "Should show English lesson.");
    }
}
