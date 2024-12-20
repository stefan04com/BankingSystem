package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;

public final class AddAccountTr extends Transaction {
    private final int timestamp;
    private final String description = "New account created";

    public AddAccountTr(final int timestamp) {
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
