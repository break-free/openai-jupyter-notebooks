
package org.apache.fineract.infrastructure.security.service;
public interface SqlInjectionPreventerService {
    String encodeSql(String literal);
}
