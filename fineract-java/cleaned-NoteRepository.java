
package org.apache.fineract.portfolio.note.domain;
import java.util.List;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.portfolio.group.domain.Group;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
import org.apache.fineract.portfolio.savings.domain.SavingsAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
public interface NoteRepository extends JpaRepository<Note, Long>, JpaSpecificationExecutor<Note> {
    List<Note> findByLoanId(Long id);
    List<Note> findByClient(Client id);
    List<Note> findByGroup(Group group);
    Note findByLoanAndId(Loan loanId, Long id);
    Note findByClientAndId(Client client, Long id);
    Note findByGroupAndId(Group group, Long id);
    Note findByLoanTransactionAndId(LoanTransaction loanTransaction, Long id);
    List<Note> findBySavingsAccount(SavingsAccount savingAccount);
}
