package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

public final class DestroyCardTr extends Transaction {
    private final String account;
    private final String card;
    private final String cardHolder;
    private final String description = "The card has been destroyed";
    private final int timestamp;

    public DestroyCardTr(final CommandInput input, final String card, final String account) {
        this.account = account;
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
