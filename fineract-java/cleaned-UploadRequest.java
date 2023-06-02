
package org.apache.fineract.infrastructure.core.data;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.File;
import java.io.InputStream;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
public class UploadRequest {
    @Schema(type = "string", format = "binary")
    @FormDataParam("file")
    private InputStream uploadedInputStream;
    @Schema(implementation = File.class, hidden = true)
    @FormDataParam("file")
    private File uploadedFile;
    @Schema(implementation = FormDataContentDisposition.class, hidden = true)
    @FormDataParam("file")
    private FormDataContentDisposition fileDetail;
    @Schema(implementation = UriInfo.class, hidden = true)
    @FormDataParam("file")
    private UriInfo uriInfo;
    @Schema(implementation = UriInfo.class, hidden = true)
    @FormDataParam("file")
    private FormDataBodyPart bodyPart;
    @Schema(name = "locale", type = "string", accessMode = Schema.AccessMode.READ_WRITE)
    @FormDataParam("locale")
    private String locale;
    @Schema(name = "dateFormat", type = "string", accessMode = Schema.AccessMode.READ_WRITE)
    @FormDataParam("dateFormat")
    private String dateFormat;
    public InputStream getUploadedInputStream() {
        return uploadedInputStream;
    }
    public void setUploadedInputStream(InputStream uploadedInputStream) {
        this.uploadedInputStream = uploadedInputStream;
    }
    public String getLocale() {
        return locale;
    }
    public void setLocale(String locale) {
        this.locale = locale;
    }
    public String getDateFormat() {
        return dateFormat;
    }
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
}
