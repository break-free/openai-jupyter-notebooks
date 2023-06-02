
package org.apache.fineract.mix.service;
import org.apache.fineract.mix.data.NamespaceData;
public interface NamespaceReadPlatformService {
    NamespaceData retrieveNamespaceById(Long id);
    NamespaceData retrieveNamespaceByPrefix(String prefix);
}
