package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ViewOutstandingPaymentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parser for the ViewOutstandingPaymentsCommand.
 */
public class ViewOutstandingPaymentsCommandParser implements Parser<ViewOutstandingPaymentsCommand> {

    /**
     * Parses the given {@code args} into a ViewOutstandingPaymentsCommand.
     *
     * @param args the arguments to parse
     * @return the parsed ViewOutstandingPaymentsCommand
     * @throws ParseException if the user input does not conform to the expected format
     */
    public ViewOutstandingPaymentsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ViewOutstandingPaymentsCommand.MESSAGE_USAGE));
        }
        return new ViewOutstandingPaymentsCommand();
    }
}
