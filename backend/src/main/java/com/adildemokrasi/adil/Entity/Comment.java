package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Comment extends BaseCreation{

    @Id
    @GeneratedValue
    @Column(name="comment_id")
    private Integer id;

    @NotNull
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="announcement_id")
    private Announcement announcement;

    @NotNull
    @OneToOne
    private User user;

    @NotNull
    private Date date;



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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
