
package org.apache.fineract.spm.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_survey_components")
public class Component extends AbstractPersistableCustom {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;
    @Column(name = "a_key", length = 32)
    private String key;
    @Column(name = "a_text", length = 255)
    private String text;
    @Column(name = "description", length = 4096)
    private String description;
    @Column(name = "sequence_no", precision = 4)
    private Integer sequenceNo;
    public Component() {
    }
    public Survey getSurvey() {
        return survey;
    }
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getText() {
        return text;
    }
    public void setText(String title) {
        this.text = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getSequenceNo() {
        return sequenceNo;
    }
    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }
}
