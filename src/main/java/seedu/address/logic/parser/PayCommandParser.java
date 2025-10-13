package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PayCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class PayCommandParser implements Parser<PayCommand> {

    public PayCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PayCommand.MESSAGE_USAGE));
        }

    }
}
