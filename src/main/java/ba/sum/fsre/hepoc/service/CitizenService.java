package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Citizen;
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

    public void registerCitizen(Citizen citizen) {
        // Encrypt the password using BCrypt
        citizen.setPassword(passwordEncoder.encode(citizen.getPassword()));
        citizenRepository.save(citizen);
    }
}
