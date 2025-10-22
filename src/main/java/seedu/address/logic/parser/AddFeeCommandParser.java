package seedu.address.logic.parser;

import java.util.regex.Pattern;

import seedu.address.logic.commands.AddFeeCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.finance.FinanceAmount;
import seedu.address.model.finance.FinanceType;

/**
 * Parses input arguments and creates a new {@code AddFeeCommand} object.
 */
public class AddFeeCommandParser implements Parser<AddFeeCommand> {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z\\s\\-']+$");

    @Override
    public AddFeeCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args,
                new Prefix("s/"), new Prefix("t/"), new Prefix("a/"));

        // Required parameters
        String name = map.getValue(new Prefix("s/"))
                .orElseThrow(() -> new ParseException("Error: Missing required parameter. Usage: addfee s/STUDENT_NAME t/[lesson|month] a/AMOUNT"))
                .trim();
        String typeStr = map.getValue(new Prefix("t/"))
                .orElseThrow(() -> new ParseException("Error: Missing required parameter. Usage: addfee s/STUDENT_NAME t/[lesson|month] a/AMOUNT"))
                .trim().toLowerCase();
        String amountStr = map.getValue(new Prefix("a/"))
                .orElseThrow(() -> new ParseException("Error: Missing required parameter. Usage: addfee s/STUDENT_NAME t/[lesson|month] a/AMOUNT"))
                .trim();

        // Validate name
        if (name.isEmpty()) {
            throw new ParseException("Error: Student name cannot be empty. Usage: addfee s/STUDENT_NAME t/[lesson|month] a/AMOUNT");
        }
        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new ParseException("Error: Invalid name format. Only letters, spaces, hyphens (-), and apostrophes (') are allowed.");
        }
        if (name.length() > 100) {
            throw new ParseException("Error: Student name must not exceed 100 characters.");
        }

        // Validate type
        FinanceType type;
        switch (typeStr) {
        case "lesson":
            type = FinanceType.PER_LESSON;
            break;
        case "month":
            type = FinanceType.PER_MONTH;
            break;
        default:
            throw new ParseException("Error: Invalid tuition type. Allowed values are 'lesson' or 'month'.");
        }

        // Validate amount
        if (!FinanceAmount.isValidAmount(amountStr)) {
            throw new ParseException("Error: Invalid amount format. Use a positive number with up to 2 decimal places.");
        }
        double amt = Double.parseDouble(amountStr);
        if (amt <= 0.0 || amt > 100000.0) {
            throw new ParseException("Error: Amount must be greater than 0 and less than or equal to 100,000.");
        }
        FinanceAmount amount = new FinanceAmount(amountStr);

        return new AddFeeCommand(name, type, amount);
    }
}
