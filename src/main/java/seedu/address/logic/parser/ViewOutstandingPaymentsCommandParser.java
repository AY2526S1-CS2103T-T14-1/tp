package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.parser.exceptions.ParseException;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewOutstandingPaymentsCommand;

public class ViewOutstandingPaymentsCommandParser implements Parser<ViewOutstandingPaymentsCommand>{

    public ViewOutstandingPaymentsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewOutstandingPaymentsCommand.MESSAGE_USAGE));
        }
        return new ViewOutstandingPaymentsCommand();
    }
}
