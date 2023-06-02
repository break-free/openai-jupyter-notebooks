
package org.apache.fineract.infrastructure.creditbureau.data;
import java.io.Serializable;
public final class CreditBureauReportData implements Serializable {
    @SuppressWarnings("unused")
    private final String name;
    private final String gender;
    private final String address;
    private final String creditScore;
    private final String borrowerInfo;
    private final String[] openAccounts;
    private final String[] closedAccounts;
    public static CreditBureauReportData instance(final String name, final String gender, final String address, final String creditScore,
            final String borrowerInfo, final String[] openAccounts, final String[] closedAccounts) {
        return new CreditBureauReportData(name, gender, address, creditScore, borrowerInfo, openAccounts, closedAccounts);
    }
    public CreditBureauReportData(final String name, final String gender, final String address, final String creditScore,
            final String borrowerInfo, final String[] openAccounts, final String[] closedAccounts) {
        this.name = name;
        this.gender = gender;
        this.address = address;
        this.creditScore = creditScore;
        this.borrowerInfo = borrowerInfo;
        this.openAccounts = openAccounts;
        this.closedAccounts = closedAccounts;
    }
    public String getName() {
        return name;
    }
    public String getGender() {
        return gender;
    }
    public String getAddress() {
        return address;
    }
    public String getCreditScore() {
        return creditScore;
    }
    public String getBorrowerInfo() {
        return borrowerInfo;
    }
    public String[] getOpenAccounts() {
        return openAccounts;
    }
    public String[] getClosedAccounts() {
        return closedAccounts;
    }
}
