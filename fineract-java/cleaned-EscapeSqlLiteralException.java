
package org.apache.fineract.infrastructure.security.exception;
import java.sql.SQLException;
public class EscapeSqlLiteralException extends RuntimeException {
    public EscapeSqlLiteralException(String message, SQLException e) {
        super(message, e);
    }
}
