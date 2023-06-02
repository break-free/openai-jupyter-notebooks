
package org.apache.fineract.portfolio.note.starter;
import org.apache.fineract.portfolio.client.domain.ClientRepositoryWrapper;
import org.apache.fineract.portfolio.group.domain.GroupRepository;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepositoryWrapper;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransactionRepository;
import org.apache.fineract.portfolio.note.domain.NoteRepository;
import org.apache.fineract.portfolio.note.serialization.NoteCommandFromApiJsonDeserializer;
import org.apache.fineract.portfolio.note.service.NoteReadPlatformService;
import org.apache.fineract.portfolio.note.service.NoteReadPlatformServiceImpl;
import org.apache.fineract.portfolio.note.service.NoteWritePlatformService;
import org.apache.fineract.portfolio.note.service.NoteWritePlatformServiceJpaRepositoryImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
@Configuration
public class NoteAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public NoteReadPlatformService noteReadPlatformService(JdbcTemplate jdbcTemplate) {
        return new NoteReadPlatformServiceImpl(jdbcTemplate);
    }
    @Bean
    @ConditionalOnMissingBean
    public NoteWritePlatformService noteWritePlatformService(NoteRepository noteRepository, ClientRepositoryWrapper clientRepository,
            GroupRepository groupRepository, LoanRepositoryWrapper loanRepository, LoanTransactionRepository loanTransactionRepository,
            NoteCommandFromApiJsonDeserializer fromApiJsonDeserializer) {
        return new NoteWritePlatformServiceJpaRepositoryImpl(noteRepository, clientRepository, groupRepository, loanRepository,
                loanTransactionRepository, fromApiJsonDeserializer);
    }
}
