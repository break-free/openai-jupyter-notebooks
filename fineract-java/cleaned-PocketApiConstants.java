
package org.apache.fineract.portfolio.self.pockets.api;
public interface PocketApiConstants {
    String pocketsResourceName = "pockets";
    String linkAccountsToPocketCommandParam = "linkAccounts";
    String delinkAccountsFromPocketCommandParam = "delinkAccounts";
    String linkAccountsActionName = "LINK_ACCOUNT_TO";
    String pocketEntityName = "POCKET";
    String delinkAccountsActionName = "DELINK_ACCOUNT_FROM";
    String accountIdParamName = "accountId";
    String accountTypeParamName = "accountType";
    String accountsDetail = "accountsDetail";
    String pocketAccountMappingList = "pocketAccountMappingIds";
    String pocketAccountMappingId = "pocketAccountMappingId";
    String dataValidationMessage = "validation.msg.validation.errors.exist";
    String validationErrorMessage = "Validation errors exist.";
    String pocketNotFoundException = "error.msg.pocket.not.found";
    String pocketNotFoundErrorMessage = "Pocket not found.";
    String mappingIdNotLinkedToPocketException = "mapping.id.not.linked.to.pocket.exception";
    String mappingIdNotLinkedToPocketErrorMessage = "Mapping Id is not linked to Pocket.";
    String uniqueConstraintName = "m_pocket_account_unique_mapping";
    String duplicateMappingException = "error.msg.one.or.more.accounts.are.already.mapped.to.pocket.";
    String duplicateMappingExceptionMessage = "One or more accounts are already mapped to pocket.";
    String unknownDataIntegrityException = "error.msg.unknown.data.integrity.issue";
    String unknownDataIntegrityExceptionMessage = "Unknown data integrity issue with resource.";
}
