package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PaymentHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input for the {@code payments} command (no arguments expected).
 */
public class PaymentHistoryCommandParser implements Parser<PaymentHistoryCommand> {
    @Override
    public PaymentHistoryCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PaymentHistoryCommand.MESSAGE_USAGE));
        }
        return new PaymentHistoryCommand();
    }
}
