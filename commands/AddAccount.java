package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;

public final class AddAccount implements  Command {
    @Override
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {
        bankSystem.addAccount(input);
    }
}