package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;

public final class ChangeInterestRate implements Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {
        if (!bankSystem.changeInterestRate(input)) {
            ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
            commandNode.put("command", "changeInterestRate");
            ObjectNode commandNode1 = JsonNodeFactory.instance.objectNode();
            commandNode1.put("timestamp", input.getTimestamp());
            commandNode1.put("description", "This is not a savings account");
            commandNode.putPOJO("output", commandNode1);
            commandNode.put("timestamp", input.getTimestamp());
            output.add(commandNode);
        }
    }
}
