package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

    private String twitter;

    private String instagram;

    @Column(length = 2000)
    private String aboutMe;

    private String job;

    private boolean active;

    @ManyToMany(mappedBy = "userList")
    private Set<NGO> followedNgoList;

    private boolean deleted;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name="user_role", joinColumns = @JoinColumn(name ="user_id"),
            inverseJoinColumns = @JoinColumn(name ="role_id"))
    private Set<Role> roles;

    @ManyToMany
    @JoinTable(name="user_event", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="event_id"))
    private List<Event> events;

    @OneToMany
    @JoinTable(name="user_announcement", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="announcement_id"))
    private List<Announcement> announcements;

    @OneToMany
    @JoinTable(name="user_comment", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="comment_id"))
    private List<Comment> comments;



    public User() {
        this.deleted = false;
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

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public Set<NGO> getFollowedNgoList() {
        return followedNgoList;
    }

    public void setFollowedNgoList(Set<NGO> followedNgoList) {
        this.followedNgoList = followedNgoList;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        if(this.comments == null){
            List<Comment> comments = new ArrayList<>();
            comments.add(comment);
        }
        else{
            this.comments.add(comment);
        }
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }

    public void addAnnouncement(Announcement announcement){
        if(this.announcements == null){
            List<Announcement> announcements = new ArrayList<>();
            announcements.add(announcement);
            this.announcements = announcements;
        }
        else{
            this.announcements.add(announcement);
        }
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event){
        if(this.events == null){
            List<Event> events = new ArrayList<>();
            events.add(event);
            this.events = events;
        }
        else{
            this.events.add(event);
        }
    }

}
