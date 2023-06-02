
package org.apache.fineract.integrationtests.bulkimport.importhandler.office;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.fineract.infrastructure.bulkimport.constants.OfficeConstants;
import org.apache.fineract.infrastructure.bulkimport.constants.TemplatePopulateImportConstants;
import org.apache.fineract.integrationtests.common.OfficeHelper;
import org.apache.fineract.integrationtests.common.Utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class OfficeImportHandlerTest {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeImportHandlerTest.class);
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
    public void testOfficeImport() throws IOException, InterruptedException, NoSuchFieldException, ParseException {
        OfficeHelper officeHelper = new OfficeHelper(requestSpec, responseSpec);
        Workbook workbook = officeHelper.getOfficeWorkBook("dd MMMM yyyy");
        Sheet sheet = workbook.getSheet(TemplatePopulateImportConstants.OFFICE_SHEET_NAME);
        Row firstOfficeRow = sheet.getRow(1);
        firstOfficeRow.createCell(OfficeConstants.OFFICE_NAME_COL).setCellValue(Utils.randomNameGenerator("Test_Off_", 6));
        firstOfficeRow.createCell(OfficeConstants.PARENT_OFFICE_NAME_COL)
                .setCellValue(firstOfficeRow.getCell(OfficeConstants.LOOKUP_OFFICE_COL).getStringCellValue());
        firstOfficeRow.createCell(OfficeConstants.PARENT_OFFICE_ID_COL)
                .setCellValue(firstOfficeRow.getCell(OfficeConstants.LOOKUP_OFFICE_ID_COL).getNumericCellValue());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        Date date = simpleDateFormat.parse("14 May 2001");
        firstOfficeRow.createCell(OfficeConstants.OPENED_ON_COL).setCellValue(date);
        String currentdirectory = new File("").getAbsolutePath();
        File directory = new File(currentdirectory + File.separator + "src" + File.separator + "integrationTest" + File.separator
                + "resources" + File.separator + "bulkimport" + File.separator + "importhandler" + File.separator + "office");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        File file = new File(directory + File.separator + "Office.xls");
        OutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        String importDocumentId = officeHelper.importOfficeTemplate(file);
        file.delete();
        Assertions.assertNotNull(importDocumentId);
        Thread.sleep(10000);
        String location = officeHelper.getOutputTemplateLocation(importDocumentId);
        FileInputStream fileInputStream = new FileInputStream(location);
        Workbook outputWorkbook = new HSSFWorkbook(fileInputStream);
        Sheet officeSheet = outputWorkbook.getSheet(TemplatePopulateImportConstants.OFFICE_SHEET_NAME);
        Row row = officeSheet.getRow(1);
        LOG.info("Output location: {}", location);
        LOG.info("Failure reason column: {}", row.getCell(OfficeConstants.STATUS_COL).getStringCellValue());
        Assertions.assertEquals("Imported", row.getCell(OfficeConstants.STATUS_COL).getStringCellValue());
        outputWorkbook.close();
    }
}
