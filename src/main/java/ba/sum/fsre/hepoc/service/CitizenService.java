package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.repository.CitizenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;


    public CitizenService(CitizenRepository citizenRepository, PasswordEncoder passwordEncoder) {
        this.citizenRepository = citizenRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(Citizen citizen) {
        citizenRepository.save(citizen);
    }

    public void register (Citizen citizen) {
        citizen.setPassword(passwordEncoder.encode(citizen.getPassword()));
        citizenRepository.save(citizen);
    }

    public Optional<Citizen> findByJmbg(String jmbg) {
        return citizenRepository.findByJmbg(jmbg);
    }

    public List<Citizen> findAll() {
        return citizenRepository.findAll();
    }

    public Citizen findById(Integer id) {
        return citizenRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        citizenRepository.deleteById(id);
    }
}
