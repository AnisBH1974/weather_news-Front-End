package com.example.application.restprovider;

import com.example.application.views.main.WeatherView;
import com.example.application.views.main.News;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;


@RestController
public class RESTProvider {

    private WeatherView view;
    private News newsView;


    @PostMapping( value = "/upd" )
    public void updateView(@RequestBody Map<String, Object> payload) throws IOException {
        if( this.view != null )
            this.view.updElement( payload );
    }

    @PostMapping( value = "/updNews" )
    public void updateNewsView(@RequestBody Map<String, Object> payload) throws IOException {
        if( this.newsView != null )
            this.newsView.updElement( payload );
    }

    @PostMapping( value = "/clrNews" )
    public void clearNewsView() throws IOException {
        if( this.newsView != null )
            this.newsView.clearNews();
    }

    public void setView(WeatherView view) {
            this.view = view;
    }

    public void setNewsView(News newsView) {
        this.newsView = newsView;
    }



}
