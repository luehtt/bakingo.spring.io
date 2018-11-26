package io.bakingo.demo.service;

import io.bakingo.demo.model.Role;
import io.bakingo.demo.model.User;
import io.bakingo.demo.repository.RoleRepository;
import io.bakingo.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(@Qualifier("userRepository") UserRepository userRepository, @Qualifier("roleRepository") RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findById(Integer id) {
        User user = userRepository.findById(id).get();
        user.setRoles(null);
        user.setPassword("");
        user.setConfirmPassword("");
        return user;
    }

    public void save(User item) {
        item.setPassword(bCryptPasswordEncoder.encode(item.getPassword()));
        item.setEnabled(true);
        Role role = roleRepository.findByName("USER");
        item.setRoles(new HashSet<>(Collections.singletonList(role)));
        userRepository.save(item);
    }

    public List<User> findCustomer(Boolean enabled) {
        List<User> items = userRepository.findAll().stream()
                .filter(x -> !PrincipalService.hasRole(x, "ADMIN"))
                .filter(x -> x.getEnabled() == enabled)
                .collect(Collectors.toList());
        for (User i : items) {
            i.setPassword("");
            i.setConfirmPassword("");
            i.setRoles(null);
        }
        return items;
    }

    public User update(Integer id, Boolean enabled) {
        User user = userRepository.findById(id).get();
        user.setEnabled(enabled);
        userRepository.save(user);
        return user;
    }

    public User updatePassword(User user) {
        User get = userRepository.findById(user.getId()).get();
        get.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(get);
        return get;
    }

    public User updateInfo(User user) {
        User get = userRepository.findById(user.getId()).get();
        get.setName(user.getName());
        get.setPhone(user.getPhone());
        get.setAddress(user.getAddress());
        userRepository.save(get);
        return get;
    }
}
