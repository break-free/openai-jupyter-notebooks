
package org.apache.fineract.batch.exception;
public final class ErrorInfo {
    private Integer statusCode;
    private Integer errorCode;
    private String message;
    public ErrorInfo(final Integer statusCode, final Integer errorCode, final String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
    ErrorInfo() {
    }
    public Integer getStatusCode() {
        return this.statusCode;
    }
    public void setStatusCode(final Integer statusCode) {
        this.statusCode = statusCode;
    }
    public Integer getErrorCode() {
        return this.errorCode;
    }
    public void setErrorCode(final Integer errorCode) {
        this.errorCode = errorCode;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
}
