
package org.apache.fineract.infrastructure.campaigns.email.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.fineract.infrastructure.campaigns.email.domain.EmailCampaign;
import org.apache.fineract.infrastructure.campaigns.email.domain.EmailCampaignRepository;
import org.apache.fineract.infrastructure.campaigns.sms.constants.SmsCampaignTriggerType;
import org.apache.fineract.portfolio.businessevent.BusinessEventListener;
import org.apache.fineract.portfolio.businessevent.domain.loan.LoanApprovedBusinessEvent;
import org.apache.fineract.portfolio.businessevent.domain.loan.LoanRejectedBusinessEvent;
import org.apache.fineract.portfolio.businessevent.domain.loan.transaction.LoanTransactionMakeRepaymentPostBusinessEvent;
import org.apache.fineract.portfolio.businessevent.service.BusinessEventNotifierService;
import org.apache.fineract.portfolio.loanaccount.domain.Loan;
import org.apache.fineract.portfolio.loanaccount.domain.LoanTransaction;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
public class EmailCampaignDomainServiceImpl implements EmailCampaignDomainService {
    private final BusinessEventNotifierService businessEventNotifierService;
    private final EmailCampaignWritePlatformService emailCampaignWritePlatformService;
    private final EmailCampaignRepository emailCampaignRepository;
    @PostConstruct
    public void addListeners() {
        businessEventNotifierService.addPostBusinessEventListener(LoanApprovedBusinessEvent.class, new SendEmailOnLoanApproved());
        businessEventNotifierService.addPostBusinessEventListener(LoanRejectedBusinessEvent.class, new SendEmailOnLoanRejected());
        businessEventNotifierService.addPostBusinessEventListener(LoanTransactionMakeRepaymentPostBusinessEvent.class,
                new SendEmailOnLoanRepayment());
    }
    private class SendEmailOnLoanRepayment implements BusinessEventListener<LoanTransactionMakeRepaymentPostBusinessEvent> {
        @Override
        public void onBusinessEvent(LoanTransactionMakeRepaymentPostBusinessEvent event) {
            LoanTransaction loanTransaction = event.get();
            try {
                notifyLoanOwner(loanTransaction, "Loan Repayment");
            } catch (IOException e) {
                log.error("Exception when trying to send triggered email: {}", e.getMessage());
            }
        }
    }
    private class SendEmailOnLoanRejected implements BusinessEventListener<LoanRejectedBusinessEvent> {
        @Override
        public void onBusinessEvent(LoanRejectedBusinessEvent event) {
            Loan loan = event.get();
            try {
                notifyLoanOwner(loan, "Loan Rejected");
            } catch (IOException e) {
                log.error("Exception when trying to send triggered email: {}", e.getMessage());
            }
        }
    }
    private class SendEmailOnLoanApproved implements BusinessEventListener<LoanApprovedBusinessEvent> {
        @Override
        public void onBusinessEvent(LoanApprovedBusinessEvent event) {
            Loan loan = event.get();
            try {
                notifyLoanOwner(loan, "Loan Approved");
            } catch (IOException e) {
                log.error("Exception when trying to send triggered email: {}", e.getMessage());
            }
        }
    }
    private void notifyLoanOwner(LoanTransaction loanTransaction, String paramValue) throws IOException {
        List<EmailCampaign> campaigns = this.retrieveEmailCampaigns(paramValue);
        for (EmailCampaign emailCampaign : campaigns) {
            HashMap<String, String> campaignParams = new ObjectMapper().readValue(emailCampaign.getParamValue(),
                    new TypeReference<HashMap<String, String>>() {});
            campaignParams.put("loanId", loanTransaction.getLoan().getId().toString());
            campaignParams.put("loanTransactionId", loanTransaction.getId().toString());
            this.emailCampaignWritePlatformService.insertDirectCampaignIntoEmailOutboundTable(loanTransaction.getLoan(), emailCampaign,
                    campaignParams);
        }
    }
    private void notifyLoanOwner(Loan loan, String paramValue) throws IOException {
        List<EmailCampaign> campaigns = this.retrieveEmailCampaigns(paramValue);
        for (EmailCampaign emailCampaign : campaigns) {
            HashMap<String, String> campaignParams = new ObjectMapper().readValue(emailCampaign.getParamValue(),
                    new TypeReference<HashMap<String, String>>() {});
            campaignParams.put("loanId", loan.getId().toString());
            this.emailCampaignWritePlatformService.insertDirectCampaignIntoEmailOutboundTable(loan, emailCampaign, campaignParams);
        }
    }
    private List<EmailCampaign> retrieveEmailCampaigns(String paramValue) {
        List<EmailCampaign> emailCampaigns = emailCampaignRepository.findActiveEmailCampaigns("%" + paramValue + "%",
                SmsCampaignTriggerType.TRIGGERED.getValue());
        return emailCampaigns;
    }
}
