package com.adildemokrasi.adil.Dto;

import java.util.List;

public class SearchResultDTO {

    private List<UserDTO> filteredUserList;

    public List<UserDTO> getFilteredUserList() {
        return filteredUserList;
    }

    public void setFilteredUserList(List<UserDTO> filteredUserList) {
        this.filteredUserList = filteredUserList;
    }
}
