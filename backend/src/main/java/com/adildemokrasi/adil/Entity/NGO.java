package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class NGO extends BaseCreation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ngo_id")
    private Long id;

    @NotNull
    private String name;

    private String description;

    @OneToMany
    @JoinTable(name="NGO_event", joinColumns = @JoinColumn(name ="ngo_id"),
            inverseJoinColumns = @JoinColumn(name ="event_id"))
    private Set<Event> events = new HashSet<>();

    @OneToMany
    @JoinTable(name="NGO_announcement", joinColumns = @JoinColumn(name ="ngo_id"),
            inverseJoinColumns = @JoinColumn(name ="announcement_id"))
    private Set<Announcement> announcements = new HashSet<>();

    @ManyToMany
    @JoinTable(name="NGO_user", joinColumns = @JoinColumn(name ="ngo_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id"))
    private Set<User> userList = new HashSet<>();

    @ManyToMany
    @JoinTable(name="NGO_admin", joinColumns = @JoinColumn(name ="ngo_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id"))
    private Set<User> adminList = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    public Set<User> getAdminList() {
        return adminList;
    }

    public void setAdminList(Set<User> adminList) {
        this.adminList = adminList;
    }

    public void addAdmin(User user){
        this.adminList.add(user);
    }

    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public Set<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(Set<Announcement> announcements) {
        this.announcements = announcements;
    }
}
