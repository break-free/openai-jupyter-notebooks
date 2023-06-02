
package org.apache.fineract.infrastructure.survey.service;
import org.apache.fineract.infrastructure.survey.data.LikeliHoodPovertyLineData;
import org.apache.fineract.infrastructure.survey.data.PpiPovertyLineData;
public interface PovertyLineService {
    PpiPovertyLineData retrieveAll(String ppiName);
    LikeliHoodPovertyLineData retrieveForLikelihood(String ppiName, Long likelihood);
}
