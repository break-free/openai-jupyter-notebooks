
package org.apache.fineract.infrastructure.survey.data;
public class LikelihoodData {
    final long resourceId;
    final String likeliHoodName;
    final String likeliHoodCode;
    final long enabled;
    public LikelihoodData(final long resourceId, final String likeliHoodName, final String likeliHoodCode, final long enabled) {
        this.resourceId = resourceId;
        this.likeliHoodName = likeliHoodName;
        this.likeliHoodCode = likeliHoodCode;
        this.enabled = enabled;
    }
}
