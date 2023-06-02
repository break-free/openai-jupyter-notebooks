
package org.apache.fineract.portfolio.self.pockets.data;
import java.util.Collection;
import org.apache.fineract.portfolio.self.pockets.domain.PocketAccountMapping;
@SuppressWarnings("unused")
public final class PocketAccountMappingData {
    private final Collection<PocketAccountMapping> loanAccounts;
    private final Collection<PocketAccountMapping> savingsAccounts;
    private final Collection<PocketAccountMapping> shareAccounts;
    private PocketAccountMappingData(final Collection<PocketAccountMapping> loanAccounts, Collection<PocketAccountMapping> savingsAccounts,
            final Collection<PocketAccountMapping> shareAccounts) {
        this.loanAccounts = loanAccounts;
        this.savingsAccounts = savingsAccounts;
        this.shareAccounts = shareAccounts;
    }
    public static PocketAccountMappingData instance(final Collection<PocketAccountMapping> loanAccounts,
            final Collection<PocketAccountMapping> savingsAccounts, final Collection<PocketAccountMapping> shareAccounts) {
        return new PocketAccountMappingData(loanAccounts, savingsAccounts, shareAccounts);
    }
}
