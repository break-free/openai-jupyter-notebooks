
package org.apache.fineract.infrastructure.core.domain;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
@AllArgsConstructor
@Getter
public class FineractContext implements Serializable {
    private final String contextHolder;
    private final FineractPlatformTenant tenantContext;
    private final String authTokenContext;
    private final HashMap<BusinessDateType, LocalDate> businessDateContext;
    private final ActionContext actionContext;
}
