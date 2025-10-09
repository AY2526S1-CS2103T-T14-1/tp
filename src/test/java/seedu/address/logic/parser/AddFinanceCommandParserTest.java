package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINANCE_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FINANCE_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFinanceCommand;
import seedu.address.logic.commands.AddFinanceCommand.AddFinanceDescriptor;
import seedu.address.model.finance.Finance;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.FinanceStatus;
import seedu.address.model.finance.FinanceType;

public class AddFinanceCommandParserTest {

    private static final String VALID_AMOUNT = "100.00";
    private static final String VALID_TYPE = "PER_MONTH";
    private static final String VALID_STATUS = "UNPAID";

    private static final String AMOUNT_DESC = " " + PREFIX_AMOUNT + VALID_AMOUNT;
    private static final String TYPE_DESC = " " + PREFIX_FINANCE_TYPE + VALID_TYPE;
    private static final String STATUS_DESC = " " + PREFIX_FINANCE_STATUS + VALID_STATUS;

    private static final String INVALID_AMOUNT_DESC = " " + PREFIX_AMOUNT + "abc";
    private static final String INVALID_TYPE_DESC = " " + PREFIX_FINANCE_TYPE + "INVALID";
    private static final String INVALID_STATUS_DESC = " " + PREFIX_FINANCE_STATUS + "INVALID";

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFinanceCommand.MESSAGE_USAGE);

    private AddFinanceCommandParser parser = new AddFinanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC + TYPE_DESC + STATUS_DESC;

        AddFinanceDescriptor descriptor = new AddFinanceDescriptor();
        Finance finance = new Finance(
                new FinanceAmount(VALID_AMOUNT),
                FinanceType.PER_MONTH,
                FinanceStatus.UNPAID
        );
        descriptor.setFinance(finance);

        AddFinanceCommand expectedCommand = new AddFinanceCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, AMOUNT_DESC + TYPE_DESC + STATUS_DESC, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + AMOUNT_DESC + TYPE_DESC + STATUS_DESC, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + AMOUNT_DESC + TYPE_DESC + STATUS_DESC, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_missingFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // missing amount
        String userInput = targetIndex.getOneBased() + TYPE_DESC + STATUS_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // missing type
        userInput = targetIndex.getOneBased() + AMOUNT_DESC + STATUS_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // missing status
        userInput = targetIndex.getOneBased() + AMOUNT_DESC + TYPE_DESC;
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);

        // all fields missing
        userInput = String.valueOf(targetIndex.getOneBased());
        assertParseFailure(parser, userInput, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // invalid amount
        assertParseFailure(parser, targetIndex.getOneBased() + INVALID_AMOUNT_DESC + TYPE_DESC + STATUS_DESC,
                FinanceAmount.MESSAGE_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, targetIndex.getOneBased() + AMOUNT_DESC + INVALID_TYPE_DESC + STATUS_DESC,
                "Finance type must be either PER_MONTH or PER_LESSON.");

        // invalid status
        assertParseFailure(parser, targetIndex.getOneBased() + AMOUNT_DESC + TYPE_DESC + INVALID_STATUS_DESC,
                "Finance status must be either PAID, UNPAID, or OVERDUE.");
    }

    @Test
    public void parse_duplicateFields_failure() {
        Index targetIndex = INDEX_FIRST_PERSON;

        // duplicate amount
        String userInput = targetIndex.getOneBased() + AMOUNT_DESC + AMOUNT_DESC + TYPE_DESC + STATUS_DESC;
        assertParseFailure(parser, userInput,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(PREFIX_AMOUNT));

        // duplicate type
        userInput = targetIndex.getOneBased() + AMOUNT_DESC + TYPE_DESC + TYPE_DESC + STATUS_DESC;
        assertParseFailure(parser, userInput,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FINANCE_TYPE));

        // duplicate status
        userInput = targetIndex.getOneBased() + AMOUNT_DESC + TYPE_DESC + STATUS_DESC + STATUS_DESC;
        assertParseFailure(parser, userInput,
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(PREFIX_FINANCE_STATUS));
    }
}
