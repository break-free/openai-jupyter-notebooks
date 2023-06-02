
package org.apache.fineract.mix.service;
import java.sql.Date;
import org.apache.fineract.mix.data.XBRLData;
public interface XBRLResultService {
    XBRLData getXBRLResult(Date startDate, Date endDate, String currency);
}
