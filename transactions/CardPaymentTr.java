package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

public final class CardPaymentTr extends Transaction {
    private final double amount;
    private final String commerciant;
    private final String description = "Card payment";
    private final int timestamp;

    public CardPaymentTr(final CommandInput input, final double amount) {
        this.amount = amount;
        this.commerciant = input.getCommerciant();
        this.timestamp = input.getTimestamp();
    }

    public double getAmount() {
        return amount;
    }

    public String getCommerciant() {
        return commerciant;
    }

    public String getDescription() {
        return description;
    }

    public int getTimestamp() {
        return timestamp;
    }

    @JsonIgnore
    public String getAccount() {
        return "";
    }

    @JsonIgnore
    public double getComAmount() {
        return amount;
    }
}
