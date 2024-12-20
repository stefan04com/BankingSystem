package org.poo.commerciants;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.poo.fileio.CommandInput;

public final class Commerciant {
    private final String commerciant;
    private double total;
    @JsonIgnore
    private final int timestamp;

    public Commerciant(final CommandInput commandInput, final double total) {
        this.commerciant = commandInput.getCommerciant();
        this.total = total;
        this.timestamp = commandInput.getTimestamp();
    }

    public String getCommerciant() {
        return commerciant;
    }

    public double getTotal() {
        return total;
    }

    public int getTimestamp() {
        return timestamp;
    }

}
