
package org.apache.fineract.portfolio.repaymentwithpostdatedchecks.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class PostDatedCheckNotFoundException extends AbstractPlatformResourceNotFoundException {
    public PostDatedCheckNotFoundException(final Long id) {
        super("error.msg.post.dated.check.id.invalid", "Post Dated Check with identifier " + id + " does not exist", id);
    }
    public PostDatedCheckNotFoundException(final Long id, final Integer installmentNo) {
        super("error.msg.post.dated.check.for.installment.not.found",
                "Post Dated Check for loan id " + id + " and installment number " + installmentNo + " does not exist");
    }
}
