package com.adildemokrasi.adil.Entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Announcement extends BaseCreation {

    @Id
    @GeneratedValue
    @Column(name = "announcement_id")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn
    private User creator;

    @NotNull
    private String title;

    private String post;

    @ManyToOne
    private NGO ngo;

    @OneToMany(mappedBy = "announcement")
    private List<Comment> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
