
package org.apache.fineract.interoperation.data;
import java.io.Serializable;
public class IdDocument implements Serializable {
    String idType;
    String idNumber;
    String issuerCountry;
    String otherIdDescription;
    public IdDocument(String idType, String idNumber, String issuerCountry, String otherIdDescription) {
        this.idType = idType;
        this.idNumber = idNumber;
        this.issuerCountry = issuerCountry;
        this.otherIdDescription = otherIdDescription;
    }
    public String getIdType() {
        return idType;
    }
    public void setIdType(String idType) {
        this.idType = idType;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public String getIssuerCountry() {
        return issuerCountry;
    }
    public void setIssuerCountry(String issuerCountry) {
        this.issuerCountry = issuerCountry;
    }
    public String getOtherIdDescription() {
        return otherIdDescription;
    }
    public void setOtherIdDescription(String otherIdDescription) {
        this.otherIdDescription = otherIdDescription;
    }
}
