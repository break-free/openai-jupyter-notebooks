
package org.apache.fineract.infrastructure.gcm.domain;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public final class MulticastResult implements Serializable {
    private final int success;
    private final int failure;
    private final int canonicalIds;
    private final long multicastId;
    private final List<Result> results;
    private final List<Long> retryMulticastIds;
    public static final class Builder {
        private final List<Result> results = new ArrayList<>();
        private final int success;
        private final int failure;
        private final int canonicalIds;
        private final long multicastId;
        private List<Long> retryMulticastIds;
        public Builder(int success, int failure, int canonicalIds, long multicastId) {
            this.success = success;
            this.failure = failure;
            this.canonicalIds = canonicalIds;
            this.multicastId = multicastId;
        }
        public Builder addResult(Result result) {
            results.add(result);
            return this;
        }
        public Builder retryMulticastIds(List<Long> retryMulticastIds) {
            this.retryMulticastIds = retryMulticastIds;
            return this;
        }
        public MulticastResult build() {
            return new MulticastResult(this);
        }
    }
    private MulticastResult(Builder builder) {
        success = builder.success;
        failure = builder.failure;
        canonicalIds = builder.canonicalIds;
        multicastId = builder.multicastId;
        results = Collections.unmodifiableList(builder.results);
        List<Long> tmpList = builder.retryMulticastIds;
        if (tmpList == null) {
            tmpList = Collections.emptyList();
        }
        retryMulticastIds = Collections.unmodifiableList(tmpList);
    }
    public long getMulticastId() {
        return multicastId;
    }
    public int getSuccess() {
        return success;
    }
    public int getTotal() {
        return success + failure;
    }
    public int getFailure() {
        return failure;
    }
    public int getCanonicalIds() {
        return canonicalIds;
    }
    public List<Result> getResults() {
        return results;
    }
    public List<Long> getRetryMulticastIds() {
        return retryMulticastIds;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("MulticastResult(").append("multicast_id=").append(multicastId).append(",")
                .append("total=").append(getTotal()).append(",").append("success=").append(success).append(",").append("failure=")
                .append(failure).append(",").append("canonical_ids=").append(canonicalIds).append(",");
        if (!results.isEmpty()) {
            builder.append("results: " + results);
        }
        return builder.toString();
    }
}
