package br.rn.sesed.sides.core.generator;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomStringGeneratorService {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789#@*";
    private static final int DEFAULT_LENGTH = 6;
    private final Random random = new SecureRandom();

    public String generateRandomString() {
        return generateRandomString(DEFAULT_LENGTH);
    }

    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
