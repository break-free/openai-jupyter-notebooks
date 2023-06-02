
package org.apache.fineract.infrastructure.survey.data;
public class PovertyLineData {
    final Long resourceId;
    final Long scoreFrom;
    final Long scoreTo;
    final Double povertyLine;
    public PovertyLineData(final Long resourceId, final Long scoreFrom, final Long scoreTo, final Double povertyLine) {
        this.resourceId = resourceId;
        this.scoreTo = scoreTo;
        this.scoreFrom = scoreFrom;
        this.povertyLine = povertyLine;
    }
}
