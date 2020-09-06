package com.tauqeer.weatherapp;

public class ViewModel {
    private String date,desc;
    private String imageUrl;

    public ViewModel(String date,String desc,String imageUrl) {
        this.date = date;
        this.desc = desc;
        this.imageUrl = imageUrl;
    }

    public String getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
