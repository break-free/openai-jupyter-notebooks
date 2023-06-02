
package org.apache.fineract.infrastructure.security.utils;
import java.sql.SQLException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
public class SQLInjectionException extends PlatformApiDataValidationException {
    public SQLInjectionException() {
        super("error.msg.found.sql.injection", "Unexpected SQL Commands found", null);
    }
    public SQLInjectionException(SQLException e) {
        super("error.msg.found.sql.injection", "Unexpected SQL Commands found", null, e);
    }
}
