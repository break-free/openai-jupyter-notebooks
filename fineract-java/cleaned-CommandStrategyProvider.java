
package org.apache.fineract.batch.command;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.fineract.batch.command.internal.UnknownCommandStrategy;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
@Component
public class CommandStrategyProvider {
    private final ApplicationContext applicationContext;
    private final Map<CommandContext, String> commandStrategies = new ConcurrentHashMap<>();
    public CommandStrategyProvider(final ApplicationContext applicationContext) {
        init();
        this.applicationContext = applicationContext;
    }
    public CommandStrategy getCommandStrategy(final CommandContext commandContext) {
        if (this.commandStrategies.containsKey(commandContext)) {
            return (CommandStrategy) this.applicationContext.getBean(this.commandStrategies.get(commandContext));
        }
        for (Map.Entry<CommandContext, String> entry : this.commandStrategies.entrySet()) {
            if (commandContext.matcher(entry.getKey())) {
                return (CommandStrategy) this.applicationContext.getBean(this.commandStrategies.get(entry.getKey()));
            }
        }
        return new UnknownCommandStrategy();
    }
    private void init() {
        this.commandStrategies.put(CommandContext.resource("clients").method("POST").build(), "createClientCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("clients\\/\\d+").method("PUT").build(), "updateClientCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans").method("POST").build(), "applyLoanCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans\\/\\d+").method("GET").build(), "getLoanByIdCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans/\\d+(\\?(\\w+(?:\\=[\\w,]+|&)+)+)").method("GET").build(),
                "getLoanByIdCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("savingsaccounts").method("POST").build(), "applySavingsCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans\\/\\d+\\/charges").method("POST").build(), "createChargeCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans\\/\\d+\\/charges").method("GET").build(),
                "collectChargesCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans\\/\\d+\\/transactions\\?command=repayment").method("POST").build(),
                "createTransactionLoanCommandStrategy");
        this.commandStrategies.put(
                CommandContext.resource("loans\\/\\d+\\/transactions\\?command=creditBalanceRefund").method("POST").build(),
                "createTransactionLoanCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("clients\\/\\d+\\?command=activate").method("POST").build(),
                "activateClientCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans\\/\\d+\\?command=approve").method("POST").build(),
                "approveLoanCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans\\/\\d+\\?command=disburse").method("POST").build(),
                "disburseLoanCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("rescheduleloans\\/\\d+\\?command=approve").method("POST").build(),
                "approveLoanRescheduleCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("loans\\/\\d+\\/transactions\\/\\d+").method("GET").build(),
                "getTransactionByIdCommandStrategy");
        this.commandStrategies.put(CommandContext.resource("datatables\\/[a-zA-Z0-9_]*\\/\\d+").method("GET").build(),
                "getDatatableEntryByAppTableIdCommandStrategy");
        this.commandStrategies.put(
                CommandContext.resource("datatables\\/[a-zA-Z0-9_]*\\/\\d+(\\?(\\w+(?:\\=[\\w,]+|&)+)+)").method("GET").build(),
                "getDatatableEntryByAppTableIdCommandStrategy");
    }
}
