
package org.apache.fineract.portfolio.account.api;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.fineract.portfolio.account.AccountDetailConstants;
public final class StandingInstructionApiConstants {
    private StandingInstructionApiConstants() {
    }
    public static final String STANDING_INSTRUCTION_RESOURCE_NAME = "standinginstruction";
    public static final String nameParamName = "name";
    public static final String priorityParamName = "priority";
    public static final String instructionTypeParamName = "instructionType";
    public static final String statusParamName = "status";
    public static final String amountParamName = "amount";
    public static final String validFromParamName = "validFrom";
    public static final String validTillParamName = "validTill";
    public static final String recurrenceTypeParamName = "recurrenceType";
    public static final String recurrenceFrequencyParamName = "recurrenceFrequency";
    public static final String recurrenceIntervalParamName = "recurrenceInterval";
    public static final String recurrenceOnMonthDayParamName = "recurrenceOnMonthDay";
    public static final String monthDayFormatParamName = "monthDayFormat";
    static final Set<String> RESPONSE_DATA_PARAMETERS = new HashSet<>(
            Arrays.asList(AccountDetailConstants.idParamName, nameParamName, priorityParamName, instructionTypeParamName, statusParamName,
                    AccountDetailConstants.transferTypeParamName, validFromParamName, validTillParamName));
}
