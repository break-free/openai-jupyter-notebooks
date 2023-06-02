
package org.apache.fineract.spm.domain;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "m_survey_questions")
public class Question extends AbstractPersistableCustom {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("sequenceNo")
    private List<Response> responses;
    @Column(name = "component_key", length = 32)
    private String componentKey;
    @Column(name = "a_key", length = 32)
    private String key;
    @Column(name = "a_text", length = 255)
    private String text;
    @Column(name = "description", length = 4096)
    private String description;
    @Column(name = "sequence_no", precision = 4)
    private Integer sequenceNo;
    public Question() {
    }
    public Survey getSurvey() {
        return survey;
    }
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    public List<Response> getResponses() {
        return responses;
    }
    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }
    public String getComponentKey() {
        return componentKey;
    }
    public void setComponentKey(String componentKey) {
        this.componentKey = componentKey;
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
    public void setText(String text) {
        this.text = text;
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
