package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.clients.Client;
import org.poo.commerciants.Commerciant;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;
import org.poo.transactions.Transaction;

import java.util.ArrayList;
import java.util.Collections;

public final class SpendingsReport implements Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {

        boolean ok = true;
        for (Client client : bankSystem.getClients()) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())
                        && account.getType().equals("savings")) {
                    ok = false;
                }
            }
        }

        if (!ok) {
            ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
            commandNode.put("command", "spendingsReport");
            ObjectNode commandNode1 = JsonNodeFactory.instance.objectNode();
            commandNode1.put("error", "This kind of report is not supported for a saving account");
            commandNode.put("output", commandNode1);
            commandNode.put("timestamp", input.getTimestamp());
            output.add(commandNode);
            return;
        }

        boolean found = false;
        ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
        commandNode.put("command", "spendingsReport");
        ObjectNode commandNode1 = JsonNodeFactory.instance.objectNode();
        for (Client client : bankSystem.getClients()) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())) {
                    found = true;
                    commandNode1.put("IBAN", account.getIban());
                    ArrayList<Commerciant> commerciants = new ArrayList<>();
                    for (Commerciant commerciant : account.getCommerciant()) {
                        if (commerciant.getTimestamp() >= input.getStartTimestamp()
                                && commerciant.getTimestamp() <= input.getEndTimestamp()) {
                            commerciants.add(commerciant);
                        }
                    }

                    Collections.sort(commerciants, (c1, c2) -> c1.getCommerciant().compareTo(c2.getCommerciant()));

                    ArrayList<String> commerciantNames = new ArrayList<>();
                    ArrayList<String> commercianAmounts = new ArrayList<>();
                    for (Commerciant c : commerciants) {
                        commerciantNames.add(c.getCommerciant());
                        commercianAmounts.add(String.valueOf(c.getTotal()));
                    }

                    commandNode1.putPOJO("commerciants", commerciants);
                    commandNode1.put("balance", account.getBalance());
                    commandNode1.put("currency", account.getCurrecncy());
                    ArrayList<Transaction> transactions = new ArrayList<>();

                    if (commerciants.size() != 0) {
                        for (Transaction transaction : client.getTransactions()) {
                            if (transaction.getTimestamp() >= input.getStartTimestamp()
                                    && transaction.getTimestamp() <= input.getEndTimestamp()
                                    && !transaction.getCommerciant().equals("")
                                    && commerciantNames.contains(transaction.getCommerciant())
                                    && commercianAmounts.contains(String
                                    .valueOf(transaction.getComAmount()))) {
                                transactions.add(transaction);
                            }
                        }
                }
                    commandNode1.putPOJO("transactions", transactions);
                }
            }
        }
        if (!found) {
            commandNode1.put("description", "Account not found");
            commandNode1.put("timestamp", input.getTimestamp());
        }
        commandNode.putPOJO("output", commandNode1);
        commandNode.put("timestamp", input.getTimestamp());
        output.add(commandNode);
    }
}
