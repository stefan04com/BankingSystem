package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.clients.Client;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;
import org.poo.transactions.CardStatusTr;

public final class CheckCardStatus implements Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {
        final String msg = "You have reached the minimum amount of funds, the card will be frozen";
        boolean found = false;
        for (Client client : bankSystem.getClients()) {
            for (Account account : client.getAccounts()) {
                for (Card card : account.getCards()) {
                    if (card.getCardNumber().equals(input.getCardNumber())) {
                        found = true;
                        if (account.getBalance() == account.getMinimumBalance()
                                && account.getBalance() == 0) {
                            client.getTransactions()
                                    .add(new CardStatusTr(input.getTimestamp(), msg));
                        }
                    }
                }
            }
        }
        if (!found) {
            ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
            ObjectNode commandNode1 = JsonNodeFactory.instance.objectNode();
            commandNode.put("command", "checkCardStatus");
            commandNode1.put("description", "Card not found");
            commandNode1.put("timestamp", input.getTimestamp());
            commandNode.putPOJO("output", commandNode1);
            commandNode.put("timestamp", input.getTimestamp());
            output.add(commandNode);
        }
    }
}
