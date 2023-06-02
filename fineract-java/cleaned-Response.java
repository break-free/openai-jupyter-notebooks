
package org.apache.fineract.spm.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_survey_responses")
public class Response extends AbstractPersistableCustom {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    @Column(name = "a_text", length = 255)
    private String text;
    @Column(name = "a_value", precision = 4)
    private Integer value;
    @Column(name = "sequence_no", precision = 4)
    private Integer sequenceNo;
    public Response() {
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public Integer getSequenceNo() {
        return sequenceNo;
    }
    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
}
