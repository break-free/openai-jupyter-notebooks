
package org.apache.fineract.infrastructure.dataqueries.data;
import java.io.Serializable;
public class ResultsetColumnValueData implements Serializable {
    private final int id;
    private final String value;
    @SuppressWarnings("unused")
    private final Integer score;
    public ResultsetColumnValueData(final int id, final String value) {
        this.id = id;
        this.value = value;
        this.score = null;
    }
    public ResultsetColumnValueData(final int id, final String value, final int score) {
        this.id = id;
        this.value = value;
        this.score = score;
    }
    public boolean matches(final String match) {
        return match.equalsIgnoreCase(this.value);
    }
    public boolean codeMatches(final Integer match) {
        return match.intValue() == this.id;
    }
}
