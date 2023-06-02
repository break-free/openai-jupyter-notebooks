
package org.apache.fineract.infrastructure.sqlbuilder;
import org.apache.commons.lang3.StringUtils;
final class SqlBuilderUtil {
    private SqlBuilderUtil() {}
    static Object transform(String argument) {
        if (!StringUtils.isBlank(argument)) {
            if ("NULL".equals(argument)) {
                return null;
            } else if ("EMPTY".equals(argument)) {
                return "";
            } else if (isInt(argument)) {
                return Integer.parseInt(argument);
            } else if (isDouble(argument)) {
                return Double.parseDouble(argument);
            } else {
                return argument;
            }
        }
        return null;
    }
    static boolean isInt(String str) {
        return str != null && str.matches("\\d+");
    }
    static boolean isDouble(String str) {
        return str != null && str.matches("\\d+\\.\\d+");
    }
}
