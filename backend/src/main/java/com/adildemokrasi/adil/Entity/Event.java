package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event extends BaseCreation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "event_id")
    private int id;

    @NotNull
    private String title;

    @Column(length = 2000)
    private String content;

    @NotNull
    @ManyToOne
    @JoinColumn
    private User creator;

    @ManyToMany(mappedBy = "events")
    private List<User> users;

    @ManyToOne
    @JoinColumn
    private Address address;

    @OneToMany
    @JoinTable(name="event_comment", joinColumns = @JoinColumn(name="event_id"),
            inverseJoinColumns = @JoinColumn(name="comment_id"))
    private List<Comment> commentList = new ArrayList<>();

    @ManyToOne
    private NGO ngo;

    private String date;

    private boolean deleted;

    public Event() {
        this.deleted = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public NGO getNgo() {
        return ngo;
    }

    public void setNgo(NGO ngo) {
        this.ngo = ngo;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user){
        if(this.users == null){
            List<User> userList = new ArrayList<>();
            userList.add(user);
            this.users = userList;
        }
        else{
            this.users.add(user);
        }
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public void addComment(Comment comment){
        this.commentList.add(comment);
    }
}
