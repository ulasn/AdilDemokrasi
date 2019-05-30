package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Comment extends BaseCreation{

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Integer id;

    @NotNull
    @Column(length = 4000)
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="announcement_id")
    private Announcement announcement;

    @ManyToOne(cascade = CascadeType.ALL)
    private Event event;


    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    @NotNull
    private String date;


    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Announcement getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(Announcement announcement) {
        this.announcement = announcement;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
