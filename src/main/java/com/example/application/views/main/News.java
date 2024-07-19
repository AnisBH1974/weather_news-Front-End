package com.example.application.views.main;

import com.example.application.restprovider.RESTProvider;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.application.views.main.data.Information;

@PageTitle("News")
@Route(value = "thenews", layout = MainView.class)

public class News extends Div {

    private UI ui;
    private final RESTProvider restProvider;

    private static List<Information> infos = new ArrayList<>();


    private ComponentRenderer<Component, Information> newsCardRenderer = new ComponentRenderer<>(info -> {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        Image img = new Image( info.getPictureUrl(),info.getNewsTitle());
        img.setHeight("64px");
        img.setWidth("64px");

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);

        String ts = info.getTimeStamp().replace("T", " ").replace("Z", "");

        infoLayout.getElement().appendChild(ElementFactory.createStrong(ts + " "), ElementFactory.createAnchor(info.getUrl(),  info.getNewsTitle()) );
        infoLayout.add(new Div(new Text(info.getNewsContent())));


//        VerticalLayout contactLayout = new VerticalLayout();
//        contactLayout.setSpacing(false);
//        contactLayout.setPadding(false);
//        contactLayout.add(new Div(new Text(person.getEmail())));
//        contactLayout.add(new Div(new Text(person.getAddress().getPhone())));
//        infoLayout.add(new Details("Contact information", contactLayout));

        cardLayout.add(img, infoLayout);

        return cardLayout;
    });

    public News(@Autowired RESTProvider service) {
        this.restProvider = service;

        Div container = new Div(); //container.getStyle().set("position", "relative");

         // tag::snippet[]
        VirtualList<Information> list = new VirtualList<>();
        list.setItems(infos);
        list.setRenderer(newsCardRenderer);
        list.setHeight("800px");
        container.add(list);
        add(container);
        //setSizeFull();
        // end::snippet[]
    }

    public static void setInfos(List<Information> infos) {
        News.infos = infos;
    }

    public static List<Information> getInfos() {
        return News.infos;
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        this.ui = attachEvent.getUI();
        restProvider.setNewsView(this);
    }

    public void clearNews(){
        Div d = (Div) getComponentAt(0);
        VirtualList<Information> vlist =  (VirtualList<Information>) d.getComponentAt(0);
        List<Information> noinfos = new ArrayList<>();
        this.ui.access(() -> {
            Div container = new Div(); container.getStyle().set("position", "relative");

            vlist.setItems(noinfos);
            vlist.setRenderer(newsCardRenderer);
            container.add(vlist);
            replace( getComponentAt(0), container );

        });
    }

    public void updElement(Map<String, Object> payload) {

        Div d = (Div) getComponentAt(0);

        String ts = (String) ((HashMap<String, Object>)payload.get("news")).get("ts");
        String title = (String) ((HashMap<String, Object>)payload.get("news")).get("title");
        String content = (String) ((HashMap<String, Object>)payload.get("news")).get("content");
        String img_url = (String) ((HashMap<String, Object>)payload.get("news")).get("img_url");
        String url = (String) ((HashMap<String, Object>)payload.get("news")).get("url");

        VirtualList<Information> list = (VirtualList<Information>) d.getComponentAt(0);

        List<Information> newList = getInfos();
            newList.add( new Information(title, content, img_url, ts, url)
        );

        this.ui.access(() -> {
            Div container = new Div(); container.getStyle().set("position", "relative");

            list.setItems(newList);
            list.setRenderer(newsCardRenderer);
            container.add(list);
            replace( getComponentAt(0), container );
            Notification.show(content);
        });



        setInfos(newList);

    }
}

