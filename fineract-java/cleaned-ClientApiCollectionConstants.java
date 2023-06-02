
package org.apache.fineract.portfolio.client.data;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.portfolio.client.api.ClientApiConstants;
public class ClientApiCollectionConstants extends ClientApiConstants {
    protected static final Set<String> CLIENT_CREATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(familyMembers, address,
            localeParamName, dateFormatParamName, groupIdParamName, accountNoParamName, externalIdParamName, mobileNoParamName,
            emailAddressParamName, firstnameParamName, middlenameParamName, lastnameParamName, fullnameParamName, officeIdParamName,
            activeParamName, activationDateParamName, staffIdParamName, submittedOnDateParamName, savingsProductIdParamName,
            dateOfBirthParamName, genderIdParamName, clientTypeIdParamName, clientClassificationIdParamName,
            clientNonPersonDetailsParamName, displaynameParamName, legalFormIdParamName, datatables, isStaffParamName));
    protected static final Set<String> CLIENT_NON_PERSON_CREATE_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(familyMembers, address, localeParamName, dateFormatParamName, incorpNumberParamName, remarksParamName,
                    incorpValidityTillParamName, constitutionIdParamName, mainBusinessLineIdParamName, datatables));
    protected static final Set<String> CLIENT_UPDATE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(localeParamName,
            dateFormatParamName, accountNoParamName, externalIdParamName, mobileNoParamName, emailAddressParamName, firstnameParamName,
            middlenameParamName,
            lastnameParamName, fullnameParamName, activeParamName, activationDateParamName, staffIdParamName, savingsProductIdParamName,
            dateOfBirthParamName, genderIdParamName, clientTypeIdParamName, clientClassificationIdParamName, submittedOnDateParamName,
            clientNonPersonDetailsParamName, displaynameParamName, legalFormIdParamName, isStaffParamName));
    protected static final Set<String> CLIENT_NON_PERSON_UPDATE_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, incorpNumberParamName, remarksParamName, incorpValidityTillParamName,
                    constitutionIdParamName, mainBusinessLineIdParamName));
    protected static final Set<String> ACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, activationDateParamName));
    protected static final Set<String> REACTIVATION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, reactivationDateParamName));
    protected static final Set<String> CLIENT_CLOSE_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, closureDateParamName, closureReasonIdParamName));
    protected static final Set<String> CLIENT_REJECT_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, rejectionDateParamName, rejectionReasonIdParamName));
    protected static final Set<String> CLIENT_WITHDRAW_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, withdrawalDateParamName, withdrawalReasonIdParamName));
    protected static final Set<String> UNDOREJECTION_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, reopenedDateParamName));
    protected static final Set<String> UNDOWITHDRAWN_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(localeParamName, dateFormatParamName, reopenedDateParamName));
    protected static final Set<String> CLIENT_CHARGES_ADD_REQUEST_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(chargeIdParamName, amountParamName, dueAsOfDateParamName, dateFormatParamName, localeParamName));
    protected static final Set<String> CLIENT_CHARGES_PAY_CHARGE_REQUEST_DATA_PARAMETERS = new HashSet<>(Arrays.asList(amountParamName,
            transactionDateParamName, dateFormatParamName, localeParamName, paymentTypeIdParamName, transactionAccountNumberParamName,
            checkNumberParamName, routingCodeParamName, receiptNumberParamName, bankNumberParamName));
}
