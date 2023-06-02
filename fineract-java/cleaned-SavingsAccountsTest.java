
package org.apache.fineract.integrationtests;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.fineract.client.models.PostSavingsAccountsAccountIdRequest;
import org.apache.fineract.client.models.PostSavingsAccountsAccountIdResponse;
import org.apache.fineract.client.models.PostSavingsAccountsRequest;
import org.apache.fineract.client.models.PostSavingsAccountsResponse;
import org.apache.fineract.integrationtests.client.IntegrationTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Response;
public class SavingsAccountsTest extends IntegrationTest {
    private static final Logger LOG = LoggerFactory.getLogger(SavingsAccountsTest.class);
    private final String dateFormat = "dd MMMM yyyy";
    private final String locale = "en";
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
    private final String formattedDate = simpleDateFormat.format(new Date());
    private int savingId = 1;
    @Test
    @Order(1)
    void submitSavingsAccountsApplication() {
        LOG.info("------------------------------ CREATING NEW SAVINGS ACCOUNT APPLICATION ---------------------------------------");
        PostSavingsAccountsRequest request = new PostSavingsAccountsRequest();
        request.setClientId(1);
        request.setProductId(1);
        request.setLocale(locale);
        request.setDateFormat(dateFormat);
        request.submittedOnDate(formattedDate);
        Response<PostSavingsAccountsResponse> response = okR(fineract().savingsAccounts.submitApplication2(request));
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isNotNull();
        savingId = response.body().getSavingsId();
    }
    @Test
    @Order(2)
    void approveSavingsAccount() {
        LOG.info("------------------------------ APPROVING SAVINGS ACCOUNT ---------------------------------------");
        PostSavingsAccountsAccountIdRequest request = new PostSavingsAccountsAccountIdRequest();
        request.dateFormat(dateFormat);
        request.setLocale(locale);
        request.setApprovedOnDate(formattedDate);
        Response<PostSavingsAccountsAccountIdResponse> response = okR(
                fineract().savingsAccounts.handleCommands6((long) savingId, request, "approve"));
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isNotNull();
    }
    @Test
    @Order(3)
    void activateSavingsAccount() {
        LOG.info("------------------------------ ACTIVATING SAVINGS ACCOUNT ---------------------------------------");
        PostSavingsAccountsAccountIdRequest request = new PostSavingsAccountsAccountIdRequest();
        request.dateFormat(dateFormat);
        request.setLocale(locale);
        request.setActivatedOnDate(formattedDate);
        Response<PostSavingsAccountsAccountIdResponse> response = okR(
                fineract().savingsAccounts.handleCommands6((long) savingId, request, "activate"));
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isNotNull();
    }
}
