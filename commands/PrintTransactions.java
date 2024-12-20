package org.poo.commands;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.clients.Client;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;
import org.poo.transactions.Transaction;

public final class PrintTransactions implements Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {
        ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
        commandNode.put("command", "printTransactions");

        ArrayNode transactionsArray = JsonNodeFactory.instance.arrayNode();

        ObjectMapper objectMapper = new ObjectMapper();

        for (Client clients : bankSystem.getClients()) {
            if (clients.getEmail().equals(input.getEmail())) {
                for (Transaction transaction : clients.getTransactions()) {
                    ObjectNode transactionNode = objectMapper
                            .convertValue(transaction, ObjectNode.class);

                    transactionsArray.add(transactionNode);
                }
            }
        }

        commandNode.set("output", transactionsArray);
        commandNode.put("timestamp", input.getTimestamp());

        output.add(commandNode);
    }
}
