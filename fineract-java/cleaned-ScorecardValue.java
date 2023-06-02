
package org.apache.fineract.spm.data;
import java.time.LocalDateTime;
public class ScorecardValue {
    private Long questionId;
    private Long responseId;
    private Integer value;
    private LocalDateTime createdOn;
    public ScorecardValue() {
    }
    private ScorecardValue(final Long questionId, final Long responseId, final Integer value, final LocalDateTime createdOn) {
        this.questionId = questionId;
        this.responseId = responseId;
        this.value = value;
        this.createdOn = createdOn;
    }
    public static ScorecardValue instance(final Long questionId, final Long responseId, final Integer value,
            final LocalDateTime createdOn) {
        return new ScorecardValue(questionId, responseId, value, createdOn);
    }
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public Long getResponseId() {
        return responseId;
    }
    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public LocalDateTime getCreatedOn() {
        return this.createdOn;
    }
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
