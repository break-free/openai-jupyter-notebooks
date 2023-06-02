
package org.apache.fineract.integrationtests.common.system;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.Date;
import java.util.List;
import org.apache.fineract.integrationtests.common.ClientHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class DatatableIntegrationTest {
    private RequestSpecification requestSpec;
    private ResponseSpecification responseSpec;
    private DatatableHelper datatableHelper;
    private static final String CLIENT_APP_TABLE_NAME = "m_client";
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        this.datatableHelper = new DatatableHelper(this.requestSpec, this.responseSpec);
    }
    @Test
    public void validateCreateReadDeleteDatatable() {
        String datatableName = this.datatableHelper.createDatatable(CLIENT_APP_TABLE_NAME, false);
        DatatableHelper.verifyDatatableCreatedOnServer(this.requestSpec, this.responseSpec, datatableName);
        final Integer clientID = ClientHelper.createClientAsPerson(requestSpec, responseSpec);
        final boolean genericResultSet = true;
        Integer datatableResourceID = this.datatableHelper.createDatatableEntry(CLIENT_APP_TABLE_NAME, datatableName, clientID,
                genericResultSet, "yyyy-MM-dd", "resourceId");
        assertNotNull(datatableResourceID, "ERROR IN CREATING THE ENTITY DATATABLE RECORD");
        final List<String> items = this.datatableHelper.readDatatableEntry(datatableName, clientID, genericResultSet, null, "data");
        assertEquals(1, items.size());
        final Date valueDate = this.datatableHelper.readDatatableEntry(datatableName, clientID, !genericResultSet, 0, "Date of Approval");
        assertNotNull(valueDate, "ERROR IN GETTING THE DATE VALUE FROM DATATABLE RECORD");
        assertInstanceOf(Date.class, valueDate);
        Integer appTableId = this.datatableHelper.deleteDatatableEntries(datatableName, clientID, "clientId");
        assertEquals(clientID, appTableId, "ERROR IN DELETING THE DATATABLE ENTRIES");
        String deletedDataTableName = this.datatableHelper.deleteDatatable(datatableName);
        assertEquals(datatableName, deletedDataTableName, "ERROR IN DELETING THE DATATABLE");
    }
    @Test
    public void validateReadDatatableMultirow() {
        String datatableName = this.datatableHelper.createDatatable(CLIENT_APP_TABLE_NAME, true);
        DatatableHelper.verifyDatatableCreatedOnServer(this.requestSpec, this.responseSpec, datatableName);
        final Integer clientIdA = ClientHelper.createClientAsPerson(requestSpec, responseSpec);
        final Integer clientIdB = ClientHelper.createClientAsPerson(requestSpec, responseSpec);
        final boolean genericResultSet = true;
        final Integer datatableResourceIdA = this.datatableHelper.createDatatableEntry(CLIENT_APP_TABLE_NAME, datatableName, clientIdA,
                genericResultSet, "yyyy-MM-dd", "resourceId");
        assertNotNull(datatableResourceIdA, "ERROR IN CREATING THE ENTITY DATATABLE RECORD");
        final Integer datatableResourceIdB = this.datatableHelper.createDatatableEntry(CLIENT_APP_TABLE_NAME, datatableName, clientIdB,
                genericResultSet, "yyyy-MM-dd", "resourceId");
        assertNotNull(datatableResourceIdB, "ERROR IN CREATING THE ENTITY DATATABLE RECORD");
        List<String> items;
        items = this.datatableHelper.readDatatableEntry(datatableName, clientIdA, genericResultSet, datatableResourceIdA, "data");
        assertEquals(1, items.size());
        items = this.datatableHelper.readDatatableEntry(datatableName, clientIdB, genericResultSet, datatableResourceIdB, "data");
        assertEquals(1, items.size());
        items = this.datatableHelper.readDatatableEntry(datatableName, clientIdA, genericResultSet, datatableResourceIdB, "data");
        assertEquals(0, items.size());
        Integer appTableIdA = this.datatableHelper.deleteDatatableEntries(datatableName, clientIdA, "clientId");
        assertEquals(clientIdA, appTableIdA, "ERROR IN DELETING THE DATATABLE ENTRIES");
        Integer appTableIdB = this.datatableHelper.deleteDatatableEntries(datatableName, clientIdB, "clientId");
        assertEquals(clientIdB, appTableIdB, "ERROR IN DELETING THE DATATABLE ENTRIES");
        String deletedDataTableName = this.datatableHelper.deleteDatatable(datatableName);
        assertEquals(datatableName, deletedDataTableName, "ERROR IN DELETING THE DATATABLE");
    }
}
