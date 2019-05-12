package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
public class User extends BaseCreation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String surname;

    @Column(nullable = false, unique = true)
    private String username;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private boolean active;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name ="role_id"))
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name="user_event", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="event_id"))
    private List<Event> events;

    public User() {
    }

    public User(@NotNull String name, @NotNull String surname, String username, @NotNull String email, @NotNull String password, Set<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(User user) {
        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.id = user.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        this.events.add(event);
    }
}
