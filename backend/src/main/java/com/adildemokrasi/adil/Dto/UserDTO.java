package com.adildemokrasi.adil.Dto;

import com.adildemokrasi.adil.Entity.User;
import org.modelmapper.ModelMapper;

public class UserDTO{
    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;

    public UserDTO() {
    }

    public UserDTO(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = this;
        userDTO = modelMapper.map(user, UserDTO.class);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
