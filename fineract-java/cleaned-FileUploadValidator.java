
package org.apache.fineract.infrastructure.documentmanagement.api;
import java.io.InputStream;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.springframework.stereotype.Component;
@Component
public class FileUploadValidator {
    public void validate(Long contentLength, InputStream inputStream, FormDataContentDisposition fileDetails, FormDataBodyPart bodyPart) {
        new DataValidatorBuilder().resource("fileUpload").reset().parameter("Content-Length").value(contentLength).notBlank()
                .integerGreaterThanNumber(0).reset().parameter("InputStream").value(inputStream).notNull().reset()
                .parameter("FormDataContentDisposition").value(fileDetails).notNull().reset().parameter("FormDataBodyPart").value(bodyPart)
                .notNull().throwValidationErrors();
    }
}
