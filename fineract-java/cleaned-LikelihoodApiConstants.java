
package org.apache.fineract.infrastructure.survey.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.infrastructure.survey.data.LikelihoodStatus;
public final class LikelihoodApiConstants {
    private LikelihoodApiConstants() {
    }
    public static final String ACTIVE = "active";
    public static final String LIKELIHOOD_RESOURCE_NAME = "likelihood";
    static final Set<Long> VALID_LIKELIHOOD_ENABLED_VALUES = new HashSet<>(
            Arrays.asList(LikelihoodStatus.DISABLED, LikelihoodStatus.ENABLED));
}
