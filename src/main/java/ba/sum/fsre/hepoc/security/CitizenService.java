package ba.sum.fsre.hepoc.security;

import ba.sum.fsre.hepoc.repository.CitizenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;


    public CitizenService(CitizenRepository citizenRepository, PasswordEncoder passwordEncoder) {
        this.citizenRepository = citizenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerCitizen() {

    }
}
