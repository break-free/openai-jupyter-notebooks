
package org.apache.fineract.organisation.staff.data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.organisation.office.data.OfficeData;
public final class StaffData implements Serializable {
    private final Long id;
    private final String externalId;
    private final String firstname;
    private final String lastname;
    private final String displayName;
    private final String mobileNo;
    private final Long officeId;
    private final String officeName;
    private final Boolean isLoanOfficer;
    private final Boolean isActive;
    private final LocalDate joiningDate;
    private transient Integer rowIndex;
    private String dateFormat;
    private String locale;
    public static StaffData importInstance(String externalId, String firstname, String lastname, String mobileNo, Long officeId,
            Boolean isLoanOfficer, Boolean isActive, LocalDate joinedOnDate, Integer rowIndex, String locale, String dateFormat) {
        return new StaffData(externalId, firstname, lastname, mobileNo, officeId, isLoanOfficer, isActive, joinedOnDate, rowIndex, locale,
                dateFormat);
    }
    private StaffData(String externalId, String firstname, String lastname, String mobileNo, Long officeId, Boolean isLoanOfficer,
            Boolean isActive, LocalDate joiningDate, Integer rowIndex, String locale, String dateFormat) {
        this.externalId = externalId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.mobileNo = mobileNo;
        this.officeId = officeId;
        this.isLoanOfficer = isLoanOfficer;
        this.isActive = isActive;
        this.joiningDate = joiningDate;
        this.rowIndex = rowIndex;
        this.dateFormat = dateFormat;
        this.locale = locale;
        this.allowedOffices = null;
        this.id = null;
        this.officeName = null;
        this.displayName = null;
    }
    public Integer getRowIndex() {
        return rowIndex;
    }
    @SuppressWarnings("unused")
    private final Collection<OfficeData> allowedOffices;
    public static StaffData templateData(final StaffData staff, final Collection<OfficeData> allowedOffices) {
        return new StaffData(staff.id, staff.firstname, staff.lastname, staff.displayName, staff.officeId, staff.officeName,
                staff.isLoanOfficer, staff.externalId, staff.mobileNo, allowedOffices, staff.isActive, staff.joiningDate);
    }
    public static StaffData lookup(final Long id, final String displayName) {
        return new StaffData(id, null, null, displayName, null, null, null, null, null, null, null, null);
    }
    public static StaffData instance(final Long id, final String firstname, final String lastname, final String displayName,
            final Long officeId, final String officeName, final Boolean isLoanOfficer, final String externalId, final String mobileNo,
            final boolean isActive, final LocalDate joiningDate) {
        return new StaffData(id, firstname, lastname, displayName, officeId, officeName, isLoanOfficer, externalId, mobileNo, null,
                isActive, joiningDate);
    }
    private StaffData(final Long id, final String firstname, final String lastname, final String displayName, final Long officeId,
            final String officeName, final Boolean isLoanOfficer, final String externalId, final String mobileNo,
            final Collection<OfficeData> allowedOffices, final Boolean isActive, final LocalDate joiningDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.displayName = displayName;
        this.officeName = officeName;
        this.isLoanOfficer = isLoanOfficer;
        this.externalId = externalId;
        this.officeId = officeId;
        this.mobileNo = mobileNo;
        this.allowedOffices = allowedOffices;
        this.isActive = isActive;
        this.joiningDate = joiningDate;
    }
    public Long getId() {
        return this.id;
    }
    public String getDisplayName() {
        return this.displayName;
    }
    public String getFirstname() {
        return this.firstname;
    }
    public String getLastname() {
        return this.lastname;
    }
    public String getOfficeName() {
        return this.officeName;
    }
    public LocalDate getJoiningDate() {
        return this.joiningDate;
    }
    public Long getOfficeId() {
        return this.officeId;
    }
}
