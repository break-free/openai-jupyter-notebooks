
package org.apache.fineract.infrastructure.documentmanagement.api;
import com.google.common.io.ByteSource;
import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.fineract.infrastructure.documentmanagement.data.FileData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
final class ContentResources {
    private static final Logger LOG = LoggerFactory.getLogger(ContentResources.class);
    private ContentResources() {}
    static Response fileDataToResponse(FileData fileData, String fileName, String dispositionType) {
        ResponseBuilder response;
        try {
            ByteSource byteSource = fileData.getByteSource();
            InputStream is = byteSource.openBufferedStream();
            response = Response.ok(is);
            response.header("Content-Disposition", dispositionType + "; filename=\"" + fileName + "\"");
            response.header("Content-Length", byteSource.sizeIfKnown().or(-1L));
            response.header("Content-Type", fileData.contentType());
        } catch (IOException e) {
            LOG.error("resizedImage.getByteSource().openBufferedStream() failed", e);
            response = Response.serverError();
        }
        return response.build();
    }
    static Response fileDataToResponse(FileData fileData, String dispositionType) {
        return fileDataToResponse(fileData, fileData.name(), dispositionType);
    }
}
