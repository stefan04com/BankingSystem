package org.poo.accounts;

import org.poo.cards.Card;
import org.poo.commerciants.Commerciant;
import org.poo.fileio.CommandInput;
import org.poo.utils.Utils;

import java.util.ArrayList;

public final class Account {
    private String iban;
    private double balance;
    private String currecncy;
    private String type;
    private ArrayList<Card> cards = new ArrayList<>();

    private ArrayList<Commerciant> commerciants = new ArrayList<>();

    private double interestRate;

    private double minimumBalance;

    private String alias = "";

    public Account(final CommandInput commandInput) {
        this.iban = Utils.generateIBAN();
        this.currecncy = commandInput.getCurrency();
        this.type = commandInput.getAccountType();
        this.balance = 0;
        this.minimumBalance = 0;
        if (this.type.equals("savings")) {
            this.interestRate = commandInput.getInterestRate();
        }
    }

    public ArrayList<Commerciant> getCommerciant() {
        return commerciants;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(final double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * Get the alias of the account
     * @return
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Set the alias of the account
     * @param alias
     */
    public void setAlias(final String alias) {
        this.alias = alias;
    }

    /**
     * Get the minimum balance of the account
     * @return
     */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * Set the minimum balance of the account
     * @param minimumBalance
     */
    public void setMinimumBalance(final double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    /**
     * Get the IBAN of the account
     * @return
     */
    public String getIban() {
        return iban;
    }

    /**
     * Set the IBAN of the account
     * @param iban
     */
    public void setIban(final String iban) {
        this.iban = iban;
    }

    /**
     * Get the balance of the account
     * @return
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Set the balance of the account
     * @param balance
     */
    public void setBalance(final double balance) {
        this.balance = balance;
    }

    /**
     * Get the currency of the account
     * @return
     */
    public String getCurrecncy() {
        return currecncy;
    }

    /**
     * Set the currency of the account
     * @param currecncy
     */
    public void setCurrecncy(final String currecncy) {
        this.currecncy = currecncy;
    }

    /**
     * Get the type of the account
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of the account
     * @param type
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     * Get the cards of the account
     * @return
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Set the cards of the account
     * @param cards
     */
    public void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }

    /**
     * Add funds to the account
     * @param amount
     */
    public void addAccountFunds(final double amount) {
        this.balance += amount;
    }

    /**
     * Add a new card to the account
     * @param input
     */
    public void addAccountCard(final CommandInput input) {
        cards.add(new Card.CardBuilder().setCardNumber().build());
    }

    /**
     * Add a new one time card to the account
     * @param input
     */
    public void addAccountOneTimeCard(final CommandInput input) {
        cards.add(new Card.CardBuilder().setCardNumber().setType("OneTime").build());
    }

    /**
     * Add interest to the account
     * @param
     */
    public void addInterest() {
        this.balance += this.balance * this.interestRate;
    }
}
