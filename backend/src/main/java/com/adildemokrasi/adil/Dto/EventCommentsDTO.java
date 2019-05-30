package com.adildemokrasi.adil.Dto;

import java.util.List;

public class EventCommentsDTO {

    private List<CommentDTO> commentList;

    public List<CommentDTO> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentDTO> commentList) {
        this.commentList = commentList;
    }
}
