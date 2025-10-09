package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.text.ParseException;

import seedu.address.logic.commands.ViewOutstandingPaymentsCommand;

public class ViewOutstandingPaymentsCommandParser implements Parser<ViewOutstandingPaymentsCommand>{
    
    public ViewOutstandingPaymentsCommand parse(String args) throws ParseException {
        requireNonNull(args);
        if (!args.trim().isEmpty()) {
            throw new ParseException(String.format(seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT, 
                    ViewOutstandingPaymentsCommand.MESSAGE_USAGE));
        }
        return new ViewOutstandingPaymentsCommand();
    }
}
