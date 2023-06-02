
package org.apache.fineract.portfolio.charge.service;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.EnumOptionData;
public interface ChargeDropdownReadPlatformService {
    List<EnumOptionData> retrieveCalculationTypes();
    List<EnumOptionData> retrieveApplicableToTypes();
    List<EnumOptionData> retrieveCollectionTimeTypes();
    List<EnumOptionData> retrivePaymentModes();
    List<EnumOptionData> retrieveLoanCalculationTypes();
    List<EnumOptionData> retrieveLoanCollectionTimeTypes();
    List<EnumOptionData> retrieveSavingsCalculationTypes();
    List<EnumOptionData> retrieveSavingsCollectionTimeTypes();
    List<EnumOptionData> retrieveClientCalculationTypes();
    List<EnumOptionData> retrieveClientCollectionTimeTypes();
    List<EnumOptionData> retrieveSharesCalculationTypes();
    List<EnumOptionData> retrieveSharesCollectionTimeTypes();
}
