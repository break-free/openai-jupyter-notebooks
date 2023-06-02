
package org.apache.fineract.infrastructure.jobs.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformDomainRuleException;
public class JobNodeIdMismatchingException extends AbstractPlatformDomainRuleException {
    public JobNodeIdMismatchingException(final String nodeId, final String nodeIdProvided) {
        super("error.msg.job.cannot.execute.on.node." + nodeIdProvided,
                "The node id provided `" + nodeIdProvided + "`" + "` does not match with the configured nodeId.", new Object[] { nodeId });
    }
}
