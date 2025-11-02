package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.lesson.Lesson;
import seedu.address.model.person.Person;

/**
 * Shows this week's schedule (Mon..Sun), sorted by date and time.
 */
public class ScheduleCommand extends Command {
    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows all lessons in the current week (Mon–Sun), "
            + "sorted by day and time.\n"
            + "Example: " + COMMAND_WORD;

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        LocalDate today = LocalDate.now();
        LocalDate monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = monday.plusDays(6);

        List<Person> personList = model.getAddressBook().getPersonList();
        List<Row> rows = new ArrayList<>();

        for (Person p : personList) {
            Optional<Lesson> optional = p.getLesson();
            if (optional.isEmpty()) {
                continue;
            }

            Lesson lesson = optional.get();

            DayOfWeek dayOfWeek = lesson.getDate().asDayOfWeek();
            LocalDate lessonDate = monday.plusDays(dayOfWeek.getValue() - DayOfWeek.MONDAY.getValue());
            LocalDateTime lessonStart = LocalDateTime.of(lessonDate, lesson.getTime().asLocalTime());

            rows.add(new Row(lessonStart, p, lesson));
        }

        rows.sort(Comparator.comparing(Row::getStart));

        if (rows.isEmpty()) {
            return new CommandResult("NO LESSONS FOUND THIS WEEK!");
        }

        String output = "Weekly schedule (" + monday + " to " + sunday + "):\n";

        DayOfWeek current = null;
        for (Row r : rows) {
            DayOfWeek day = r.getStart().getDayOfWeek();
            if (current != day) {
                current = day;
                output += "\n" + day.name() + " " + r.getStart().toLocalDate() + "\n";
            }

            output += " " + r.getStart().toLocalTime()
                    + " - [" + r.getPerson().getName() + "] "
                    + r.getLesson().getLessonName()
                    + " @ " + r.getLesson().getLocation()
                    + "\n";
        }
        return new CommandResult(output, false, false, true);
    }

    private static final class Row {
        private final LocalDateTime start;
        private final Person person;
        private final Lesson lesson;

        public Row(LocalDateTime start, Person person, Lesson lesson) {
            this.start = start;
            this.person = person;
            this.lesson = lesson;
        }

        public LocalDateTime getStart() {
            return start;
        }

        public Lesson getLesson() {
            return lesson;
        }

        public Person getPerson() {
            return person;
        }
    }
}
