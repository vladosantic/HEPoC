package ba.sum.fsre.hepoc.security;

import ba.sum.fsre.hepoc.entity.Citizen;
import ba.sum.fsre.hepoc.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CitizenDetails implements UserDetails {

    private final Citizen citizen;

    public CitizenDetails(Citizen citizen) {
        this.citizen = citizen;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return mapRolesToAuthorities(citizen.getRoles());
    }

    private Collection< ? extends GrantedAuthority> mapRolesToAuthorities(Collection <Role> roles) {
        Collection < ? extends GrantedAuthority> mapRoles = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
        return mapRoles;
    }

    @Override
    public String getPassword() {
        return citizen.getPassword();
    }

    @Override
    public String getUsername() {
        return citizen.getJmbg();
    }

    public boolean isTwoFactorEnabled() {
        return true;
    }
}
