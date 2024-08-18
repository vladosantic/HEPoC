package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.repository.CitizenRepository;
import ba.sum.fsre.hepoc.security.CitizenDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CitizenDetailsService implements UserDetailsService {
    private final CitizenRepository citizenRepository;

    @Autowired
    public CitizenDetailsService(CitizenRepository citizenRepository) {
        this.citizenRepository = citizenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String jmbg) throws UsernameNotFoundException {
        Citizen citizen = citizenRepository.findByJmbg(jmbg)
                .orElseThrow(() -> new UsernameNotFoundException("Citizen not found with JMBG: " + jmbg));
        return new CitizenDetails(citizen);
    }
}
