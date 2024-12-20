package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class CardStatusTr extends Transaction {
    private final int timestamp;
    private final String description;

    public CardStatusTr(final int timestamp, final String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    @JsonIgnore
    public String getCommerciant() {
        return "";
    }

    @JsonIgnore
    public String getAccount() {
        return "";
    }

    @JsonIgnore
    public double getComAmount() {
        return 0;
    }
}
