package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;

public interface Command {
    /**
     * Executes the command
     * @param input
     * @param output
     * @param bankSystem
     */
    void execute(CommandInput input, ArrayNode output, BankSystem bankSystem);
}
