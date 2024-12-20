package org.poo.system;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.accounts.Account;
import org.poo.cards.Card;
import org.poo.clients.Client;
import org.poo.commands.Commands;
import org.poo.commerciants.Commerciant;
import org.poo.fileio.CommandInput;
import org.poo.fileio.ObjectInput;
import org.poo.fileio.UserInput;
import org.poo.transactions.*;
import org.poo.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;

public final class BankSystem {
    private ArrayList<Client> clients = new ArrayList<>();
    private ArrayList<ExchangeRate> exchangeRates = new ArrayList<>();

    private final int warningAmount = 30;

    public ArrayList<Client> getClients() {
        return clients;
    }

    public BankSystem(final ObjectInput input) {
        for (UserInput userInput : input.getUsers()) {
            clients.add(new Client(userInput));
        }
        for (int i = 0; i < input.getExchangeRates().length; i++) {
            exchangeRates.add(new ExchangeRate(input.getExchangeRates()[i]));
        }
    }

    /**
     * start executing the commands
     * @param input
     * @param output
     */
    public void start(final ObjectInput input, final ArrayNode output) {
        Utils.resetRandom();
        Commands commands = new Commands();
        for (CommandInput commandInput : input.getCommands()) {
            commands.execute(commandInput, output, this);
        }
    }

    /**
     * add a new account
     * @param input
     */
    public void addAccount(final CommandInput input) {
        for (Client client : clients) {
            if (client.getEmail().equals(input.getEmail())) {
                client.addClientAccount(input);
                client.getTransactions().add(new AddAccountTr(input.getTimestamp()));
            }
        }
    }

    /**
     * add funds to an account
     * @param input
     */
    public void addFunds(final CommandInput input) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())) {
                    account.addAccountFunds(input.getAmount());
                }
            }
        }
    }

    /**
     * add a new card
     * @param input
     */
    public void addCard(final CommandInput input) {
        for (Client client : clients) {
            if (client.getEmail().equals(input.getEmail())) {
                for (Account account : client.getAccounts()) {
                    if (account.getIban().equals(input.getAccount())) {
                        account.addAccountCard(input);
                        client.getTransactions().add(new AddCardTr(input, account.getCards()
                                .get(account.getCards().size() - 1).getCardNumber()));
                    }
                }
            }
        }
    }

    /**
     * delete an account
     * @param input
     * @return
     */
    public boolean deleteAccount(final CommandInput input) {
        for (Client client : clients) {
            if (client.getEmail().equals(input.getEmail())) {
                for (Account account : client.getAccounts()) {
                    if (account.getIban().equals(input.getAccount())) {
                        if (account.getBalance() != 0) {
                            client.getTransactions().add(new DelAccRemBalTr(input));
                            return false;
                        }
                        account.getCards().clear();
                        client.getAccounts().remove(account);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * add a one time card
     * @param input
     */
    public void addOneTimeCard(final CommandInput input) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())) {
                    account.addAccountOneTimeCard(input);
                    client.getTransactions().add(new AddCardTr(input, account.getCards()
                            .get(account.getCards().size() - 1).getCardNumber()));
                }
            }
        }
    }

    /**
     * delete a card
     * @param input
     * @return
     */
    public boolean deleteCard(final CommandInput input) {
        for (Client client : clients) {
           if (client.getEmail().equals(input.getEmail())) {
               for (Account account : client.getAccounts()) {
                   for (int i = 0; i < account.getCards().size(); i++) {
                       if (account.getCards().get(i).getCardNumber()
                               .equals(input.getCardNumber())) {
                           account.getCards().remove(i);
                            client.getTransactions()
                                      .add(new DestroyCardTr(input, input.getCardNumber(),
                                              account.getIban()));
                           return true;
                       }
                   }
               }
           }
        }
        return false;
    }

    /**
     * set the minimum amount
     * @param input
     * @return
     */
    public boolean setMinimumBalance(final CommandInput input) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())) {
                    account.setMinimumBalance(input.getAmount());
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * send money from an account to another
     * @param input
     */
    public void sendMoney(final CommandInput input) {
        Account sender = null;
        Account receiver = null;

        Client senderClient = null;
        Client receiverClient = null;

        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())) {
                    sender = account;
                    senderClient = client;
                }
                if (account.getIban().equals(input.getReceiver())
                        || account.getAlias().equals(input.getReceiver())) {
                    receiver = account;
                    receiverClient = client;
                }
            }
        }
        if (sender == null || receiver == null) {
            return;
        }
        if (sender.getBalance() < input.getAmount()) {
            senderClient.getTransactions()
                    .add(new InsufficientTr(input.getTimestamp()));
            return;
        }

        double newAmount = ExchangeRate.convertAmount(
                exchangeRates, sender.getCurrecncy(), receiver.getCurrecncy(), input.getAmount());

        senderClient.getTransactions()
                .add(new SendMoneyTr(input, "sent", sender.getCurrecncy(), input.getAmount()));
        receiverClient.getTransactions()
                .add(new SendMoneyTr(input, "received", receiver.getCurrecncy(), newAmount));

        sender.setBalance(sender.getBalance() - input.getAmount());
        receiver.setBalance(receiver.getBalance() + newAmount);
    }

    /**
     * pay online with a card
     * @param input
     * @return
     */
    public boolean onlinePayment(final CommandInput input) {
        for (Client client : clients) {
            if (client.getEmail().equals(input.getEmail())) {
                for (Account account : client.getAccounts()) {
                    Iterator<Card> cardIterator = account.getCards().iterator();

                    while (cardIterator.hasNext()) {
                        Card card = cardIterator.next();

                        if (card.getCardNumber().equals(input.getCardNumber())
                                && card.getStatus().equals("frozen")) {
                            client.getTransactions()
                                    .add(new CardStatusTr(input.getTimestamp(),
                                            "The card is frozen"));
                            return true;
                        }

                        if (card.getCardNumber().equals(input.getCardNumber())
                                && card.getStatus().equals("active")) {

                            double newAmount = ExchangeRate.convertAmount(
                                    this.exchangeRates, input.getCurrency(),
                                    account.getCurrecncy(),
                                    input.getAmount());

                            if (account.getBalance() - newAmount <= account.getMinimumBalance()
                                    && account.getBalance() - newAmount >= 0) {
                                card.setStatus("frozen");
                                client.getTransactions()
                                        .add(new CardStatusTr(input.getTimestamp(),
                                                "The card is frozen"));
                                return true;
                            }

                            if (account.getBalance() - newAmount >= 0) {

                                account.setBalance(account.getBalance() - newAmount);

                                client.getTransactions()
                                        .add(new CardPaymentTr(input, newAmount));

                                account.getCommerciant().add(new Commerciant(input, newAmount));

                                if (account.getBalance() <= warningAmount) {
                                    card.setStatus("warning");
                                }

                                if (card.getType().equals("OneTime")) {
                                    client.getTransactions()
                                            .add(new DestroyCardTr(input, input.getCardNumber(),
                                                    account.getIban()));
                                    cardIterator.remove();
                                    account.addAccountOneTimeCard(input);
                                    client.getTransactions()
                                            .add(new AddCardTr(input, account.getCards()
                                                    .get(account.getCards().size() - 1)
                                                    .getCardNumber(), account.getIban()));
                                }

                                return true;
                            }
                                client.getTransactions()
                                        .add(new InsufficientTr(input.getTimestamp()));
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * set the alias of an account
     * @param input
     */
    public void setAlias(final CommandInput input) {
        for (Client client : clients) {
            if (client.getEmail().equals(input.getEmail())) {
                for (Account account : client.getAccounts()) {
                    if (account.getIban().equals(input.getAccount())) {
                        account.setAlias(input.getAlias());
                    }
                }
            }
        }
    }

    /**
     * change the interest rate of an account
     * @param input
     */
    public boolean changeInterestRate(final CommandInput input) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())
                        && account.getType().equals("savings")) {
                    account.setInterestRate(input.getInterestRate());
                    client.getTransactions().add(new ChangeIRTr(input));
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * split the payment
     * @param input
     */
    public void splitPayment(final CommandInput input) {
        double toPay = input.getAmount() / input.getAccounts().size();
        String acc = "";

        for (String account : input.getAccounts()) {
            for (Client client : clients) {
                for (Account acco : client.getAccounts()) {
                    if (acco.getIban().equals(account)) {
                        if (acco.getBalance() < ExchangeRate.convertAmount(
                                exchangeRates, input.getCurrency(), acco.getCurrecncy(), toPay)) {
                            acc = account;
                        }
                    }
                }
            }
        }

        if (!acc.isEmpty()) {
            for (Client client : clients) {
                for (Account account : client.getAccounts()) {
                    if (input.getAccounts().contains(account.getIban())) {
                        client.getTransactions().add(new SplitPayError(input, acc));
                    }
                }
            }
            return;
        }

            for (Client client : clients) {
                for (Account account : client.getAccounts()) {
                    if (input.getAccounts().contains(account.getIban())) {
                        double newToPay = ExchangeRate.convertAmount(
                                exchangeRates, input.getCurrency(), account.getCurrecncy(), toPay);
                        account.setBalance(account.getBalance() - newToPay);
                        client.getTransactions().add(new SplitPayTr(input));
                    }
                }
            }
    }

    /**
     * add interest to an account
     * @param input
     * @return
     */
    public boolean addInterest(final CommandInput input) {
        for (Client client : clients) {
            for (Account account : client.getAccounts()) {
                if (account.getIban().equals(input.getAccount())
                        && account.getType().equals("savings")) {
                    account.addInterest();
                    return true;
                }
            }
        }
        return false;
    }
}
