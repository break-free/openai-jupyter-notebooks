
package org.apache.fineract.organisation.staff.exception;
import org.apache.fineract.infrastructure.core.exception.AbstractPlatformResourceNotFoundException;
public class StaffRoleException extends AbstractPlatformResourceNotFoundException {
    public enum StaffRole {
        LOAN_OFFICER, BRANCH_MANAGER, SAVINGS_OFFICER;
        @Override
        public String toString() {
            return name().toString().replaceAll("-", " ").toLowerCase();
        }
    }
    public StaffRoleException(final Long id, final StaffRole role) {
        super("error.msg.staff.id.invalid.role", "Staff with identifier " + id + " is not a " + role.toString(), id);
    }
}
