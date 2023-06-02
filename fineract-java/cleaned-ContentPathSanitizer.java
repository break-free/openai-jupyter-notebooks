
package org.apache.fineract.infrastructure.documentmanagement.contentrepository;
import java.io.BufferedInputStream;
public interface ContentPathSanitizer {
    String sanitize(String path);
    String sanitize(String path, BufferedInputStream is);
}
