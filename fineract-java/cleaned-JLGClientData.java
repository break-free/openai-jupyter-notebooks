
package org.apache.fineract.portfolio.collectionsheet.data;
import java.util.Collection;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public final class JLGClientData {
    private final Long clientId;
    private final String clientName;
    private Collection<LoanDueData> loans;
    private Collection<SavingsDueData> savings;
    @SuppressWarnings("unused")
    private final EnumOptionData attendanceType;
    public static JLGClientData instance(final Long clientId, final String clientName, final EnumOptionData attendanceType) {
        final Collection<LoanDueData> loans = null;
        final Collection<SavingsDueData> savings = null;
        return new JLGClientData(clientId, clientName, loans, savings, attendanceType);
    }
    public static JLGClientData withSavings(final JLGClientData client, final Collection<SavingsDueData> savings) {
        final Collection<LoanDueData> loans = null;
        final EnumOptionData attendanceType = null;
        return new JLGClientData(client.clientId, client.clientName, loans, savings, attendanceType);
    }
    public static JLGClientData withAttendance(final Long clientId, final String clientName, final EnumOptionData attendanceType) {
        final Collection<LoanDueData> loans = null;
        final Collection<SavingsDueData> savings = null;
        return new JLGClientData(clientId, clientName, loans, savings, attendanceType);
    }
    private JLGClientData(Long clientId, String clientName, Collection<LoanDueData> loans, Collection<SavingsDueData> savings,
            EnumOptionData attendanceType) {
        this.clientId = clientId;
        this.clientName = clientName;
        this.loans = loans;
        this.savings = savings;
        this.attendanceType = attendanceType;
    }
    public Long getClientId() {
        return this.clientId;
    }
    public String getClientName() {
        return this.clientName;
    }
    public Collection<LoanDueData> getLoans() {
        return this.loans;
    }
    public void setLoans(final Collection<LoanDueData> loans) {
        this.loans = loans;
    }
    public Collection<SavingsDueData> getSavings() {
        return this.savings;
    }
    public void setSavings(Collection<SavingsDueData> savings) {
        this.savings = savings;
    }
    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof JLGClientData)) {
            return false;
        }
        final JLGClientData clientData = (JLGClientData) obj;
        return clientData.clientId.compareTo(this.clientId) == 0;
    }
    @Override
    public int hashCode() {
        return this.clientId.hashCode();
    }
}
