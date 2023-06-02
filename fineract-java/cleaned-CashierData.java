
package org.apache.fineract.organisation.teller.data;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import org.apache.fineract.organisation.staff.data.StaffData;
public final class CashierData implements Serializable {
    private final Long id;
    private final Long tellerId;
    private final Long officeId;
    private final Long staffId;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final Boolean isFullDay;
    private final String startTime;
    private final String endTime;
    private final String officeName;
    private final String tellerName;
    private final String staffName;
    private final Collection<StaffData> staffOptions;
    private CashierData(final Long id, final Long officeId, String officeName, final Long staffId, final String staffName,
            final Long tellerId, final String tellerName, final String description, final LocalDate startDate, final LocalDate endDate,
            final Boolean isFullDay, final String startTime, final String endTime, Collection<StaffData> staffOptions) {
        this.id = id;
        this.officeId = officeId;
        this.staffId = staffId;
        this.tellerId = tellerId;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFullDay = isFullDay;
        this.startTime = startTime;
        this.endTime = endTime;
        this.officeName = officeName;
        this.tellerName = tellerName;
        this.staffName = staffName;
        this.staffOptions = staffOptions;
    }
    public static CashierData instance(final Long id, final Long officeId, String officeName, final Long staffId, final String staffName,
            final Long tellerId, final String tellerName, final String description, final LocalDate startDate, final LocalDate endDate,
            final Boolean isFullDay, final String startTime, final String endTime) {
        return new CashierData(id, officeId, officeName, staffId, staffName, tellerId, tellerName, description, startDate, endDate,
                isFullDay, startTime, endTime, null);
    }
    public static CashierData template(final Long officeId, final String officeName, final Long tellerId, final String tellerName,
            final Collection<StaffData> staffOptions) {
        return new CashierData(null, officeId, officeName, null, null, tellerId, tellerName, null, null, null, null, null, null,
                staffOptions);
    }
    public Long getId() {
        return id;
    }
    public Long getOfficeId() {
        return officeId;
    }
    public Long getStaffId() {
        return staffId;
    }
    public Long getTellerId() {
        return tellerId;
    }
    public String getDescription() {
        return description;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public Boolean isFullDay() {
        return isFullDay;
    }
    public String getStartTime() {
        return startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public String getOfficeName() {
        return officeName;
    }
    public String getTellerName() {
        return tellerName;
    }
    public String getStaffName() {
        return staffName;
    }
}
