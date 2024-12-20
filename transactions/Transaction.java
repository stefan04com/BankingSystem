package org.poo.transactions;

public abstract class Transaction {
    /**
     * return the timestamp of the transaction
     * @return
     */
    public abstract int getTimestamp();
    /**
     * return the commerciant of the transaction
     * @return
     */
    public abstract String getCommerciant();

    /**
     * return the account of the transaction
     * @return
     */
    public abstract String getAccount();

    /**
     * return the amount of the transaction
     * @return
     */
    public abstract double getComAmount();
}
