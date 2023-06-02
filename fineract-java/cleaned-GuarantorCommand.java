
package org.apache.fineract.portfolio.loanaccount.guarantor.command;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.fineract.infrastructure.core.data.ApiParameterError;
import org.apache.fineract.infrastructure.core.data.DataValidatorBuilder;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.portfolio.loanaccount.guarantor.GuarantorConstants.GuarantorJSONinputParams;
import org.apache.fineract.portfolio.loanaccount.guarantor.domain.GuarantorType;
public class GuarantorCommand {
    private final Long clientRelationshipTypeId;
    private final Integer guarantorTypeId;
    private final Long entityId;
    private final String firstname;
    private final String lastname;
    private final String addressLine1;
    private final String addressLine2;
    private final String city;
    private final String state;
    private final String zip;
    private final String country;
    private final String mobileNumber;
    private final String housePhoneNumber;
    private final String comment;
    private final LocalDate dob;
    private final Long savingsId;
    private final BigDecimal amount;
    public GuarantorCommand(final Long clientRelationshipTypeId, final Integer guarantorTypeId, final Long entityId, final String firstname,
            final String lastname, final String addressLine1, final String addressLine2, final String city, final String state,
            final String zip, final String country, final String mobileNumber, final String housePhoneNumber, final String comment,
            final LocalDate dob, final Long savingsId, final BigDecimal amount) {
        this.clientRelationshipTypeId = clientRelationshipTypeId;
        this.guarantorTypeId = guarantorTypeId;
        this.entityId = entityId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.country = country;
        this.mobileNumber = mobileNumber;
        this.housePhoneNumber = housePhoneNumber;
        this.comment = comment;
        this.dob = dob;
        this.savingsId = savingsId;
        this.amount = amount;
    }
    public boolean isExternalGuarantor() {
        return GuarantorType.EXTERNAL.getValue().equals(this.guarantorTypeId);
    }
    public LocalDate getDobAsDate() {
        return this.dob;
    }
    public void validateForCreate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = getDataValidator(dataValidationErrors);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.CLIENT_RELATIONSHIP_TYPE_ID.getValue())
                .value(this.clientRelationshipTypeId).ignoreIfNull().integerGreaterThanZero();
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.GUARANTOR_TYPE_ID.getValue()).value(this.guarantorTypeId).notNull()
                .inMinMaxRange(GuarantorType.getMinValue(), GuarantorType.getMaxValue());
        if (!isExternalGuarantor()) {
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.ENTITY_ID.getValue()).value(this.entityId).notNull()
                    .integerGreaterThanZero();
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.SAVINGS_ID.getValue()).value(this.savingsId).longGreaterThanZero();
            if (this.savingsId != null) {
                baseDataValidator.reset().parameter(GuarantorJSONinputParams.AMOUNT.getValue()).value(this.amount).notNull()
                        .positiveAmount();
            }
        } else {
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.FIRSTNAME.getValue()).value(this.firstname).notBlank()
                    .notExceedingLengthOf(50);
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.LASTNAME.getValue()).value(this.lastname).notBlank()
                    .notExceedingLengthOf(50);
            validateNonMandatoryFieldsForMaxLength(baseDataValidator);
        }
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
    public void validateForUpdate() {
        final List<ApiParameterError> dataValidationErrors = new ArrayList<>();
        final DataValidatorBuilder baseDataValidator = getDataValidator(dataValidationErrors);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.CLIENT_RELATIONSHIP_TYPE_ID.getValue())
                .value(this.clientRelationshipTypeId).ignoreIfNull().integerGreaterThanZero();
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.GUARANTOR_TYPE_ID.getValue()).value(this.guarantorTypeId)
                .ignoreIfNull().inMinMaxRange(GuarantorType.getMinValue(), GuarantorType.getMaxValue());
        if (!isExternalGuarantor()) {
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.ENTITY_ID.getValue()).value(this.entityId).ignoreIfNull()
                    .integerGreaterThanZero();
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.SAVINGS_ID.getValue()).value(this.savingsId).longGreaterThanZero();
            if (this.savingsId != null) {
                baseDataValidator.reset().parameter(GuarantorJSONinputParams.AMOUNT.getValue()).value(this.amount).notNull()
                        .positiveAmount();
            }
        } else {
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.FIRSTNAME.getValue()).value(this.firstname).ignoreIfNull()
                    .notExceedingLengthOf(50);
            baseDataValidator.reset().parameter(GuarantorJSONinputParams.LASTNAME.getValue()).value(this.lastname).ignoreIfNull()
                    .notExceedingLengthOf(50);
            validateNonMandatoryFieldsForMaxLength(baseDataValidator);
        }
        baseDataValidator.reset().anyOfNotNull(this.entityId, this.addressLine1, this.addressLine2, this.city, this.comment, this.country,
                this.firstname, this.housePhoneNumber, this.lastname, this.mobileNumber, this.state, this.zip);
        if (!dataValidationErrors.isEmpty()) {
            throw new PlatformApiDataValidationException("validation.msg.validation.errors.exist", "Validation errors exist.",
                    dataValidationErrors);
        }
    }
    private void validateNonMandatoryFieldsForMaxLength(final DataValidatorBuilder baseDataValidator) {
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.ADDRESS_LINE_1.getValue()).value(this.addressLine1).ignoreIfNull()
                .notExceedingLengthOf(500);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.ADDRESS_LINE_2.getValue()).value(this.addressLine2).ignoreIfNull()
                .notExceedingLengthOf(500);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.CITY.getValue()).value(this.city).ignoreIfNull()
                .notExceedingLengthOf(50);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.STATE.getValue()).value(this.state).ignoreIfNull()
                .notExceedingLengthOf(50);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.ZIP.getValue()).value(this.zip).ignoreIfNull()
                .notExceedingLengthOf(50);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.COUNTRY.getValue()).value(this.country).ignoreIfNull()
                .notExceedingLengthOf(50);
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.MOBILE_NUMBER.getValue()).value(this.mobileNumber).ignoreIfNull()
                .notExceedingLengthOf(20).validatePhoneNumber();
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.PHONE_NUMBER.getValue()).value(this.housePhoneNumber).ignoreIfNull()
                .notExceedingLengthOf(20).validatePhoneNumber();
        baseDataValidator.reset().parameter(GuarantorJSONinputParams.COMMENT.getValue()).value(this.comment).ignoreIfNull()
                .notExceedingLengthOf(500);
    }
    private DataValidatorBuilder getDataValidator(final List<ApiParameterError> dataValidationErrors) {
        final DataValidatorBuilder baseDataValidator = new DataValidatorBuilder(dataValidationErrors).resource("Guarantor");
        return baseDataValidator;
    }
    public Long getClientRelationshipTypeId() {
        return this.clientRelationshipTypeId;
    }
    public Long getEntityId() {
        return this.entityId;
    }
    public Integer getGuarantorTypeId() {
        return this.guarantorTypeId;
    }
    public Long getSavingsId() {
        return this.savingsId;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
}
