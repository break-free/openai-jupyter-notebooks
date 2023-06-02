
package org.apache.fineract.integrationtests.support.instancemode;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.fineract.integrationtests.common.Utils;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
public class InstanceModeSupportExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final Namespace INSTANCE_MODE_NAMESPACE = Namespace.create(InstanceModeSupportExtension.class);
    private static final String AUTH_KEY = "AUTH_KEY";
    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        context.getTestMethod().ifPresent(m -> {
            ConfigureInstanceMode annotation = m.getAnnotation(ConfigureInstanceMode.class);
            if (annotation != null) {
                boolean readEnabled = annotation.readEnabled();
                boolean writeEnabled = annotation.writeEnabled();
                boolean batchWorkerEnabled = annotation.batchWorkerEnabled();
                boolean batchManagerEnabled = annotation.batchWorkerEnabled();
                changeInstanceMode(context, readEnabled, writeEnabled, batchWorkerEnabled, batchManagerEnabled);
            }
        });
    }
    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        context.getTestMethod().ifPresent(m -> {
            ConfigureInstanceMode annotation = m.getAnnotation(ConfigureInstanceMode.class);
            if (annotation != null) {
                changeInstanceMode(context, true, true, true, true);
            }
        });
    }
    private void changeInstanceMode(ExtensionContext extensionContext, boolean readEnabled, boolean writeEnabled,
            boolean batchWorkerEnabled, boolean batchManagerEnabled) {
        Store store = extensionContext.getStore(INSTANCE_MODE_NAMESPACE);
        String authKey = store.getOrComputeIfAbsent(AUTH_KEY, k -> Utils.loginIntoServerAndGetBase64EncodedAuthenticationKey(),
                String.class);
        RequestSpecification requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        requestSpec.header("Authorization", "Basic " + authKey);
        InstanceModeHelper.changeMode(requestSpec, responseSpec, readEnabled, writeEnabled, batchWorkerEnabled, batchManagerEnabled);
    }
}
