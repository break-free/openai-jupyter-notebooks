
package org.apache.fineract.client.util;
import java.io.File;
import java.util.Optional;
import okhttp3.MediaType;
import okhttp3.MultipartBody.Part;
import okhttp3.RequestBody;
import org.apache.fineract.client.services.DocumentsApiFixed;
import org.apache.fineract.client.services.ImagesApi;
import retrofit2.Response;
public final class Parts {
    private Parts() {}
    public static Part fromFile(File file) {
        RequestBody rb = RequestBody.create(file, mediaType(file.getName()));
        return Part.createFormData("file", file.getName(), rb);
    }
    public static Part fromBytes(String fileName, byte[] bytes) {
        RequestBody rb = RequestBody.create(bytes, mediaType(fileName));
        return Part.createFormData("file", fileName, rb);
    }
    static MediaType mediaType(String fileName) {
        if (fileName == null) {
            return null;
        }
        int dotPos = fileName.lastIndexOf('.');
        if (dotPos == -1) {
            return null;
        }
        String ext = fileName.substring(dotPos + 1);
        switch (ext) {
            case "jpg":
            case "jpeg":
                return MediaType.get("image/jpeg");
            case "png":
                return MediaType.get("image/png");
            case "tif":
            case "tiff":
                return MediaType.get("image/tiff");
            case "gif":
                return MediaType.get("image/gif");
            case "pdf":
                return MediaType.get("application/pdf");
            case "docx":
                return MediaType.get("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            case "doc":
                return MediaType.get("application/msword");
            case "xlsx":
                return MediaType.get("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            case "xls":
                return MediaType.get("application/vnd.ms-excel");
            case "odt":
                return MediaType.get("application/vnd.oasis.opendocument.text");
            case "ods":
                return MediaType.get("application/vnd.oasis.opendocument.spreadsheet");
            case "txt":
                return MediaType.get("text/plain");
            default:
                return null;
        }
    }
    public static Optional<String> fileName(Response<?> response) {
        String contentDisposition = response.headers().get("Content-Disposition");
        if (contentDisposition == null) {
            return Optional.empty();
        }
        int i = contentDisposition.indexOf("; filename=\"");
        if (i == -1) {
            return Optional.empty();
        }
        return Optional.of(contentDisposition.substring(i + "; filename=\"".length(), contentDisposition.length() - 1));
    }
}
