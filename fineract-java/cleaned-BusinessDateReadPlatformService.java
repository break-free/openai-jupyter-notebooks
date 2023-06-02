
package org.apache.fineract.infrastructure.businessdate.service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import org.apache.fineract.infrastructure.businessdate.data.BusinessDateData;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
public interface BusinessDateReadPlatformService {
    List<BusinessDateData> findAll();
    BusinessDateData findByType(String type);
    HashMap<BusinessDateType, LocalDate> getBusinessDates();
}
