
package org.apache.fineract.portfolio.self.registration.data;
import java.time.LocalDate;
import org.apache.fineract.portfolio.client.domain.Client;
public class SelfServiceRegistrationData {
    @SuppressWarnings("unused")
    private final Long id;
    @SuppressWarnings("unused")
    private Client client;
    @SuppressWarnings("unused")
    private String firstName;
    @SuppressWarnings("unused")
    private String lastName;
    @SuppressWarnings("unused")
    private String mobileNumber;
    @SuppressWarnings("unused")
    private String email;
    @SuppressWarnings("unused")
    private String authenticationToken;
    @SuppressWarnings("unused")
    private String username;
    @SuppressWarnings("unused")
    private String password;
    @SuppressWarnings("unused")
    private LocalDate createdDate;
    public SelfServiceRegistrationData(final Long id, final Client client, final String firstName, final String lastName,
            final String mobileNumber, final String email, final String authenticationToken, final String username, final String password,
            final LocalDate createdDate) {
        this.id = id;
        this.client = client;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.authenticationToken = authenticationToken;
        this.username = username;
        this.password = password;
        this.createdDate = createdDate;
    }
    public static SelfServiceRegistrationData getData(final Long id, final Client client, final String firstName, final String lastName,
            final String mobileNumber, final String email, final String authenticationToken, final String username, final String password,
            final LocalDate createdDate) {
        return new SelfServiceRegistrationData(id, client, firstName, lastName, mobileNumber, email, authenticationToken, username,
                password, createdDate);
    }
}
