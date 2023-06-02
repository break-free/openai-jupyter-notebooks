
package org.apache.fineract.infrastructure.codes.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
public interface CodeValueReadPlatformService {
    Collection<CodeValueData> retrieveCodeValuesByCode(String code);
    Collection<CodeValueData> retrieveAllCodeValues(Long codeId);
    CodeValueData retrieveCodeValue(Long codeValueId);
}
