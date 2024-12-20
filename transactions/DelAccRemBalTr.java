package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

public final class DelAccRemBalTr extends Transaction {
    private final String description;
    private final int timestamp;

    public DelAccRemBalTr(final CommandInput input) {
        this.description = "Account couldn't be deleted - there are funds remaining";
        this.timestamp = input.getTimestamp();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getTimestamp() {
        return timestamp;
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
