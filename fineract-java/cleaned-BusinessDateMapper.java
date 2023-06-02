
package org.apache.fineract.infrastructure.businessdate.mapper;
import java.util.List;
import org.apache.fineract.infrastructure.businessdate.data.BusinessDateData;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
@Mapper(componentModel = "spring")
public interface BusinessDateMapper {
    @Mappings({ @Mapping(target = "businessDateType", source = "source.type"), @Mapping(target = "date", source = "source.date") })
    BusinessDateData map(BusinessDate source);
    List<BusinessDateData> map(List<BusinessDate> sources);
}
