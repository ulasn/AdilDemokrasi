package com.adildemokrasi.adil.Dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResultDTO {

    private List<SearchResultElementDTO> searchElements = new ArrayList<>();

    public List<SearchResultElementDTO> getSearchElements() {
        return searchElements;
    }

    public void setSearchElements(List<SearchResultElementDTO> searchElements) {
        this.searchElements = searchElements;
    }

    public void addElement(SearchResultElementDTO elementDTO){
        this.searchElements.add(elementDTO);
    }
}
