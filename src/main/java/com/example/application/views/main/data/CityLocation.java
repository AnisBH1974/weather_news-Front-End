package com.example.application.views.main.data;

public class CityLocation {
    String id;
    String country;
    String cityOriginalName;
    String label;

    String positionTop;
    String positionLeft;

    WeatherIcon locationIcon;

    public String getCityOriginalName() {
        return cityOriginalName;
    }


    String defaultIcon;

    public CityLocation(String id, String cityOriginalName, String label, String country, String positionTop, String positionLeft,
                    String positionImgTop, String positionImgLeft, String defaultIcon) {
        this.id = id;
        this.country = country;
        this.cityOriginalName = cityOriginalName;
        this.label = label;

        this.positionTop = positionTop;
        this.positionLeft = positionLeft;

        this.locationIcon = new WeatherIcon(positionImgTop, positionImgLeft);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPositionTop() {
        return positionTop;
    }

    public String getPositionLeft() {
        return positionLeft;
    }

    public String getPositionImgTop(){
        return this.locationIcon.positionImgTop;
    }
    public String getPositionImgLeft(){
        return this.locationIcon.positionImgLeft;
    }

    public String getDefaultIcon() {
        return this.locationIcon.urlImg;
    }

    public void setLocationIconUrl(String url){
        this.locationIcon.urlImg = url;
    }


    public String getIconUrl() {
        return this.locationIcon.urlImg;
    }
}
