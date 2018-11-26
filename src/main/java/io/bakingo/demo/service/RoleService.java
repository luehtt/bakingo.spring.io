package io.bakingo.demo.service;

import io.bakingo.demo.model.Role;
import io.bakingo.demo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleService")
public class RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(@Qualifier("roleRepository") RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public void save(Role item) {
        roleRepository.save(item);
    }
}
