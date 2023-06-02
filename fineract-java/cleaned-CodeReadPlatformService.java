
package org.apache.fineract.infrastructure.codes.service;
import java.util.Collection;
import org.apache.fineract.infrastructure.codes.data.CodeData;
public interface CodeReadPlatformService {
    Collection<CodeData> retrieveAllCodes();
    CodeData retrieveCode(Long codeId);
    CodeData retriveCode(String codeName);
}
