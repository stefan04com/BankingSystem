package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

public final class AddCardTr extends Transaction {
    private final String account;
    private final String card;
    private final String cardHolder;
    private final String description = "New card created";
    private final int timestamp;

    public AddCardTr(final CommandInput input, final String card) {
        this.account = input.getAccount();
        this.card = card;
        this.cardHolder = input.getEmail();
        this.timestamp = input.getTimestamp();
    }

    public AddCardTr(final CommandInput input, final String card, final String acc) {
        this.account = acc;
        this.card = card;
        this.cardHolder = input.getEmail();
        this.timestamp = input.getTimestamp();
    }

    public String getAccount() {
        return account;
    }

    public String getCard() {
        return card;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public String getDescription() {
        return description;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public String getCommerciant() {
        return "";
    }

    @JsonIgnore
    public double getComAmount() {
        return 0;
    }
}
