
package org.apache.fineract.portfolio.shareaccounts.data;
import java.io.Serializable;
@SuppressWarnings("unused")
public class ShareAccountStatusEnumData implements Serializable {
    private final Long id;
    private final String code;
    private final String value;
    private final boolean submittedAndPendingApproval;
    private final boolean approved;
    private final boolean rejected;
    private final boolean active;
    private final boolean closed;
    public ShareAccountStatusEnumData(final Long id, final String code, final String value, final boolean submittedAndPendingApproval,
            final boolean approved, final boolean rejected, final boolean active, final boolean closed) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.submittedAndPendingApproval = submittedAndPendingApproval;
        this.approved = approved;
        this.rejected = rejected;
        this.active = active;
        this.closed = closed;
    }
    public Long id() {
        return this.id;
    }
}
