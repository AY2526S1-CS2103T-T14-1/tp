package seedu.address.logic.parser;

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

        // ✅ Step 1: Parse index (first argument)
        String preamble = map.getPreamble().trim();
        if (preamble.isEmpty()) {
            throw new ParseException("Error: Missing student index. Usage: addfee INDEX amt/AMOUNT");
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(preamble);
        } catch (ParseException pe) {
            throw new ParseException("Error: Invalid index format. Index must be a positive integer.");
        }

        // ✅ Step 2: Parse amount
        String amountStr = map.getValue(PREFIX_AMOUNT)
                .orElseThrow(() -> new ParseException(
                        "Error: Missing required parameter. Usage: addfee INDEX amt/AMOUNT"))
                .trim();

        if (!FinanceAmount.isValidAmount(amountStr)) {
            throw new ParseException(
                    "Error: Invalid amount format. Use a positive number with up to 2 decimal places.");
        }

        double amt = Double.parseDouble(amountStr);
        if (amt <= 0.0 || amt > 100000.0) {
            throw new ParseException(
                    "Error: Amount must be greater than 0 and less than or equal to 100,000.");
        }

        FinanceAmount amount = new FinanceAmount(amountStr);
        return new AddFeeCommand(index, amount);
    }
}
