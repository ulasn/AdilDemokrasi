package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<Event> events = new ArrayList<>();

    @OneToMany
    @JoinTable(name="NGO_announcement", joinColumns = @JoinColumn(name ="ngo_id"),
            inverseJoinColumns = @JoinColumn(name ="announcement_id"))
    private List <Announcement> announcements = new ArrayList<>();

    @ManyToMany
    @JoinTable(name="NGO_user", joinColumns = @JoinColumn(name ="ngo_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id"))
    private Set<User> userList = new HashSet<>();

    @ManyToMany
    @JoinTable(name="NGO_admin", joinColumns = @JoinColumn(name ="ngo_id"),
            inverseJoinColumns = @JoinColumn(name ="user_id"))
    private Set<User> adminList = new HashSet<>();

    private String tag;

    private String twitter;

    private String instagram;

    private String facebook;


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

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        this.events.add(event);
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public void addAnnouncement(Announcement announcement){
        this.announcements.add(announcement);
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }
}
