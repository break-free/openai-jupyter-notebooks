
package org.apache.fineract.infrastructure.survey.data;
import java.util.List;
public class LikeliHoodPovertyLineData {
    final long resourceId;
    final String likeliHoodName;
    final String likeliHoodCode;
    final long enabled;
    List<PovertyLineData> povertyLineData;
    public LikeliHoodPovertyLineData(final long resourceId, final List<PovertyLineData> povertyLineData, final String likeliHoodName,
            final String likeliHoodCode, final long enabled) {
        this.resourceId = resourceId;
        this.povertyLineData = povertyLineData;
        this.likeliHoodName = likeliHoodName;
        this.likeliHoodCode = likeliHoodCode;
        this.enabled = enabled;
    }
    public void addPovertyLine(PovertyLineData povertyLineData) {
        this.povertyLineData.add(povertyLineData);
    }
}
