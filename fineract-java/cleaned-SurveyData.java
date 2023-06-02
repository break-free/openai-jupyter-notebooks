
package org.apache.fineract.spm.data;
import java.time.LocalDate;
import java.util.List;
public class SurveyData {
    private Long id;
    private List<ComponentData> componentDatas;
    private List<QuestionData> questionDatas;
    private String key;
    private String name;
    private String description;
    private String countryCode;
    private LocalDate validFrom;
    private LocalDate validTo;
    public SurveyData() {
    }
    public SurveyData(final Long id, final List<ComponentData> componentDatas, final List<QuestionData> questionDatas, final String key,
            final String name, final String description, final String countryCode, final LocalDate validFrom, final LocalDate validTo) {
        this.id = id;
        this.componentDatas = componentDatas;
        this.questionDatas = questionDatas;
        this.key = key;
        this.name = name;
        this.description = description;
        this.countryCode = countryCode;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<ComponentData> getComponentDatas() {
        return componentDatas;
    }
    public void setComponentDatas(List<ComponentData> componentDatas) {
        this.componentDatas = componentDatas;
    }
    public List<QuestionData> getQuestionDatas() {
        return questionDatas;
    }
    public void setQuestionDatas(List<QuestionData> questionDatas) {
        this.questionDatas = questionDatas;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public LocalDate getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }
    public LocalDate getValidTo() {
        return validTo;
    }
    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}
