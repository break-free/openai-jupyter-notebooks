
package org.apache.fineract.interoperation.data;
import java.io.Serializable;
public class SubjectName implements Serializable {
    String firstName;
    String middleName;
    String lastName;
    String displayName;
    public SubjectName(String firstName, String middleName, String lastName, String displayName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.displayName = displayName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getMiddleName() {
        return middleName;
    }
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
