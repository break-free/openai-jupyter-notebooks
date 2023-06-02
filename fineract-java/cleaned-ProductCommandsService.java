
package org.apache.fineract.portfolio.products.service;
public interface ProductCommandsService {
    Object handleCommand(Long productId, String command, String jsonBody);
}
