package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.poo.accounts.Account;
import org.poo.clients.Client;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;
import org.poo.transactions.Transaction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

public final class Report implements Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {
        boolean found = false;
        ObjectNode commandNode = JsonNodeFactory.instance.objectNode();
        commandNode.put("command", "report");
        ObjectNode commandNode1 = JsonNodeFactory.instance.objectNode();
        for (Client client : bankSystem.getClients()) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())) {
                    found = true;
                    commandNode1.put("IBAN", account.getIban());
                    commandNode1.put("balance", account.getBalance());
                    commandNode1.put("currency", account.getCurrecncy());

                    TreeSet<Transaction> transactions = new TreeSet<>(
                            Comparator.comparingInt(Transaction::getTimestamp)
                    );
                    transactions.addAll(client.getTransactions());
                    ArrayList<Transaction> transactions1 = new ArrayList<>(transactions);
                    transactions1.sort(Comparator.comparingInt(Transaction::getTimestamp));

                    ArrayList<Transaction> transactions2 = new ArrayList<>();
                    for (Transaction transaction : transactions1) {
                        if (transaction.getTimestamp() >= input.getStartTimestamp()
                                && transaction.getTimestamp() <= input.getEndTimestamp()) {
                            transactions2.add(transaction);
                        }
                    }
                    Iterator<Transaction> iterator = transactions2.iterator();
                    while (iterator.hasNext()) {
                        Transaction transaction = iterator.next();
                        if (!transaction.getAccount().isEmpty()
                                && !transaction.getAccount().equals(account.getIban())) {
                            iterator.remove();
                        }
                    }
                    commandNode1.putPOJO("transactions", transactions2);
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
