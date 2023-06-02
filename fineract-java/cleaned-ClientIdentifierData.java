
package org.apache.fineract.portfolio.client.data;
import java.util.Collection;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
public class ClientIdentifierData {
    private final Long id;
    private final Long clientId;
    private final CodeValueData documentType;
    private final String documentKey;
    private final String description;
    private final String status;
    @SuppressWarnings("unused")
    private final Collection<CodeValueData> allowedDocumentTypes;
    public static ClientIdentifierData singleItem(final Long id, final Long clientId, final CodeValueData documentType,
            final String documentKey, final String status, final String description) {
        return new ClientIdentifierData(id, clientId, documentType, documentKey, description, status, null);
    }
    public static ClientIdentifierData template(final Collection<CodeValueData> codeValues) {
        return new ClientIdentifierData(null, null, null, null, null, null, codeValues);
    }
    public static ClientIdentifierData template(final ClientIdentifierData data, final Collection<CodeValueData> codeValues) {
        return new ClientIdentifierData(data.id, data.clientId, data.documentType, data.documentKey, data.description, data.status,
                codeValues);
    }
    public ClientIdentifierData(final Long id, final Long clientId, final CodeValueData documentType, final String documentKey,
            final String description, final String status, final Collection<CodeValueData> allowedDocumentTypes) {
        this.id = id;
        this.clientId = clientId;
        this.documentType = documentType;
        this.documentKey = documentKey;
        this.description = description;
        this.allowedDocumentTypes = allowedDocumentTypes;
        this.status = status;
    }
}
