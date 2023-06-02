
package org.apache.fineract.mix.service;
import java.util.List;
import org.apache.fineract.mix.data.MixTaxonomyData;
public interface MixTaxonomyReadPlatformService {
    List<MixTaxonomyData> retrieveAll();
    MixTaxonomyData retrieveOne(Long id);
}
