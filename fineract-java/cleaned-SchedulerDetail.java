
package org.apache.fineract.infrastructure.jobs.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.apache.fineract.infrastructure.core.domain.AbstractPersistableCustom;
@Entity
@Table(name = "scheduler_detail")
public class SchedulerDetail extends AbstractPersistableCustom {
    @Column(name = "execute_misfired_jobs")
    private boolean executeInstructionForMisfiredJobs;
    @Column(name = "is_suspended")
    private boolean suspended;
    @Column(name = "reset_scheduler_on_bootup")
    private boolean resetSchedulerOnBootup;
    protected SchedulerDetail() {
    }
    public boolean isExecuteInstructionForMisfiredJobs() {
        return this.executeInstructionForMisfiredJobs;
    }
    public void updateExecuteInstructionForMisfiredJobs(final boolean executeInstructionForMisfiredJobs) {
        this.executeInstructionForMisfiredJobs = executeInstructionForMisfiredJobs;
    }
    public boolean isSuspended() {
        return this.suspended;
    }
    public void updateSuspendedState(final boolean suspended) {
        this.suspended = suspended;
    }
    public boolean isResetSchedulerOnBootup() {
        return this.resetSchedulerOnBootup;
    }
    public void updateResetSchedulerOnBootup(final boolean resetSchedulerOnBootup) {
        this.resetSchedulerOnBootup = resetSchedulerOnBootup;
    }
}
