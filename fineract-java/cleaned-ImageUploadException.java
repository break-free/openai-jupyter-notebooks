
package org.apache.fineract.infrastructure.core.exception;
public class ImageUploadException extends AbstractPlatformDomainRuleException {
    public ImageUploadException(String badMimeType) {
        super("error.msg.image.type.upload", "Only image files of type GIF,PNG and JPG are allowed, but not: " + badMimeType);
    }
}
