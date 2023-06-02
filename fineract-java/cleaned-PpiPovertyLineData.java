
package org.apache.fineract.infrastructure.survey.data;
import java.util.List;
public class PpiPovertyLineData {
    final String ppi;
    final List<LikeliHoodPovertyLineData> likeliHoodPovertyLineData;
    public PpiPovertyLineData(final List<LikeliHoodPovertyLineData> likeliHoodPovertyLineData, final String ppi) {
        this.likeliHoodPovertyLineData = likeliHoodPovertyLineData;
        this.ppi = ppi;
    }
}
