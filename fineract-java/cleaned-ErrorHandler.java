
package org.apache.fineract.batch.exception;
import com.google.gson.Gson;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
import org.apache.fineract.infrastructure.core.exception.PlatformApiDataValidationException;
import org.apache.fineract.infrastructure.core.exception.PlatformDataIntegrityException;
import org.apache.fineract.infrastructure.core.exception.PlatformInternalServerException;
import org.apache.fineract.infrastructure.core.exception.UnsupportedParameterException;
import org.apache.fineract.infrastructure.core.exceptionmapper.PlatformApiDataValidationExceptionMapper;
import org.apache.fineract.infrastructure.core.exceptionmapper.PlatformDataIntegrityExceptionMapper;
import org.apache.fineract.infrastructure.core.exceptionmapper.PlatformDomainRuleExceptionMapper;
import org.apache.fineract.infrastructure.core.exceptionmapper.PlatformInternalServerExceptionMapper;
import org.apache.fineract.infrastructure.core.exceptionmapper.PlatformResourceNotFoundExceptionMapper;
import org.apache.fineract.infrastructure.core.exceptionmapper.UnsupportedParameterExceptionMapper;
import org.apache.fineract.infrastructure.core.serialization.GoogleGsonSerializerHelper;
import org.apache.fineract.portfolio.loanaccount.exception.MultiDisbursementDataRequiredException;
import org.apache.fineract.portfolio.loanproduct.exception.LinkedAccountRequiredException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.transaction.TransactionException;
public class ErrorHandler extends RuntimeException {
    private static Gson jsonHelper = GoogleGsonSerializerHelper.createGsonBuilder(true).create();
    ErrorHandler() {
    }
    public static ErrorInfo handler(final RuntimeException exception) {
        if (exception instanceof AbstractPlatformDomainRuleException) {
            PlatformDomainRuleExceptionMapper mapper = new PlatformDomainRuleExceptionMapper();
            final String errorBody = jsonHelper.toJson(mapper.toResponse((AbstractPlatformDomainRuleException) exception).getEntity());
            return new ErrorInfo(500, 9999, errorBody);
        } else if (exception instanceof AbstractPlatformResourceNotFoundException) {
            final PlatformResourceNotFoundExceptionMapper mapper = new PlatformResourceNotFoundExceptionMapper();
            final String errorBody = jsonHelper
                    .toJson(mapper.toResponse((AbstractPlatformResourceNotFoundException) exception).getEntity());
            return new ErrorInfo(404, 1001, errorBody);
        } else if (exception instanceof UnsupportedParameterException) {
            final UnsupportedParameterExceptionMapper mapper = new UnsupportedParameterExceptionMapper();
            final String errorBody = jsonHelper.toJson(mapper.toResponse((UnsupportedParameterException) exception).getEntity());
            return new ErrorInfo(400, 2001, errorBody);
        } else if (exception instanceof PlatformApiDataValidationException) {
            final PlatformApiDataValidationExceptionMapper mapper = new PlatformApiDataValidationExceptionMapper();
            final String errorBody = jsonHelper.toJson(mapper.toResponse((PlatformApiDataValidationException) exception).getEntity());
            return new ErrorInfo(400, 2002, errorBody);
        } else if (exception instanceof PlatformDataIntegrityException) {
            final PlatformDataIntegrityExceptionMapper mapper = new PlatformDataIntegrityExceptionMapper();
            final String errorBody = jsonHelper.toJson(mapper.toResponse((PlatformDataIntegrityException) exception).getEntity());
            return new ErrorInfo(403, 3001, errorBody);
        } else if (exception instanceof LinkedAccountRequiredException) {
            final PlatformDomainRuleExceptionMapper mapper = new PlatformDomainRuleExceptionMapper();
            final String errorBody = jsonHelper.toJson(mapper.toResponse((LinkedAccountRequiredException) exception).getEntity());
            return new ErrorInfo(403, 3002, errorBody);
        } else if (exception instanceof MultiDisbursementDataRequiredException) {
            final PlatformDomainRuleExceptionMapper mapper = new PlatformDomainRuleExceptionMapper();
            final String errorBody = jsonHelper.toJson(mapper.toResponse((MultiDisbursementDataRequiredException) exception).getEntity());
            return new ErrorInfo(403, 3003, errorBody);
        } else if (exception instanceof TransactionException) {
            return new ErrorInfo(400, 4001, "{\"Exception\": " + exception.getMessage() + "}");
        } else if (exception instanceof PlatformInternalServerException) {
            final PlatformInternalServerExceptionMapper mapper = new PlatformInternalServerExceptionMapper();
            final String errorBody = jsonHelper.toJson(mapper.toResponse((PlatformInternalServerException) exception).getEntity());
            return new ErrorInfo(500, 5001, errorBody);
        } else if (exception instanceof NonTransientDataAccessException) {
            return new ErrorInfo(400, 4001, "{\"Exception\": " + exception.getMessage() + "}");
        }
        return new ErrorInfo(500, 9999, "{\"Exception\": " + exception.toString() + "}");
    }
}
