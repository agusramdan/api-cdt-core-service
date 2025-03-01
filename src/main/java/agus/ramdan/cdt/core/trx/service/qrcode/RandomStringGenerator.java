package agus.ramdan.cdt.core.trx.service.qrcode;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomStringGenerator {
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final int STRING_LENGTH = 20;
    private static final Random random = new Random();

    public String generateRandomString() {
        StringBuilder result = new StringBuilder(STRING_LENGTH);
        for (int i = 0; i < STRING_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            result.append(CHARACTERS.charAt(index));
        }
        return result.toString();
    }
}
