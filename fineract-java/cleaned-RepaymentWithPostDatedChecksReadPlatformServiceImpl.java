
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepaymentScheduleInstallmentRepository;
import org.apache.fineract.portfolio.loanaccount.domain.LoanRepository;
import org.apache.fineract.portfolio.loanaccount.exception.LoanNotFoundException;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.data.PostDatedChecksData;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.data.PostDatedChecksStatus;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain.PostDatedChecks;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.domain.PostDatedChecksRepository;
import org.apache.fineract.portfolio.repaymentwithpostdatedchecks.exception.PostDatedCheckNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Transactional(readOnly = true)
public class RepaymentWithPostDatedChecksReadPlatformServiceImpl implements RepaymentWithPostDatedChecksReadPlatformService {
    private final PostDatedChecksRepository postDatedChecksRepository;
    private final LoanRepository loanRepository;
    private final LoanRepaymentScheduleInstallmentRepository loanRepaymentScheduleInstallmentRepository;
    @Autowired
    public RepaymentWithPostDatedChecksReadPlatformServiceImpl(final PostDatedChecksRepository postDatedChecksRepository,
            final LoanRepository loanRepository,
            final LoanRepaymentScheduleInstallmentRepository loanRepaymentScheduleInstallmentRepository) {
        this.postDatedChecksRepository = postDatedChecksRepository;
        this.loanRepository = loanRepository;
        this.loanRepaymentScheduleInstallmentRepository = loanRepaymentScheduleInstallmentRepository;
    }
    @Override
    public List<PostDatedChecksData> getPostDatedChecks(final Long id) {
        final Loan loan = this.loanRepository.findById(id).orElseThrow(() -> new LoanNotFoundException(id));
        final List<PostDatedChecks> postDatedChecks = loan.getPostDatedChecks();
        List<PostDatedChecksData> postDatedChecksDataList = new ArrayList<>();
        for (PostDatedChecks postDatedCheckObject : postDatedChecks) {
            if (!PostDatedChecksStatus.POST_DATED_CHECKS_BOUNCED.equals(postDatedCheckObject.getStatus())) {
                postDatedChecksDataList.add(PostDatedChecksData.from(
                        postDatedCheckObject.getLoanRepaymentScheduleInstallment().getDueDate(), postDatedCheckObject.getId(),
                        postDatedCheckObject.getLoanRepaymentScheduleInstallment().getInstallmentNumber(),
                        postDatedCheckObject.getAccountNo(), postDatedCheckObject.getAmount(), postDatedCheckObject.getBankName(),
                        postDatedCheckObject.getCheckNo(), postDatedCheckObject.getStatus()));
            }
        }
        return postDatedChecksDataList;
    }
    @Override
    public PostDatedChecksData getPostDatedCheck(final Long id) {
        final PostDatedChecks postDatedChecks = this.postDatedChecksRepository.findById(id)
                .orElseThrow(() -> new PostDatedCheckNotFoundException(id));
        return PostDatedChecksData.from(postDatedChecks.getLoanRepaymentScheduleInstallment().getDueDate(), postDatedChecks.getId(),
                postDatedChecks.getLoanRepaymentScheduleInstallment().getInstallmentNumber(), postDatedChecks.getAccountNo(),
                postDatedChecks.getAmount(), postDatedChecks.getBankName(), postDatedChecks.getCheckNo(), postDatedChecks.getStatus());
    }
    @Override
    public PostDatedChecksData getPostDatedCheckByInstallmentId(final Integer id, final Long loanId) {
        final Loan loan = this.loanRepository.findById(loanId).orElseThrow(() -> new LoanNotFoundException(loanId));
        final List<PostDatedChecks> postDatedChecks = loan.getPostDatedChecks();
        if (postDatedChecks == null || postDatedChecks.size() == 0) {
            throw new PostDatedCheckNotFoundException(loanId, id);
        }
        final PostDatedChecks postDatedChecksData = postDatedChecks.stream()
                .filter((postDatedCheck) -> postDatedCheck.getLoanRepaymentScheduleInstallment().getInstallmentNumber().equals(id)
                        && !PostDatedChecksStatus.POST_DATED_CHECKS_BOUNCED.equals(postDatedCheck.getStatus()))
                .collect(Collectors.toList()).get(0);
        return PostDatedChecksData.from(postDatedChecksData.getLoanRepaymentScheduleInstallment().getDueDate(), postDatedChecksData.getId(),
                postDatedChecksData.getLoanRepaymentScheduleInstallment().getInstallmentNumber(), postDatedChecksData.getAccountNo(),
                postDatedChecksData.getAmount(), postDatedChecksData.getBankName(), postDatedChecksData.getCheckNo(),
                postDatedChecksData.getStatus());
    }
}
