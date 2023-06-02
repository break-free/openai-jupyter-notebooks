
package org.apache.fineract.infrastructure.security.utils;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
public class SQLBuilder {
    private static final Pattern ATOZ = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_-]*\\.)?[a-zA-Z_-][a-zA-Z0-9_-]*");
    private final StringBuilder sb = new StringBuilder();
    private final List<Object> args = new ArrayList<>();
    private final ArrayList<String> crts = new ArrayList<String>();
    public void addCriteria(String criteria, Object argument) {
        if (criteria == null || criteria.trim().isEmpty()) {
            throw new IllegalArgumentException("criteria cannot be null");
        }
        String trimmedCriteria = criteria.trim();
        if (trimmedCriteria.isEmpty()) {
            throw new IllegalArgumentException("criteria cannot be null");
        }
        if (trimmedCriteria.contains("?")) {
            throw new IllegalArgumentException(
                    "criteria cannot contain a '?' (that is automatically added at the end): " + trimmedCriteria);
        }
        int columnOperatorIndex = trimmedCriteria.indexOf(' ');
        if (columnOperatorIndex == -1) {
            throw new IllegalArgumentException("criteria missing operator: " + trimmedCriteria);
        }
        String columnName = trimmedCriteria.substring(0, columnOperatorIndex).trim().toLowerCase(Locale.ROOT);
        if (!ATOZ.matcher(columnName).matches()) {
            throw new IllegalArgumentException(
                    "criteria column name must match [a-zA-Z_][a-zA-Z0-9_-]*\\.)?[a-zA-Z_-][a-zA-Z0-9_-]* : " + trimmedCriteria);
        }
        String operator = trimmedCriteria.substring(columnOperatorIndex).trim();
        if (operator.indexOf(' ') > -1) {
            throw new IllegalArgumentException(
                    "criteria cannot contain more than 1 space (between column name and operator): " + trimmedCriteria);
        }
        if (!operator.equals("=") && !operator.equals("<") && !operator.equals(">") && !operator.equals("<=") && !operator.equals(">=")
                && !operator.equals("<>") && !operator.equals("LIKE") && !operator.equals("like") && !operator.toLowerCase().equals("is")) {
            throw new IllegalArgumentException("criteria must end with valid SQL operator for WHERE: " + trimmedCriteria);
        }
        if (sb.length() > 0) {
            sb.append("  AND  ");
        }
        sb.append(trimmedCriteria);
        sb.append(" ?");
        crts.add(trimmedCriteria);
        args.add(argument);
    }
    public void addNonNullCriteria(String criteria, Object argument) {
        if (argument != null) {
            addCriteria(criteria, argument);
        }
    }
    public String getSQLTemplate() {
        if (sb.length() > 0) {
            return " WHERE  " + sb.toString();
        }
        return "";
    }
    public Object[] getArguments() {
        return args.toArray();
    }
    @Override
    public String toString() {
        StringBuilder whereClause = new StringBuilder("SQLBuilder{");
        for (int i = 0; i < args.size(); i++) {
            if (i != 0) {
                whereClause.append("  AND  ");
            } else {
                whereClause.append("WHERE  ");
            }
            Object currentArg = args.get(i);
            whereClause.append(crts.get(i));
            whereClause.append(" ");
            whereClause.append("[");
            if (currentArg instanceof String) {
                whereClause.append("'");
                whereClause.append(currentArg);
                whereClause.append("'");
            } else if (currentArg == null) {
                whereClause.append("null");
            } else {
                whereClause.append(String.valueOf(currentArg));
            }
            whereClause.append("]");
        }
        whereClause.append("}");
        return whereClause.toString();
    }
}
