
package org.apache.fineract.infrastructure.businessdate.data;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.time.ZoneId;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
import org.apache.fineract.infrastructure.core.serialization.CommandProcessingResultJsonSerializer;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.core.serialization.ExcludeNothingWithPrettyPrintingOffJsonSerializerGoogleGson;
import org.apache.fineract.infrastructure.core.serialization.ExcludeNothingWithPrettyPrintingOnJsonSerializerGoogleGson;
import org.apache.fineract.infrastructure.core.serialization.GoogleGsonSerializerHelper;
import org.junit.Test;
public class BusinessDataSerialized {
    @Test
    public void serializeBusinessDateData() {
        DefaultToApiJsonSerializer<BusinessDateData> jsonSerializer = new DefaultToApiJsonSerializer<>(
                new ExcludeNothingWithPrettyPrintingOffJsonSerializerGoogleGson(),
                new ExcludeNothingWithPrettyPrintingOnJsonSerializerGoogleGson(), new CommandProcessingResultJsonSerializer(),
                new GoogleGsonSerializerHelper());
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        BusinessDateData businessDateData = BusinessDateData.instance(BusinessDateType.BUSINESS_DATE, now);
        String result = jsonSerializer.serialize(businessDateData);
        assertEquals("{\"description\":\"Business Date\",\"type\":\"BUSINESS_DATE\",\"date\":[" + now.getYear() + "," + now.getMonthValue()
                + "," + now.getDayOfMonth() + "]}", result);
    }
    @Test
    public void serializeBusinessDateData_COB() {
        DefaultToApiJsonSerializer<BusinessDateData> jsonSerializer = new DefaultToApiJsonSerializer<>(
                new ExcludeNothingWithPrettyPrintingOffJsonSerializerGoogleGson(),
                new ExcludeNothingWithPrettyPrintingOnJsonSerializerGoogleGson(), new CommandProcessingResultJsonSerializer(),
                new GoogleGsonSerializerHelper());
        LocalDate now = LocalDate.now(ZoneId.systemDefault());
        BusinessDateData businessDateData = BusinessDateData.instance(BusinessDateType.COB_DATE, now);
        String result = jsonSerializer.serialize(businessDateData);
        assertEquals("{\"description\":\"Close of Business Date\",\"type\":\"COB_DATE\",\"date\":[" + now.getYear() + ","
                + now.getMonthValue() + "," + now.getDayOfMonth() + "]}", result);
    }
}
