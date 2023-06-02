
package org.apache.fineract.portfolio.self.registration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public final class SelfServiceApiConstants {
    private SelfServiceApiConstants() {
    }
    public static final String accountNumberParamName = "accountNumber";
    public static final String passwordParamName = "password";
    public static final String firstNameParamName = "firstName";
    public static final String mobileNumberParamName = "mobileNumber";
    public static final String lastNameParamName = "lastName";
    public static final String emailParamName = "email";
    public static final String usernameParamName = "username";
    public static final String authenticationTokenParamName = "authenticationToken";
    public static final String authenticationModeParamName = "authenticationMode";
    public static final String emailModeParamName = "email";
    public static final String mobileModeParamName = "mobile";
    public static final String requestIdParamName = "requestId";
    public static final String createRequestSuccessMessage = "Self service request created.";
    public static final Set<String> REGISTRATION_REQUEST_DATA_PARAMETERS = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(usernameParamName, accountNumberParamName, passwordParamName, firstNameParamName,
                    mobileNumberParamName, lastNameParamName, emailParamName, authenticationModeParamName)));
    public static final Set<String> CREATE_USER_REQUEST_DATA_PARAMETERS = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(requestIdParamName, authenticationTokenParamName)));
    public static final List<Object> SUPPORTED_AUTHENTICATION_MODE_PARAMETERS = List
            .copyOf(Arrays.asList(emailModeParamName, mobileModeParamName));
    public static final String SELF_SERVICE_USER_ROLE = "Self Service User";
}
