package com.adildemokrasi.adil.Repository;

import com.adildemokrasi.adil.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> getRoleByRoleName(String roleName);
}
