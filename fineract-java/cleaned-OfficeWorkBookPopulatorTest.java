
package org.apache.fineract.integrationtests.bulkimport.populator.office;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.IOException;
import org.apache.fineract.infrastructure.bulkimport.constants.OfficeConstants;
import org.apache.fineract.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import org.apache.fineract.integrationtests.common.OfficeHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class OfficeWorkBookPopulatorTest {
    private ResponseSpecification responseSpec;
    private RequestSpecification requestSpec;
    @BeforeEach
    public void setup() {
        Utils.initializeRESTAssured();
        this.requestSpec = new RequestSpecBuilder().build();
        this.requestSpec.header("Authorization", "Basic " + Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey());
        this.responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }
    @Test
    public void testOfficeWorkbookPopulate() throws IOException {
        OfficeHelper officeHelper = new OfficeHelper(requestSpec, responseSpec);
        Workbook workbook = officeHelper.getOfficeWorkBook("dd MMMM yyyy");
        Sheet sheet = workbook.getSheet(TemplatePopulateImportConstants.OFFICE_SHEET_NAME);
        Row firstRow = sheet.getRow(1);
        Assertions.assertNotNull("No parent offices found", firstRow.getCell(OfficeConstants.LOOKUP_OFFICE_COL).getStringCellValue());
        Assertions.assertEquals(1, firstRow.getCell(OfficeConstants.LOOKUP_OFFICE_ID_COL).getNumericCellValue(), 0.0);
    }
}
