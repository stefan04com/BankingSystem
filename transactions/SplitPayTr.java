package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

import java.util.List;

public final class SplitPayTr extends Transaction {
    private final int timestamp;
    private final String description;
    private final String currency;
    private final double amount;
    private List<String> involvedAccounts;

    public SplitPayTr(final CommandInput input) {
       this.timestamp = input.getTimestamp();
       this.description = "Split payment of " + input.getAmount() + "0" + " " + input.getCurrency();
       this.currency = input.getCurrency();
       this.amount = input.getAmount() / input.getAccounts().size();
       this.involvedAccounts = input.getAccounts();
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrency() {
        return currency;
    }

    public double getAmount() {
        return amount;
    }

    public List<String> getInvolvedAccounts() {
        return involvedAccounts;
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
