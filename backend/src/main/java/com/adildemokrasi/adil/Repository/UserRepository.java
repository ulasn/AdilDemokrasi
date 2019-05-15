package com.adildemokrasi.adil.Repository;

import com.adildemokrasi.adil.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findByNameIgnoreCaseStartingWithOrSurnameIgnoreCaseStartingWithOrUsernameIgnoreCaseStartingWith(String name, String surname, String username);

}
