package ba.sum.fsre.hepoc.service;

import ba.sum.fsre.hepoc.entity.Role;
import ba.sum.fsre.hepoc.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
