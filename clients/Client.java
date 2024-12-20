package org.poo.clients;

import org.poo.accounts.Account;
import org.poo.fileio.CommandInput;
import org.poo.fileio.UserInput;
import org.poo.transactions.Transaction;

import java.util.ArrayList;

public final class Client extends User {
    private ArrayList<Account> accounts = new ArrayList<>();

    private ArrayList<Transaction> transactions = new ArrayList<>();

    public Client(final UserInput user) {
        super(user);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * Add a new account to the client
     * @param input
     */
    public void addClientAccount(final CommandInput input) {
            accounts.add(new Account(input));
    }
}
