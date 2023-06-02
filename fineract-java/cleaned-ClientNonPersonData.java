
package org.apache.fineract.portfolio.client.data;
import java.io.Serializable;
import java.time.LocalDate;
import org.apache.fineract.infrastructure.codes.data.CodeValueData;
@SuppressWarnings("unused")
public class ClientNonPersonData implements Serializable {
    private final CodeValueData constitution;
    private final String incorpNumber;
    private final LocalDate incorpValidityTillDate;
    private final CodeValueData mainBusinessLine;
    private final String remarks;
    private Long mainBusinessLineId;
    private Long constitutionId;
    private String locale;
    private String dateFormat;
    public static ClientNonPersonData importInstance(String incorporationNo, LocalDate incorpValidityTillDate, String remarks,
            Long mainBusinessLineId, Long constitutionId, String locale, String dateFormat) {
        return new ClientNonPersonData(incorporationNo, incorpValidityTillDate, remarks, mainBusinessLineId, constitutionId, locale,
                dateFormat);
    }
    private ClientNonPersonData(String incorpNumber, LocalDate incorpValidityTillDate, String remarks, Long mainBusinessLineId,
            Long constitutionId, String locale, String dateFormat) {
        this.incorpNumber = incorpNumber;
        this.incorpValidityTillDate = incorpValidityTillDate;
        this.remarks = remarks;
        this.mainBusinessLineId = mainBusinessLineId;
        this.constitutionId = constitutionId;
        this.dateFormat = dateFormat;
        this.locale = locale;
        this.constitution = null;
        this.mainBusinessLine = null;
    }
    public ClientNonPersonData(CodeValueData constitution, String incorpNo, LocalDate incorpValidityTillDate,
            CodeValueData mainBusinessLine, String remarks) {
        this.constitution = constitution;
        this.incorpNumber = incorpNo;
        this.incorpValidityTillDate = incorpValidityTillDate;
        this.mainBusinessLine = mainBusinessLine;
        this.remarks = remarks;
    }
}
