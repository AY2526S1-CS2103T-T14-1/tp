package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_DESC_3;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_DESC_4;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_NAME_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_NAME_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_NAME_DESC_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_NAME_DESC_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_NAME_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.AddLessonCommand.AddLessonDescriptor;
import seedu.address.model.lesson.Date;
import seedu.address.model.lesson.LessonName;
import seedu.address.model.lesson.Location;
import seedu.address.model.lesson.Time;
import seedu.address.testutil.AddLessonDescriptorBuilder;

public class AddLessonCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);

    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_LESSON_DESC, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_LESSON_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_LESSON_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_LESSON_DESC_1, LessonName.MESSAGE_CONSTRAINTS); // invalid lesson name
        assertParseFailure(parser, "1" + INVALID_LESSON_DESC_2, Date.MESSAGE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_LESSON_DESC_3, Time.MESSAGE_CONSTRAINTS); // invalid time
        assertParseFailure(parser, "1" + INVALID_LESSON_DESC_4, Location.MESSAGE_CONSTRAINTS); // invalid location

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_LESSON_NAME_DESC_1 + INVALID_DATE_DESC + VALID_TIME_DESC
                + VALID_LOCATION_DESC, LessonName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + VALID_LESSON_DESC;

        AddLessonDescriptor descriptor = new AddLessonDescriptorBuilder()
                .withLesson(VALID_LESSON_NAME_MATH, VALID_DATE, VALID_TIME, VALID_LOCATION).build();
        AddLessonCommand expectedCommand = new AddLessonCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_LESSON_DESC_1 + VALID_LESSON_NAME_DESC_1;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + VALID_LESSON_NAME_DESC_1 + INVALID_LESSON_DESC_1;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + VALID_LESSON_NAME_DESC_1 + VALID_LESSON_NAME_DESC_2 + VALID_DATE_DESC
                + VALID_TIME_DESC + VALID_LOCATION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_LESSON_NAME_DESC_1 + INVALID_LESSON_NAME_DESC_2
                + INVALID_DATE_DESC + INVALID_TIME_DESC + INVALID_LOCATION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
