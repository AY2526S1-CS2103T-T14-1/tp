package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Lesson;
import seedu.address.logic.commands.AddLessonCommand.AddLessonDescriptor;

/**
 * Parses input arguments and creates a new AddLessonCommand object
 */
public class AddLessonCommandParser implements Parser<AddLessonCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddLessonCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddLessonCommand.
     *
     * @param args Arguments.
     * @return A AddLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLessonCommand parse(String args) throws ParseException {
        requireNonNull(args);
        logger.log(Level.FINE, "Parsing AddLessonCommand with args: {0}", args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            logger.log(Level.WARNING, "Invalid index provided in AddLessonCommand input: {0}", args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE), pe);
        }
        assert index.getOneBased() > 0 : "Index must be positive";

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE, PREFIX_TIME, PREFIX_LOCATION);

        AddLessonDescriptor addLessonDescriptor = new AddLessonDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isEmpty()
                || argMultimap.getValue(PREFIX_DATE).isEmpty()
                || argMultimap.getValue(PREFIX_TIME).isEmpty()
                || argMultimap.getValue(PREFIX_LOCATION).isEmpty()) {
            logger.log(Level.WARNING, "Missing required lesson fields in AddLessonCommand: {0}", args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddLessonCommand.MESSAGE_USAGE));
        }

        String lessonName = argMultimap.getValue(PREFIX_NAME).get();
        String date = argMultimap.getValue(PREFIX_DATE).get();
        String time = argMultimap.getValue(PREFIX_TIME).get();
        String location = argMultimap.getValue(PREFIX_LOCATION).get();
        logger.log(Level.FINE, "Extracted lesson fields - Name: {0}, Date: {1}, Time: {2}, Location: {3}",
                new Object[]{lessonName, date, time, location});
        Lesson lesson = ParserUtil.parseLesson(lessonName, date, time, location);

        addLessonDescriptor.setLesson(lesson);
        logger.log(Level.INFO, "Successfully parsed AddLessonCommand for index {0} with lesson {1}",
                new Object[]{index, lesson});

        return new AddLessonCommand(index, addLessonDescriptor);
    }
}
