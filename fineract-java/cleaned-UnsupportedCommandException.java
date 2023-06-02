
package org.apache.fineract.commands.exception;
public class UnsupportedCommandException extends RuntimeException {
    private final String unsupportedCommandName;
    public UnsupportedCommandException(final String unsupportedCommandName) {
        this.unsupportedCommandName = unsupportedCommandName;
    }
    public String getUnsupportedCommandName() {
        return this.unsupportedCommandName;
    }
}
