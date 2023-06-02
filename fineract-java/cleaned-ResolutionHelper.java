
package org.apache.fineract.batch.service;
import com.google.common.base.Splitter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.fineract.batch.domain.BatchRequest;
import org.apache.fineract.batch.domain.BatchResponse;
import org.apache.fineract.infrastructure.core.serialization.FromJsonHelper;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
public class ResolutionHelper {
    public static class BatchRequestNode {
        private BatchRequest request;
        private final List<BatchRequestNode> childRequests = new ArrayList<>();
        public BatchRequestNode() {
        }
        public BatchRequest getRequest() {
            return this.request;
        }
        public void setRequest(BatchRequest request) {
            this.request = request;
        }
        public List<BatchRequestNode> getChildRequests() {
            return this.childRequests;
        }
        public void addChildRequest(final BatchRequestNode batchRequest) {
            this.childRequests.add(batchRequest);
        }
    }
    private final FromJsonHelper fromJsonHelper;
    public List<BatchRequestNode> getDependingRequests(final List<BatchRequest> batchRequests) {
        final List<BatchRequestNode> rootRequests = new ArrayList<>();
        for (BatchRequest batchRequest : batchRequests) {
            if (batchRequest.getReference() == null) {
                final BatchRequestNode node = new BatchRequestNode();
                node.setRequest(batchRequest);
                rootRequests.add(node);
            } else {
                this.addDependingRequest(batchRequest, rootRequests);
            }
        }
        return rootRequests;
    }
    private void addDependingRequest(final BatchRequest batchRequest, final List<BatchRequestNode> parentRequests) {
        for (BatchRequestNode batchRequestNode : parentRequests) {
            if (batchRequestNode.getRequest().getRequestId().equals(batchRequest.getReference())) {
                final BatchRequestNode dependingRequest = new BatchRequestNode();
                dependingRequest.setRequest(batchRequest);
                batchRequestNode.addChildRequest(dependingRequest);
            } else {
                addDependingRequest(batchRequest, batchRequestNode.getChildRequests());
            }
        }
    }
    public BatchRequest resoluteRequest(final BatchRequest request, final BatchResponse parentResponse) {
        final BatchRequest br = request;
        final ReadContext responseCtx = JsonPath.parse(parentResponse.getBody());
        final JsonObject jsonRequestBody = this.fromJsonHelper.parse(request.getBody()).getAsJsonObject();
        JsonObject jsonResultBody = new JsonObject();
        for (Map.Entry<String, JsonElement> element : jsonRequestBody.entrySet()) {
            final String key = element.getKey();
            final JsonElement value = resolveDependentVariables(element, responseCtx);
            jsonResultBody.add(key, value);
        }
        br.setBody(jsonResultBody.toString());
        String relativeUrl = request.getRelativeUrl();
        if (relativeUrl.contains("$.")) {
            String queryParams = "";
            if (relativeUrl.contains("?")) {
                queryParams = relativeUrl.substring(relativeUrl.indexOf("?"));
                relativeUrl = relativeUrl.substring(0, relativeUrl.indexOf("?"));
            }
            final Iterable<String> parameters = Splitter.on('/').split(relativeUrl);
            for (String parameter : parameters) {
                if (parameter.contains("$.")) {
                    final String resParamValue = responseCtx.read(parameter).toString();
                    relativeUrl = relativeUrl.replace(parameter, resParamValue);
                    br.setRelativeUrl(relativeUrl + queryParams);
                }
            }
        }
        return br;
    }
    private JsonElement resolveDependentVariables(final Map.Entry<String, JsonElement> entryElement, final ReadContext responseCtx) {
        JsonElement value = null;
        final JsonElement element = entryElement.getValue();
        if (element.isJsonObject()) {
            final JsonObject jsObject = element.getAsJsonObject();
            value = processJsonObject(jsObject, responseCtx);
        } else if (element.isJsonArray()) {
            final JsonArray jsElementArray = element.getAsJsonArray();
            value = processJsonArray(jsElementArray, responseCtx);
        } else {
            value = resolveDependentVariable(element, responseCtx);
        }
        return value;
    }
    private JsonElement processJsonObject(final JsonObject jsObject, final ReadContext responseCtx) {
        JsonObject valueObj = new JsonObject();
        for (Map.Entry<String, JsonElement> element : jsObject.entrySet()) {
            final String key = element.getKey();
            final JsonElement value = resolveDependentVariable(element.getValue(), responseCtx);
            valueObj.add(key, value);
        }
        return valueObj;
    }
    private JsonArray processJsonArray(final JsonArray elementArray, final ReadContext responseCtx) {
        JsonArray valueArr = new JsonArray();
        for (JsonElement element : elementArray) {
            if (element.isJsonObject()) {
                final JsonObject jsObject = element.getAsJsonObject();
                valueArr.add(processJsonObject(jsObject, responseCtx));
            }
        }
        return valueArr;
    }
    private JsonElement resolveDependentVariable(final JsonElement element, final ReadContext responseCtx) {
        JsonElement value = element;
        String paramVal = element.getAsString();
        if (paramVal.contains("$.")) {
            final String resParamValue = responseCtx.read(paramVal).toString();
            value = this.fromJsonHelper.parse(resParamValue);
        }
        return value;
    }
}
