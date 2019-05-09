package com.adildemokrasi.adil.Security;

import com.adildemokrasi.adil.Entity.Role;
import com.adildemokrasi.adil.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import sun.java2d.pipe.SpanShapeRenderer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails{

    private final User user;
    Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(User user){
        this.user = user;

        List<GrantedAuthority> auths = new ArrayList<>();
        for(Role role : user.getRoles()){
            auths.add(new SimpleGrantedAuthority(role.getRole().toUpperCase()));
        }
        this.authorities = auths;
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getUsername(){
        return user.getUsername();
    }

    @Override
    public String getPassword(){
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
