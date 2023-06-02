
package org.apache.fineract.infrastructure.security.service;
import java.security.SecureRandom;
public class RandomOTPGenerator {
    private static final String allowedCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVQXYZ";
    private final int tokenLength;
    private final SecureRandom secureRandom = new SecureRandom();
    public RandomOTPGenerator(int tokenLength) {
        this.tokenLength = tokenLength;
    }
    public String generate() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < tokenLength; i++) {
            builder.append(allowedCharacters.charAt((int) (secureRandom.nextDouble() * allowedCharacters.length())));
        }
        return builder.toString();
    }
}
