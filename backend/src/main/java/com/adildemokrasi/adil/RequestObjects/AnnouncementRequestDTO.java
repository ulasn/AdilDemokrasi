package com.adildemokrasi.adil.RequestObjects;

public class AnnouncementRequestDTO {
    private String username;
    private String post;
    private String title;

    public AnnouncementRequestDTO() {
    }

    public AnnouncementRequestDTO(String username, String post, String title) {
        this.username = username;
        this.post = post;
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
