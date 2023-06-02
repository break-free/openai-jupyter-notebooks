
package org.apache.fineract.infrastructure.creditbureau.domain;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Entity
@Table(name = "m_creditbureau_token")
public class CreditBureauToken extends AbstractPersistableCustom {
    private static final Logger LOG = LoggerFactory.getLogger(CreditBureauToken.class);
    @Column(name = "username")
    private String userName;
    @Column(name = "token")
    private String accessToken;
    @Column(name = "token_type")
    private String tokenType;
    @Column(name = "expires_in")
    private String expiresIn;
    @Column(name = "issued")
    private String issued;
    @Column(name = "expiry_date")
    private LocalDate expires;
    public static CreditBureauToken fromJson(final JsonCommand command) {
        final String userName = command.stringValueOfParameterNamed("userName");
        final String accessToken = command.stringValueOfParameterNamed("access_token");
        final String tokenType = command.stringValueOfParameterNamed("token_type");
        final String expiresIn = command.stringValueOfParameterNamed("expires_in");
        final String issued = command.stringValueOfParameterNamed(".issued");
        final String expiry = command.stringValueOfParameterNamed(".expires");
        DateTimeFormatter dateformat = new DateTimeFormatterBuilder().appendPattern("EEE, dd MMM yyyy kk:mm:ss zzz").toFormatter();
        LocalDate expires = LocalDate.parse(expiry, dateformat);
        return new CreditBureauToken(userName, accessToken, tokenType, expiresIn, issued, expires);
    }
    public CreditBureauToken(String userName, String accessToken, String tokenType, String expiresIn, String issued, LocalDate expires) {
        this.userName = userName;
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.issued = issued;
        this.expires = expires;
    }
    public CreditBureauToken() {
        this.userName = null;
        this.accessToken = null;
        this.tokenType = null;
        this.expiresIn = null;
        this.issued = null;
        this.expires = null;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getCurrentToken() {
        return this.accessToken;
    }
    public void setTokens(String tokens) {
        this.accessToken = tokens;
    }
    public LocalDate getTokenExpiryDate() {
        return this.expires;
    }
}
