
package org.apache.fineract.portfolio.self.registration.domain;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.infrastructure.core.service.DateUtils;
import org.apache.fineract.portfolio.client.domain.Client;
@Entity
@Table(name = "request_audit_table")
public class SelfServiceRegistration extends AbstractPersistableCustom {
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
    @Column(name = "account_number", length = 100, nullable = false)
    private String accountNumber;
    @Column(name = "firstname", length = 100, nullable = false)
    private String firstName;
    @Column(name = "lastname", length = 100, nullable = false)
    private String lastName;
    @Column(name = "mobile_number", length = 50, nullable = true)
    private String mobileNumber;
    @Column(name = "email", length = 100, nullable = false)
    private String email;
    @Column(name = "authentication_token", length = 100, nullable = true)
    private String authenticationToken;
    @Column(name = "username", length = 100, nullable = false)
    private String username;
    @Column(name = "password", length = 100, nullable = false)
    private String password;
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
    public SelfServiceRegistration() {}
    public SelfServiceRegistration(final Client client, String accountNumber, final String firstName, final String lastName,
            final String mobileNumber, final String email, final String authenticationToken, final String username, final String password) {
        this.client = client;
        this.accountNumber = accountNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.authenticationToken = authenticationToken;
        this.username = username;
        this.password = password;
        this.createdDate = DateUtils.getLocalDateTimeOfSystem();
    }
    public static SelfServiceRegistration instance(final Client client, final String accountNumber, final String firstName,
            final String lastName, final String mobileNumber, final String email, final String authenticationToken, final String username,
            final String password) {
        return new SelfServiceRegistration(client, accountNumber, firstName, lastName, mobileNumber, email, authenticationToken, username,
                password);
    }
    public Client getClient() {
        return this.client;
    }
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getMobileNumber() {
        return this.mobileNumber;
    }
    public String getEmail() {
        return this.email;
    }
    public String getAuthenticationToken() {
        return this.authenticationToken;
    }
    public LocalDateTime getCreatedDate() {
        return this.createdDate;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public String getAccountNumber() {
        return this.accountNumber;
    }
}
