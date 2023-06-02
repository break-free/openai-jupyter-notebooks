
package org.apache.fineract.portfolio.transfer.api;
public final class TransferApiConstants {
    private TransferApiConstants() {
    }
    public static final String localeParamName = "locale";
    public static final String dateFormatParamName = "dateFormat";
    public static final String idParamName = "id";
    public static final String destinationGroupIdParamName = "destinationGroupId";
    public static final String clients = "clients";
    public static final String inheritDestinationGroupLoanOfficer = "inheritDestinationGroupLoanOfficer";
    public static final String newStaffIdParamName = "staffId";
    public static final String transferActiveLoans = "transferActiveLoans";
    public static final String destinationOfficeIdParamName = "destinationOfficeId";
    public static final String note = "note";
    public static final String transferDate = "transferDate";
    public static final String transferClientLoanException = "error.msg.cannot.transfer.client.as.loan.transaction.present.on.or.after.transfer.date";
    public static final String transferClientLoanExceptionMessage = "error msg cannot transfer client as loan transaction present on or after transfer date";
    public static final String transferClientSavingsException = "error.msg.cannot.transfer.client.as.savings.transaction.present.on.or.after.transfer.date";
    public static final String transferClientSavingsExceptionMessage = "error msg cannot transfer client as savings transaction present on or after transfer date";
    public static final String transferClientToSameOfficeException = "error.msg.cannot.transfer.client.as.selected.office.and.current.office.are.same";
    public static final String transferClientToSameOfficeExceptionMessage = "error.msg.cannot.transfer.client.as.selected.office.and.current.office.are.same";
}
