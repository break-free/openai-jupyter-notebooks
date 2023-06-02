
package org.apache.fineract.portfolio.note.service;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
import org.apache.fineract.portfolio.note.domain.NoteType;
public final class NoteEnumerations {
    private NoteEnumerations() {
    }
    public static EnumOptionData noteType(final Integer id) {
        return noteType(NoteType.fromInt(id));
    }
    public static EnumOptionData noteType(final NoteType type) {
        EnumOptionData optionData = null;
        switch (type) {
            case CLIENT:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Client note");
            break;
            case LOAN:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Loan note");
            break;
            case LOAN_TRANSACTION:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Loan transaction note");
            break;
            case SAVING_ACCOUNT:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Saving account note");
            break;
            case GROUP:
                optionData = new EnumOptionData(type.getValue().longValue(), type.getCode(), "Group note");
            break;
            default:
            break;
        }
        return optionData;
    }
}
