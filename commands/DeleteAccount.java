package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;

public final class DeleteAccount implements  Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {
        if (!bankSystem.deleteAccount(input)) {
            ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
            ObjectNode commandNode1 = JsonNodeFactory.instance.objectNode();

            commandNode.put("command", "deleteAccount");
            commandNode1.put("error",
                    "Account couldn't be deleted - see org.poo.transactions for details");
            commandNode1.put("timestamp", input.getTimestamp());
            commandNode.putPOJO("output", commandNode1);
            commandNode.put("timestamp", input.getTimestamp());
            output.add(commandNode);
        } else {
            ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
            ObjectNode commandNode1 = JsonNodeFactory.instance.objectNode();

            commandNode.put("command", "deleteAccount");
            commandNode1.put("success", "Account deleted");
            commandNode1.put("timestamp", input.getTimestamp());
            commandNode.putPOJO("output", commandNode1);
            commandNode.put("timestamp", input.getTimestamp());
            output.add(commandNode);
        }
    }
}
