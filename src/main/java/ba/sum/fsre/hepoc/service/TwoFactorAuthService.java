package ba.sum.fsre.hepoc.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TwoFactorAuthService {

    private Map<String, String> verificationCodes = new ConcurrentHashMap<>();

    public String generateVerificationCode(String jmbg) {
        String code = String.format("%06d", new Random().nextInt(999999));
        verificationCodes.put(jmbg, code);
        return code;
    }

    public boolean verifyCode(String jmbg, String code) {
        String storedCode = verificationCodes.get(jmbg);
        if (storedCode != null && storedCode.equals(code)) {
            verificationCodes.remove(jmbg); // Code is used once and then removed
            return true;
        }
        return false;
    }
}
