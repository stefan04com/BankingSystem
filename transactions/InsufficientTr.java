package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class InsufficientTr extends Transaction {
    private final String description = "Insufficient funds";
    private final int timestamp;

    public InsufficientTr(final int timestamp) {
        this.timestamp = timestamp;
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
