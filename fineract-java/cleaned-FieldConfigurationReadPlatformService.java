
package org.apache.fineract.portfolio.address.service;
import java.util.Collection;
import java.util.List;
import org.apache.fineract.portfolio.address.data.FieldConfigurationData;
public interface FieldConfigurationReadPlatformService {
    Collection<FieldConfigurationData> retrieveFieldConfiguration(String entity);
    List<FieldConfigurationData> retrieveFieldConfigurationList(String entity);
}
