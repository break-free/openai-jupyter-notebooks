
package org.apache.fineract.integrationtests.common;
import static org.junit.jupiter.api.Assertions.assertEquals;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import java.util.HashMap;
@SuppressWarnings({ "rawtypes", "unchecked" })
public final class PaymentTypeHelper {
    private PaymentTypeHelper() {
    }
    private static final String CREATE_PAYMENTTYPE_URL = "/fineract-provider/api/v1/paymenttypes?" + Utils.TENANT_IDENTIFIER;
    private static final String PAYMENTTYPE_URL = "/fineract-provider/api/v1/paymenttypes";
    public static Integer createPaymentType(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final String name, final String description, final Boolean isCashPayment, final Integer position) {
        return Utils.performServerPost(requestSpec, responseSpec, CREATE_PAYMENTTYPE_URL,
                getJsonToCreatePaymentType(name, description, isCashPayment, position), "resourceId");
    }
    public static String getJsonToCreatePaymentType(final String name, final String description, final Boolean isCashPayment,
            final Integer position) {
        HashMap hm = new HashMap();
        hm.put("name", name);
        if (description != null) {
            hm.put("description", description);
        }
        hm.put("isCashPayment", isCashPayment);
        if (position != null) {
            hm.put("position", position);
        }
        return new Gson().toJson(hm);
    }
    public static void verifyPaymentTypeCreatedOnServer(final RequestSpecification requestSpec, final ResponseSpecification responseSpec,
            final Integer generatedPaymentTypeID) {
        final String GET_PAYMENTTYPE_URL = PAYMENTTYPE_URL + "/" + generatedPaymentTypeID + "?" + Utils.TENANT_IDENTIFIER;
        final Integer responsePaymentTypeID = Utils.performServerGet(requestSpec, responseSpec, GET_PAYMENTTYPE_URL, "id");
        assertEquals(generatedPaymentTypeID, responsePaymentTypeID, "ERROR IN CREATING THE PAYMENT TYPE");
    }
    public static PaymentTypeDomain retrieveById(RequestSpecification requestSpec, ResponseSpecification responseSpec,
            final Integer paymentTypeId) {
        final String GET_PAYMENTTYPE_URL = PAYMENTTYPE_URL + "/" + paymentTypeId + "?" + Utils.TENANT_IDENTIFIER;
        Object get = Utils.performServerGet(requestSpec, responseSpec, GET_PAYMENTTYPE_URL, "");
        final String jsonData = new Gson().toJson(get);
        return new Gson().fromJson(jsonData, new TypeToken<PaymentTypeDomain>() {}.getType());
    }
    public static HashMap<String, String> updatePaymentType(final int id, HashMap request, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        final String UPDATE_PAYMENTTYPE_URL = PAYMENTTYPE_URL + "/" + id + "?" + Utils.TENANT_IDENTIFIER;
        HashMap<String, String> hash = Utils.performServerPut(requestSpec, responseSpec, UPDATE_PAYMENTTYPE_URL, new Gson().toJson(request),
                "changes");
        return hash;
    }
    public static Integer deletePaymentType(final int id, final RequestSpecification requestSpec,
            final ResponseSpecification responseSpec) {
        final String DELETE_PAYMENTTYPE_URL = PAYMENTTYPE_URL + "/" + id + "?" + Utils.TENANT_IDENTIFIER;
        return Utils.performServerDelete(requestSpec, responseSpec, DELETE_PAYMENTTYPE_URL, "resourceId");
    }
    public static String randomNameGenerator(final String prefix, final int lenOfRandomSuffix) {
        return Utils.randomStringGenerator(prefix, lenOfRandomSuffix);
    }
}
