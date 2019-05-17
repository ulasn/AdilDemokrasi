package com.adildemokrasi.adil.Dto;

public class SearchResultElementDTO {

    private String name;

    private String tag;

    public String getName() {
        return name;
    }

    public SearchResultElementDTO(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
