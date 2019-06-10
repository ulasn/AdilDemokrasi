package com.adildemokrasi.adil.Dto;

import java.util.ArrayList;
import java.util.List;

public class ModelDTO {

    private List<NewsItemDTO> cultureNews = new ArrayList<>();

    private List<NewsItemDTO> educationNews = new ArrayList<>();

    private List<NewsItemDTO> enviromentNews = new ArrayList<>();


    public List<NewsItemDTO> getCultureNews() {
        return cultureNews;
    }

    public void setCultureNews(List<NewsItemDTO> cultureNews) {
        this.cultureNews = cultureNews;
    }

    public void addCultureNews(NewsItemDTO newsItemDTO){
        this.cultureNews.add(newsItemDTO);
    }

    public List<NewsItemDTO> getEducationNews() {
        return educationNews;
    }

    public void setEducationNews(List<NewsItemDTO> educationNews) {
        this.educationNews = educationNews;
    }

    public void addEducationNews(NewsItemDTO newsItemDTO) {
        this.educationNews.add(newsItemDTO);
    }

    public List<NewsItemDTO> getEnviromentNews() {
        return enviromentNews;
    }


    public void setEnviromentNews(List<NewsItemDTO> enviromentNews) {
        this.enviromentNews = enviromentNews;
    }

    public void addEnviromentNews(NewsItemDTO newsItemDTO){
        this.enviromentNews.add(newsItemDTO);
    }
}
