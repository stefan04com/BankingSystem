package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

import java.util.List;

public final class SplitPayError extends Transaction {
    private final double amount;
    private final String currency;
    private final String description;
    private final String error;
    private List<String> involvedAccounts;
    private final int timestamp;

    public SplitPayError(final CommandInput input, final String account) {
        this.timestamp = input.getTimestamp();
        this.description = "Split payment of "
                + input.getAmount() + "0" + " " + input.getCurrency();
        this.currency = input.getCurrency();
        this.amount = input.getAmount() / input.getAccounts().size();
        this.involvedAccounts = input.getAccounts();
        this.error = "Account " + account + " has insufficient funds for a split payment.";
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getDescription() {
        return description;
    }

    public String getError() {
        return error;
    }

    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
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
