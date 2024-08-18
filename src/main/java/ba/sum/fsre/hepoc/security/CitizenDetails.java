package ba.sum.fsre.hepoc.security;

import ba.sum.fsre.hepoc.entity.Citizen;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CitizenDetails implements UserDetails {

    private final Citizen citizen;

    public CitizenDetails(Citizen citizen) {
        this.citizen = citizen;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> "ROLE_CITIZEN");
    }

    @Override
    public String getPassword() {
        return citizen.getPassword();
    }

    @Override
    public String getUsername() {
        return citizen.getJmbg();
    }
}
