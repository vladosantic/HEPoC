package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.entity.Role;
import ba.sum.fsre.hepoc.repository.CitizenRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public CitizenService(CitizenRepository citizenRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.citizenRepository = citizenRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public void save(Citizen citizen) {
        citizenRepository.save(citizen);
    }

    public void register (Citizen citizen) {
        Role citizenRole = roleService.findRoleByName("CITIZEN");
        if (citizenRole != null) {
            citizen.setRoles((Arrays.asList(citizenRole)));
        }

        citizen.setPassword(passwordEncoder.encode(citizen.getPassword()));
        citizenRepository.save(citizen);
    }

    public Optional<Citizen> findByJmbg(String jmbg) {
        return citizenRepository.findByJmbg(jmbg);
    }

    public boolean citizenExistsByJmbgAndPassword(String jmbg, String password) {
        Optional<Citizen> citizen = citizenRepository.findByJmbg(jmbg);
        if (citizen != null) {
            return passwordEncoder.matches(password, citizen.get().getPassword());
        }
        return false;
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

    public boolean citizenExistsByJmbg (String jmbg) {
        return citizenRepository.findByJmbg(jmbg).isPresent();
    }
}
