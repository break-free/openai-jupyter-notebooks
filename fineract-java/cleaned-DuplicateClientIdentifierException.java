
package org.apache.fineract.portfolio.client.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class DuplicateClientIdentifierException extends AbstractPlatformDomainRuleException {
    private final Long documentTypeId;
    private final String identifierKey;
    private final String identifierType;
    public DuplicateClientIdentifierException(final String identifierType) {
        super("error.msg.clientIdentifier.type.duplicate",
                "Active Client identifier of type " + identifierType + " is already present for this client", identifierType);
        this.identifierType = identifierType;
        this.identifierKey = null;
        this.documentTypeId = null;
    }
    public DuplicateClientIdentifierException(final Long documentTypeId, final String identifierType, final String identifierKey) {
        super("error.msg.clientIdentifier.identityKey.duplicate",
                "Client identifier of type " + identifierType + " with value of " + identifierKey + " already exists.", identifierType,
                identifierKey);
        this.documentTypeId = documentTypeId;
        this.identifierType = identifierType;
        this.identifierKey = identifierKey;
    }
    public DuplicateClientIdentifierException(final String clientName, final String officeName, final String identifierType,
            final String identifierKey) {
        super("error.msg.clientIdentifier.identityKey.duplicate", "Client " + clientName + "under " + officeName + " Branch already has a "
                + identifierType + " with unique key " + identifierKey, clientName, officeName, identifierType, identifierKey);
        this.identifierType = identifierType;
        this.identifierKey = identifierKey;
        this.documentTypeId = null;
    }
    public Long getDocumentTypeId() {
        return this.documentTypeId;
    }
    public String getIdentifierKey() {
        return this.identifierKey;
    }
    public String getIdentifierType() {
        return this.identifierType;
    }
}
