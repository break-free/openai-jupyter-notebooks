
package org.apache.fineract.infrastructure.documentmanagement.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class ContentManagementException extends AbstractPlatformDomainRuleException {
    public ContentManagementException(final String filename, final String message) {
        super("error.msg.document.save", "Error while manipulating file " + filename + " due to a ContentRepository issue " + message,
                filename, message);
    }
    public ContentManagementException(final String name, final Long fileSize, final int maxFileSize) {
        super("error.msg.document.file.too.big", "Unable to save the document with name" + name + " since its file Size of "
                + fileSize / (1024 * 1024) + " MB exceeds the max permissable file size  of " + maxFileSize + " MB", name, fileSize);
    }
    public ContentManagementException(String filename, String message, Exception exception) {
        super("error.msg.document.save", "Error while manipulating file " + filename + " due to a ContentRepository issue " + message,
                filename, message, exception);
    }
}
