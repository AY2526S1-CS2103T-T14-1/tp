package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddFeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.FinanceAmount;

/**
 * Parses input arguments and creates a new {@code AddFeeCommand} object.
 */
public class AddFeeCommandParser implements Parser<AddFeeCommand> {

    private static final Prefix PREFIX_AMOUNT = new Prefix("amt/");

    @Override
    public AddFeeCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT);
        map.verifyNoDuplicatePrefixesFor(PREFIX_AMOUNT);

        // Parse index (first argument)
        String preamble = map.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFeeCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddFeeCommand.MESSAGE_USAGE), pe);
        }

        // Parse amount
        String amountStr = map.getValue(PREFIX_AMOUNT)
                .orElseThrow(() -> new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                        AddFeeCommand.MESSAGE_USAGE)));

        if (!FinanceAmount.isValidAmount(amountStr)) {
            throw new ParseException(FinanceAmount.MESSAGE_CONSTRAINTS);
        }

        FinanceAmount amount = new FinanceAmount(amountStr);
        return new AddFeeCommand(index, amount);
    }
}
