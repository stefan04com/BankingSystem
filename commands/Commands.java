package org.poo.commands;

import com.fasterxml.jackson.databind.node.ArrayNode;
import org.poo.fileio.CommandInput;
import org.poo.system.BankSystem;

import java.util.HashMap;
import java.util.Map;

public final class Commands {
    private final Map<String, Command> commandMap = new HashMap<>();

    public Commands() {
        commandMap.put("payOnline", new PayOnline());
        commandMap.put("printUsers", new PrintUsers());
        commandMap.put("deleteAccount", new DeleteAccount());
        commandMap.put("splitPayment", new SplitPayment());
        commandMap.put("addAccount", new AddAccount());
        commandMap.put("addFunds", new AddFunds());
        commandMap.put("addInterest", new AddInterest());
        commandMap.put("createCard", new CreateCard());
        commandMap.put("deleteCard", new DeleteCard());
        commandMap.put("changeInterestRate", new ChangeInterestRate());
        commandMap.put("checkCardStatus", new CheckCardStatus());
        commandMap.put("createOneTimeCard", new CreateOneTimeCard());
        commandMap.put("printTransactions", new PrintTransactions());
        commandMap.put("report", new Report());
        commandMap.put("sendMoney", new SendMoney());
        commandMap.put("setAlias", new SetAlias());
        commandMap.put("setMinimumBalance", new SetMinimumBalance());
        commandMap.put("spendingsReport", new SpendingsReport());
    }

    /**
     * Executes the command given by the input
     * @param input
     * @param output
     * @param bankSystem
     */
    public void execute(final CommandInput input,
                        final ArrayNode output,
                        final BankSystem bankSystem) {
        Command command = commandMap.get(input.getCommand());
        if (command != null) {
            command.execute(input, output, bankSystem);
        }
    }
}
