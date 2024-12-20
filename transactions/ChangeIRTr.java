package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

public final class ChangeIRTr extends Transaction {
    private final String description;
    private final int timestamp;

    public ChangeIRTr(final CommandInput input) {
        this.description = "Interest rate of the account changed to " + input.getInterestRate();
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
