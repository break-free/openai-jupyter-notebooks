
package org.apache.fineract.infrastructure.configuration.domain;
import org.apache.fineract.infrastructure.configuration.exception.GlobalConfigurationPropertyNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class GlobalConfigurationRepositoryWrapper {
    private final GlobalConfigurationRepository repository;
    @Autowired
    public GlobalConfigurationRepositoryWrapper(final GlobalConfigurationRepository repository) {
        this.repository = repository;
    }
    public GlobalConfigurationProperty findOneByNameWithNotFoundDetection(final String propertyName) {
        final GlobalConfigurationProperty property = this.repository.findOneByName(propertyName);
        if (property == null) {
            throw new GlobalConfigurationPropertyNotFoundException(propertyName);
        }
        return property;
    }
    public GlobalConfigurationProperty findOneWithNotFoundDetection(final Long configId) {
        return this.repository.findById(configId).orElseThrow(() -> new GlobalConfigurationPropertyNotFoundException(configId));
    }
    public void save(final GlobalConfigurationProperty globalConfigurationProperty) {
        this.repository.save(globalConfigurationProperty);
    }
    public void saveAndFlush(final GlobalConfigurationProperty globalConfigurationProperty) {
        this.repository.saveAndFlush(globalConfigurationProperty);
    }
    public void delete(final GlobalConfigurationProperty globalConfigurationProperty) {
        this.repository.delete(globalConfigurationProperty);
    }
}
