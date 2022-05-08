package com.med.userrole.service;

import com.med.userrole.entities.Role;
import com.med.userrole.entities.User;
import com.med.userrole.repositories.RoleRepository;
import com.med.userrole.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public User addNewUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Override
    public Role addNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public Role findRoleByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        User user=findUserByUserName(username);
        Role role=findRoleByRoleName(roleName);
        if(user.getRoles()!=null){
            user.getRoles().add(role);
            role.getUsers().add(user);
        }
        //userRepository.save(user); // c'est pas nécessaire parce que le servie est Transactional
        //en fois il y'a des modifications sur les entities il va mettre commit
    }

    @Override
    public User authenticate(String userName, String password) {
        User user=findUserByUserName(userName);
        if(user == null) throw new RuntimeException("Bad credentials");
        if(user.getPassword().equals(password)){
            return user;
        }
        throw new RuntimeException("Bad credentials");
    }
    // par la suit tu va voir que tous ce travail de authenticate methode tu peux le par Spring security
}
