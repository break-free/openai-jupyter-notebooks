
package org.apache.fineract.template.domain;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import org.apache.fineract.infrastructure.core.service.DateUtils;
@SuppressWarnings({ "HideUtilityClassConstructor" })
public class TemplateFunctions {
    public static String now() {
        final DateTimeFormatter dateFormat = new DateTimeFormatterBuilder().appendPattern("yyyy/MM/dd HH:mm").toFormatter();
        final LocalDateTime date = DateUtils.getLocalDateTimeOfSystem();
        return dateFormat.format(date);
    }
}
