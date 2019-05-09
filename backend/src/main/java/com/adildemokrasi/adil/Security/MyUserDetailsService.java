package com.adildemokrasi.adil.Security;

import com.adildemokrasi.adil.Entity.User;
import com.adildemokrasi.adil.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
//          userRepository
//                .findByUsername(username)
//                .map(u -> new org.springframework.security.core.userdetails.User(
//                        u.getUsername(),
//                        u.getPassword(),
//                        u.isActive(),
//                        u.isActive(),
//                        u.isActive(),
//                        u.isActive(),
//                        AuthorityUtils.createAuthorityList(
//                                u.getRoles()
//                                        .stream()
//                                        .map(r -> "ROLE_" + r.getRole().toUpperCase())
//                                        .collect(Collectors.toList())
//                                        .toArray(new String[]{}))))
//                .orElseThrow(() -> new UsernameNotFoundException("No user with "
//                        + "the name " + username + "was found in the database"));
//    }

    @Override
    public UserDetails loadUserByUsername(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()){
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(user.get());
    }

}





