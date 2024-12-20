package org.poo.transactions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

public final class SendMoneyTr extends Transaction {
    private final int timestamp;
    private final String description;
    private final String senderIBAN;
    private final String receiverIBAN;
    private final String amount;
    private final String transferType;

    public SendMoneyTr(final CommandInput commandInput,
                       final String transferType,
                       final String currency,
                       final Double amount) {
        this.timestamp = commandInput.getTimestamp();
        this.description = commandInput.getDescription();
        this.senderIBAN = commandInput.getAccount();
        this.receiverIBAN = commandInput.getReceiver();
        this.amount = amount + " " + currency;
        this.transferType = transferType;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    public String getSenderIBAN() {
        return senderIBAN;
    }

    public String getReceiverIBAN() {
        return receiverIBAN;
    }

    public String getAmount() {
        return amount;
    }

    public String getTransferType() {
        return transferType;
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
