
package org.apache.fineract.infrastructure.businessdate.data;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.ToString;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
@ToString
@Getter
public class BusinessDateData implements Serializable {
    private String description;
    private String type;
    private LocalDate date;
    public BusinessDateData(BusinessDateType businessDateType, LocalDate date) {
        this.type = businessDateType.getName();
        this.description = businessDateType.getDescription();
        this.date = date;
    }
    public static BusinessDateData instance(BusinessDateType businessDateType, LocalDate value) {
        return new BusinessDateData(businessDateType, value);
    }
}
