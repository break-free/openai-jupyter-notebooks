
package org.apache.fineract.organisation.teller.domain;
import com.google.common.base.Splitter;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import org.apache.fineract.infrastructure.core.api.JsonCommand;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.organisation.office.domain.Office;
import org.apache.fineract.organisation.staff.domain.Staff;
@Entity
@Table(name = "m_cashiers", uniqueConstraints = {
        @UniqueConstraint(name = "ux_cashiers_staff_teller", columnNames = { "staff_id", "teller_id" }) })
public class Cashier extends AbstractPersistableCustom {
    @Transient
    private Office office;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teller_id", nullable = false)
    private Teller teller;
    @Column(name = "description", nullable = true, length = 500)
    private String description;
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;
    @Column(name = "full_day", nullable = true)
    private Boolean isFullDay;
    @Column(name = "start_time", nullable = true, length = 10)
    private String startTime;
    @Column(name = "end_time", nullable = true, length = 10)
    private String endTime;
    public Cashier() {
    }
    public static Cashier fromJson(final Office cashierOffice, final Teller teller, final Staff staff, final String startTime,
            final String endTime, final JsonCommand command) {
        final String description = command.stringValueOfParameterNamed("description");
        final LocalDate startDate = command.localDateValueOfParameterNamed("startDate");
        final LocalDate endDate = command.localDateValueOfParameterNamed("endDate");
        final Boolean isFullDay = command.booleanObjectValueOfParameterNamed("isFullDay");
        return new Cashier(cashierOffice, teller, staff, description, startDate, endDate, isFullDay, startTime, endTime);
    }
    public Cashier(Office office, Teller teller, Staff staff, String description, LocalDate startDate, LocalDate endDate, Boolean isFullDay,
            String startTime, String endTime) {
        this.office = office;
        this.teller = teller;
        this.staff = staff;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isFullDay = isFullDay;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public Map<String, Object> update(final JsonCommand command) {
        final Map<String, Object> actualChanges = new LinkedHashMap<>(7);
        final String dateFormatAsInput = command.dateFormat();
        final String localeAsInput = command.locale();
        final String descriptionParamName = "description";
        if (command.isChangeInStringParameterNamed(descriptionParamName, this.description)) {
            final String newValue = command.stringValueOfParameterNamed(descriptionParamName);
            actualChanges.put(descriptionParamName, newValue);
            this.description = newValue;
        }
        final String startDateParamName = "startDate";
        if (command.isChangeInLocalDateParameterNamed(startDateParamName, getStartDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(startDateParamName);
            actualChanges.put(startDateParamName, valueAsInput);
            actualChanges.put("dateFormat", dateFormatAsInput);
            actualChanges.put("locale", localeAsInput);
            this.startDate = command.localDateValueOfParameterNamed(startDateParamName);
        }
        final String endDateParamName = "endDate";
        if (command.isChangeInLocalDateParameterNamed(endDateParamName, getEndDate())) {
            final String valueAsInput = command.stringValueOfParameterNamed(endDateParamName);
            actualChanges.put(endDateParamName, valueAsInput);
            actualChanges.put("dateFormat", dateFormatAsInput);
            actualChanges.put("locale", localeAsInput);
            this.endDate = command.localDateValueOfParameterNamed(endDateParamName);
        }
        final Boolean isFullDay = command.booleanObjectValueOfParameterNamed("isFullDay");
        final String isFullDayParamName = "isFullDay";
        if (command.isChangeInBooleanParameterNamed(isFullDayParamName, this.isFullDay)) {
            final Boolean newValue = command.booleanObjectValueOfParameterNamed(isFullDayParamName);
            actualChanges.put(isFullDayParamName, newValue);
            this.isFullDay = newValue;
        }
        if (!isFullDay) {
            String newStartHour = "";
            String newStartMin = "";
            String newEndHour = "";
            String newEndMin = "";
            final String hourStartTimeParamName = "hourStartTime";
            final String minStartTimeParamName = "minStartTime";
            final String hourEndTimeParamName = "hourEndTime";
            final String minEndTimeParamName = "minEndTime";
            if (command.isChangeInLongParameterNamed(hourStartTimeParamName, this.getHourFromStartTime())
                    || command.isChangeInLongParameterNamed(minStartTimeParamName, this.getMinFromStartTime())) {
                newStartHour = command.stringValueOfParameterNamed(hourStartTimeParamName);
                if (newEndHour.equalsIgnoreCase("0")) {
                    newEndHour = newEndHour + "0";
                }
                actualChanges.put(hourStartTimeParamName, newStartHour);
                newStartMin = command.stringValueOfParameterNamed(minStartTimeParamName);
                if (newStartMin.equalsIgnoreCase("0")) {
                    newStartMin = newStartMin + "0";
                }
                actualChanges.put(minStartTimeParamName, newStartMin);
                this.startTime = newStartHour + ":" + newStartMin;
            }
            if (command.isChangeInLongParameterNamed(hourEndTimeParamName, this.getHourFromEndTime())
                    || command.isChangeInLongParameterNamed(minEndTimeParamName, this.getMinFromEndTime())) {
                newEndHour = command.stringValueOfParameterNamed(hourEndTimeParamName);
                if (newEndHour.equalsIgnoreCase("0")) {
                    newEndHour = newEndHour + "0";
                }
                actualChanges.put(hourEndTimeParamName, newEndHour);
                newEndMin = command.stringValueOfParameterNamed(minEndTimeParamName);
                if (newEndMin.equalsIgnoreCase("0")) {
                    newEndMin = newEndMin + "0";
                }
                actualChanges.put(minEndTimeParamName, newEndMin);
                this.endTime = newEndHour + ":" + newEndMin;
            }
        }
        return actualChanges;
    }
    public Office getOffice() {
        return office;
    }
    public Long getHourFromStartTime() {
        if (this.startTime != null && !this.startTime.equalsIgnoreCase("")) {
            List<String> extractHourFromStartTime = Splitter.on(':').splitToList(this.startTime);
            Long hour = Long.parseLong(extractHourFromStartTime.get(1));
            return hour;
        }
        return null;
    }
    public Long getMinFromStartTime() {
        if (this.startTime != null && !this.startTime.equalsIgnoreCase("")) {
            List<String> extractMinFromStartTime = Splitter.on(':').splitToList(this.startTime);
            Long min = Long.parseLong(extractMinFromStartTime.get(1));
            return min;
        }
        return null;
    }
    public Long getHourFromEndTime() {
        if (this.endTime != null && !this.endTime.equalsIgnoreCase("")) {
            List<String> extractHourFromEndTime = Splitter.on(':').splitToList(this.endTime);
            Long hour = Long.parseLong(extractHourFromEndTime.get(0));
            return hour;
        }
        return null;
    }
    public Long getMinFromEndTime() {
        if (this.endTime != null && !this.endTime.equalsIgnoreCase("")) {
            List<String> extractMinFromEndTime = Splitter.on(':').splitToList(this.endTime);
            Long min = Long.parseLong(extractMinFromEndTime.get(1));
            return min;
        }
        return null;
    }
    public void setOffice(Office office) {
        this.office = office;
    }
    public Staff getStaff() {
        return staff;
    }
    public void setStaff(Staff staff) {
        this.staff = staff;
    }
    public Teller getTeller() {
        return teller;
    }
    public void setTeller(Teller teller) {
        this.teller = teller;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getStartDate() {
        return startDate;
    }
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    public LocalDate getEndDate() {
        return endDate;
    }
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    public Boolean isFullDay() {
        return isFullDay;
    }
    public void setFullDay(Boolean isFullDay) {
        this.isFullDay = isFullDay;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
