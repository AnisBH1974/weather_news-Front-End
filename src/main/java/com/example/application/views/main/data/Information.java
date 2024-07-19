package com.example.application.views.main.data;

import java.time.LocalDate;
import java.util.Date;

import javax.annotation.Nonnull;

public class Information {

    private final String defaultImgUrl  = "https://img.freepik.com/psd-gratuit/nouveau-design-etiquette-realiste_23-2151171088.jpg?w=740&t=st=1718829550~exp=1718830150~hmac=cfddd7fefb0cb3178e9feaf9b9d55003c09d109f27f18e454e2ef4151b512cb2";

    @Nonnull
    private String newsTitle;

    @Nonnull
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@Nonnull String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Nonnull
    private String timeStamp;

    @Nonnull
    public String getUrl() {
        return url;
    }

    @Nonnull
    private String url;

    public Information(@Nonnull String newsTitle, @Nonnull String newsContent, @Nonnull String pictureUrl,
                        @Nonnull String timestamp, @Nonnull String url) {
        this.newsTitle = newsTitle;
        this.newsContent = newsContent;
        this.pictureUrl = pictureUrl.equals("")?defaultImgUrl:pictureUrl;
        this.timeStamp = timestamp;
        this.url = url;
    }

    @Nonnull
    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(@Nonnull String newsTitle) {
        this.newsTitle = newsTitle;
    }

    @Nonnull
    public String getNewsContent() {
        return newsContent;
    }

    public void setNewsContent(@Nonnull String newsContent) {
        this.newsContent = newsContent;
    }

    public void setPictureUrl(@Nonnull String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    @Nonnull
    private String newsContent;

    @Nonnull
    private String pictureUrl;

    public String getPictureUrl() {
        return this.pictureUrl;
    }
}
