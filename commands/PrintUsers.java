package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.clients.Client;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;

public final class PrintUsers implements Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {

        ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
        commandNode.put("command", "printUsers");


        ArrayNode clientsArray = JsonNodeFactory.instance.arrayNode();

        for (Client client : bankSystem.getClients()) {

            ObjectNode clientNode = JsonNodeFactory.instance.objectNode();
            clientNode.put("firstName", client.getFirstName());
            clientNode.put("lastName", client.getLastName());
            clientNode.put("email", client.getEmail());

            ArrayNode accountsArray = JsonNodeFactory.instance.arrayNode();
            for (Account account : client.getAccounts()) {
                ObjectNode accountNode = JsonNodeFactory.instance.objectNode();
                accountNode.put("IBAN", account.getIban());
                accountNode.put("balance", account.getBalance());
                accountNode.put("currency", account.getCurrecncy());
                accountNode.put("type", account.getType());

                ArrayNode cardsArray = JsonNodeFactory.instance.arrayNode();
                for (Card card : account.getCards()) {
                    ObjectNode cardNode = JsonNodeFactory.instance.objectNode();
                    cardNode.put("cardNumber", card.getCardNumber());
                    cardNode.put("status", card.getStatus());
                    cardsArray.add(cardNode);
                }
                accountNode.set("cards", cardsArray);

                accountsArray.add(accountNode);
            }
            clientNode.set("accounts", accountsArray);

            clientsArray.add(clientNode);
        }

        commandNode.set("output", clientsArray);
        commandNode.put("timestamp", input.getTimestamp());
        output.add(commandNode);
    }
}
