package com.adildemokrasi.adil.Service;

import com.adildemokrasi.adil.Entity.Role;
import com.adildemokrasi.adil.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;


    public void addRole(Role role){
        roleRepository.save(role);
    }

    public List<Role> getRoles(){
        return roleRepository.findAll();
    }

    public Role getRole(String roleName){
        Optional<Role> role = roleRepository.getRoleByRoleName(roleName);
        if(role.isPresent()){
            return role.get();
        }
        else{
            addRole(new Role(roleName));
            return getRole(roleName);
        }
    }
}
