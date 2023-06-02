
package org.apache.fineract.portfolio.loanaccount.command;
public class UndoStateTransitionCommand {
    private final Long loanId;
    private final String note;
    public UndoStateTransitionCommand(final Long loanId, final String note) {
        this.loanId = loanId;
        this.note = note;
    }
    public Long getLoanId() {
        return this.loanId;
    }
    public String getNote() {
        return this.note;
    }
}
