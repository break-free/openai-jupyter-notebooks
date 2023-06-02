
package org.apache.fineract.spm.domain;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
import org.apache.fineract.portfolio.client.domain.Client;
import org.apache.fineract.useradministration.domain.AppUser;
@Entity
@Table(name = "m_survey_scorecards")
public class Scorecard extends AbstractPersistableCustom {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "response_id")
    private Response response;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUser appUser;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
    @Column(name = "created_on")
    @OrderBy("createdOn DESC")
    private LocalDateTime createdOn;
    @Column(name = "a_value", precision = 4)
    private Integer value;
    public Scorecard() {
    }
    public Survey getSurvey() {
        return survey;
    }
    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public Response getResponse() {
        return response;
    }
    public void setResponse(Response response) {
        this.response = response;
    }
    public AppUser getAppUser() {
        return appUser;
    }
    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public LocalDateTime getCreatedOn() {
        return createdOn;
    }
    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
