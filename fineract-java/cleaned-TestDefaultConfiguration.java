
package org.apache.fineract.module.service;
import static org.mockito.Mockito.mock;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.apache.fineract.infrastructure.core.service.RoutingDataSource;
import org.apache.fineract.infrastructure.core.service.RoutingDataSourceServiceFactory;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.group.domain.GroupRepository;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransactionRepository;
import org.apache.fineract.portfolio.note.domain.NoteRepository;
import org.apache.fineract.portfolio.note.serialization.NoteCommandFromApiJsonDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
@EnableConfigurationProperties({ FineractProperties.class })
public class TestDefaultConfiguration {
    @Bean
    public FromJsonHelper fromJsonHelper() {
        return mock(FromJsonHelper.class);
    }
    @Bean
    public RoutingDataSourceServiceFactory routingDataSourceServiceFactory() {
        return mock(RoutingDataSourceServiceFactory.class);
    }
    @Bean
    public RoutingDataSource routingDataSource() {
        return mock(RoutingDataSource.class);
    }
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return mock(JdbcTemplate.class);
    }
    @Bean
    public NoteRepository noteRepository() {
        return mock(NoteRepository.class);
    }
    @Bean
    public ClientRepositoryWrapper clientRepository() {
        return mock(ClientRepositoryWrapper.class);
    }
    @Bean
    public GroupRepository groupRepository() {
        return mock(GroupRepository.class);
    }
    @Bean
    public LoanRepositoryWrapper loanRepository() {
        return mock(LoanRepositoryWrapper.class);
    }
    @Bean
    public LoanTransactionRepository loanTransactionRepository() {
        return mock(LoanTransactionRepository.class);
    }
    @Bean
    public NoteCommandFromApiJsonDeserializer fromApiJsonDeserializer(FromJsonHelper fromJsonHelper) {
        return new NoteCommandFromApiJsonDeserializer(fromJsonHelper);
    }
}
