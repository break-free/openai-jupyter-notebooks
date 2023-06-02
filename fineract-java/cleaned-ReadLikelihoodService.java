
package org.apache.fineract.infrastructure.survey.service;
import java.util.List;
import org.apache.fineract.infrastructure.survey.data.LikelihoodData;
public interface ReadLikelihoodService {
    List<LikelihoodData> retrieveAll(String ppiName);
    LikelihoodData retrieve(Long likelihoodId);
}
