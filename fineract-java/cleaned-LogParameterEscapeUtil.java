
package org.apache.fineract.infrastructure.security.utils;
public final class LogParameterEscapeUtil {
    private LogParameterEscapeUtil() {}
    public static String escapeLogParameter(String logParameter) {
        return logParameter.replaceAll("[\n\r\t]", "_");
    }
    public static String escapeLogMDCParameter(String logParameter) {
        return logParameter.replaceAll("[\r\n]", "");
    }
}
