
package org.apache.fineract.accounting.closure.service;
import java.util.List;
import org.apache.fineract.accounting.closure.data.GLClosureData;
public interface GLClosureReadPlatformService {
    List<GLClosureData> retrieveAllGLClosures(Long OfficeId);
    GLClosureData retrieveGLClosureById(long glClosureId);
}
