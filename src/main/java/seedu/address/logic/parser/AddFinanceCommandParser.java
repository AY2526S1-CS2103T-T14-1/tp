package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddFinanceCommand;
import seedu.address.logic.commands.AddFinanceCommand.AddFinanceDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.Finance;

/**
 * Parses input arguments and creates a new AddFinanceCommand object
 */
public class AddFinanceCommandParser implements Parser<AddFinanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddFinanceCommand
     * and returns an AddFinanceCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public AddFinanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddFinanceCommand.MESSAGE_USAGE), ive);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT);

        if (argMultimap.getValue(PREFIX_AMOUNT).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddFinanceCommand.MESSAGE_USAGE));
        }

        AddFinanceDescriptor addFinanceDescriptor = new AddFinanceDescriptor();
        Finance finance = ParserUtil.parseFinance(argMultimap.getValue(PREFIX_AMOUNT).get());
        addFinanceDescriptor.setFinance(finance);

        return new AddFinanceCommand(index, addFinanceDescriptor);
    }
}
