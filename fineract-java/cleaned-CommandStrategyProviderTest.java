
package org.apache.fineract.batch.command;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.stream.Stream;
import javax.ws.rs.HttpMethod;
import org.apache.fineract.batch.command.internal.ActivateClientCommandStrategy;
import org.apache.fineract.batch.command.internal.ApplyLoanCommandStrategy;
import org.apache.fineract.batch.command.internal.ApplySavingsCommandStrategy;
import org.apache.fineract.batch.command.internal.ApproveLoanCommandStrategy;
import org.apache.fineract.batch.command.internal.ApproveLoanRescheduleCommandStrategy;
import org.apache.fineract.batch.command.internal.CollectChargesCommandStrategy;
import org.apache.fineract.batch.command.internal.CreateChargeCommandStrategy;
import org.apache.fineract.batch.command.internal.CreateClientCommandStrategy;
import org.apache.fineract.batch.command.internal.CreateTransactionLoanCommandStrategy;
import org.apache.fineract.batch.command.internal.DisburseLoanCommandStrategy;
import org.apache.fineract.batch.command.internal.GetDatatableEntryByAppTableIdCommandStrategy;
import org.apache.fineract.batch.command.internal.GetLoanByIdCommandStrategy;
import org.apache.fineract.batch.command.internal.GetTransactionByIdCommandStrategy;
import org.apache.fineract.batch.command.internal.UnknownCommandStrategy;
import org.apache.fineract.batch.command.internal.UpdateClientCommandStrategy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.ApplicationContext;
public class CommandStrategyProviderTest {
    private static Stream<Arguments> provideCommandStrategies() {
        return Stream.of(Arguments.of("clients", HttpMethod.POST, "createClientCommandStrategy", mock(CreateClientCommandStrategy.class)),
                Arguments.of("clients/123", HttpMethod.PUT, "updateClientCommandStrategy", mock(UpdateClientCommandStrategy.class)),
                Arguments.of("loans", HttpMethod.POST, "applyLoanCommandStrategy", mock(ApplyLoanCommandStrategy.class)),
                Arguments.of("loans/123", HttpMethod.GET, "getLoanByIdCommandStrategy", mock(GetLoanByIdCommandStrategy.class)),
                Arguments.of("loans/123?associations=all", HttpMethod.GET, "getLoanByIdCommandStrategy",
                        mock(GetLoanByIdCommandStrategy.class)),
                Arguments.of("loans/123?associations=all&exclude=guarantors", HttpMethod.GET, "getLoanByIdCommandStrategy",
                        mock(GetLoanByIdCommandStrategy.class)),
                Arguments.of("savingsaccounts", HttpMethod.POST, "applySavingsCommandStrategy", mock(ApplySavingsCommandStrategy.class)),
                Arguments.of("loans/123/charges", HttpMethod.POST, "createChargeCommandStrategy", mock(CreateChargeCommandStrategy.class)),
                Arguments.of("loans/123/charges", HttpMethod.GET, "collectChargesCommandStrategy",
                        mock(CollectChargesCommandStrategy.class)),
                Arguments.of("loans/123/transactions?command=repayment", HttpMethod.POST, "createTransactionLoanCommandStrategy",
                        mock(CreateTransactionLoanCommandStrategy.class)),
                Arguments.of("loans/123/transactions?command=creditBalanceRefund", HttpMethod.POST, "createTransactionLoanCommandStrategy",
                        mock(CreateTransactionLoanCommandStrategy.class)),
                Arguments.of("clients/456?command=activate", HttpMethod.POST, "activateClientCommandStrategy",
                        mock(ActivateClientCommandStrategy.class)),
                Arguments.of("loans/123?command=approve", HttpMethod.POST, "approveLoanCommandStrategy",
                        mock(ApproveLoanCommandStrategy.class)),
                Arguments.of("loans/123?command=disburse", HttpMethod.POST, "disburseLoanCommandStrategy",
                        mock(DisburseLoanCommandStrategy.class)),
                Arguments.of("rescheduleloans/123?command=approve", HttpMethod.POST, "approveLoanRescheduleCommandStrategy",
                        mock(ApproveLoanRescheduleCommandStrategy.class)),
                Arguments.of("loans/123/transactions/123", HttpMethod.GET, "getTransactionByIdCommandStrategy",
                        mock(GetTransactionByIdCommandStrategy.class)),
                Arguments.of("datatables/test_dt_table/123", HttpMethod.GET, "getDatatableEntryByAppTableIdCommandStrategy",
                        mock(GetDatatableEntryByAppTableIdCommandStrategy.class)),
                Arguments.of("datatables/test_dt_table/123?genericResultSet=true", HttpMethod.GET,
                        "getDatatableEntryByAppTableIdCommandStrategy", mock(GetDatatableEntryByAppTableIdCommandStrategy.class)));
    }
    @ParameterizedTest
    @MethodSource("provideCommandStrategies")
    public void testGetCommandStrategySuccess(final String url, final String httpMethod, final String beanName,
            final CommandStrategy commandStrategy) {
        final ApplicationContext applicationContext = mock(ApplicationContext.class);
        final CommandStrategyProvider commandStrategyProvider = new CommandStrategyProvider(applicationContext);
        when(applicationContext.getBean(beanName)).thenReturn(commandStrategy);
        final CommandStrategy result = commandStrategyProvider.getCommandStrategy(CommandContext.resource(url).method(httpMethod).build());
        assertEquals(commandStrategy, result);
    }
    private static Stream<Arguments> provideCommandStrategyResourceDetailsForErrors() {
        return Stream.of(Arguments.of("loans/123?command=reject", HttpMethod.POST),
                Arguments.of("loans/glimAccount/746?command=approve", HttpMethod.POST), Arguments.of("loans/123", HttpMethod.PUT),
                Arguments.of("datatables/test_dt_table", HttpMethod.GET), Arguments.of("datatables", HttpMethod.GET));
    }
    @ParameterizedTest
    @MethodSource("provideCommandStrategyResourceDetailsForErrors")
    public void testGetCommandStrategyForError(final String url, final String httpMethod) {
        final ApplicationContext applicationContext = mock(ApplicationContext.class);
        final CommandStrategyProvider commandStrategyProvider = new CommandStrategyProvider(applicationContext);
        final CommandStrategy result = commandStrategyProvider.getCommandStrategy(CommandContext.resource(url).method(httpMethod).build());
        assertEquals(UnknownCommandStrategy.class, result.getClass());
    }
}
