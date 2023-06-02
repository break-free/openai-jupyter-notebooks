
package org.apache.fineract.portfolio.accounts.service;
public interface AccountsCommandsService {
    Object handleCommand(Long accountId, String command, String jsonBody);
}
