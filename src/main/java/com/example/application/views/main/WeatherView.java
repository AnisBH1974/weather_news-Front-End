package com.example.application.views.main;

import com.example.application.restprovider.RESTProvider;
import com.example.application.views.main.data.CityLocation;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.html.Div;

import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import java.text.SimpleDateFormat;
import java.util.*;

import static java.lang.Double.valueOf;


@PageTitle("Weather")
@Route(value = "today", layout = MainView.class)
public class WeatherView extends VerticalLayout  {
    private UI ui;
    private final RESTProvider restProvider;

    static HashMap<String, CityLocation> id_location = new HashMap<>();

    public WeatherView(@Autowired RESTProvider service) throws IOException { // Créer un conteneur pour l'image et le texte

        restProvider = service;

        Div container = new Div(); container.getStyle().set("position", "relative");

        loadAllCities();

        addTitle(container);
        addImage(container);
        addTextOverlays(container);
        add(container);

    }

    private void addTitle(Div container){
        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd MMM H:mm:ss", Locale.ENGLISH);
        String formatted = dateFormat.format(new Date());

        NativeLabel textOverlay = new NativeLabel(formatted);
        textOverlay.getStyle().set("position", "absolute");
        textOverlay.getStyle().set("top", "1%");
        textOverlay.getStyle().set("left", "70%");
        textOverlay.getStyle().set("color", "black");
        textOverlay.setId("ts_label");
        container.add(textOverlay);
        container.add(getTabs());
    }

    private Tabs getTabs() {
        Tabs tabs = new Tabs();
        tabs.getStyle().set("margin", "auto");
        tabs.add(createTab("Today"), createTab("Forecasts"));
        return tabs;
    }

    private Tab createTab(String viewName) {
        RouterLink link = new RouterLink();
        link.add(viewName);
        // Demo has no routes
        // link.setRoute(viewClass.java);
        link.setTabIndex(-1);
        return new Tab(link);
    }

    private void loadAllCities() throws IOException {

        if(!id_location.isEmpty())
            return;

        ClassPathResource resource = new ClassPathResource("France_cities.csv");
        InputStream inputStream = resource.getInputStream();

        String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        //id, country, label, positionTop, positionLeft, positionImgTop, positionImgLeft, defaultIcon
        String[] lines = text.split("\n");
        for (String l:lines){
            if(!l.trim().isEmpty()){
                String[] chunks = l.split(",");
                if( chunks.length==8 ) {
                    id_location.put(chunks[0],
                            new CityLocation(chunks[0], chunks[1], chunks[1], chunks[2], chunks[3],
                                chunks[4], chunks[5], chunks[6], chunks[7]));

                }
            }
        }
    }

    private void addTextOverlays(Div container) {
        for(CityLocation city : id_location.values() ){
            container.add(addTextOverlay(city.getPositionTop(), city.getPositionLeft(), city.getId(), city.getCityOriginalName() ));
            container.add(addImageOverlay( city.getPositionImgTop(), city.getPositionImgLeft(), city.getDefaultIcon(), city.getId() ) );
        }
    }

    private NativeLabel addTextOverlay(String top, String left, String id_label, String label) {
        NativeLabel textOverlay = new NativeLabel(label);
        textOverlay.getStyle().set("position", "absolute");
        textOverlay.getStyle().set("top", top);
        textOverlay.getStyle().set("left", left);
        textOverlay.getStyle().set("transform", "scale(.9)");
        textOverlay.getStyle().set("color", "white"); // Ajustez la couleur selon vos besoins
        textOverlay.getStyle().set("background-color", "rgba(0, 0, 0, 0.3)"); // Optionnel : ajouter un fond transparent au texte
        textOverlay.setId(id_label);
        return textOverlay;
    }

    private Image addImageOverlay(String top, String left, String url, String id_label){
        Image image = url.isEmpty() ? new Image("icons/transparent.svg", ""): new Image(url, "");
        image.getStyle().set("position", "absolute");
        image.getStyle().set("top", top);
        image.getStyle().set("left", left);
      //  image.getStyle().set("transform", "scale(1)");
        image.setId("img_"+id_label);
        return image;
    }

    private void addImage(Div container) {
        Image svgImage = new Image("images/france-departmentsgeojson.svg", "Description de l'image");
        svgImage.getStyle().set("width", "100%"); // Ajustez la taille selon vos besoins
        container.add(svgImage); // Ajouter le texte par-dessus l'image
    }


    @Override
    protected void onAttach(AttachEvent attachEvent) {
        this.ui = attachEvent.getUI();
        restProvider.setView(this);
    }

    public void updElement(Map<String, Object> message){

        Div d = (Div) getComponentAt(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("E dd MMM H:mm:ss", Locale.ENGLISH);
        String formatted = dateFormat.format(new Date());

        NativeLabel ts = (NativeLabel) d.getComponentAt(0);

        int cc = d.getComponentCount();
        int i = 0;
        for(; i<cc; i++) {

            Component c = d.getComponentAt(i);
            if(c.getId().isEmpty())
                continue;

            String id = c.getId().get();
            String city_id = (String) ((HashMap<String, Object>)message.get("city")).get("id");

            if( c.getId().get().equals("ts_label")){
                this.ui.access(() -> {
                    NativeLabel nl = (NativeLabel) d.getComponentAt(0);
                    nl.setText(formatted);
                    ui.push();
                });
                continue;
            }

            if( id.equals(city_id)){
                int finalI = i;
                int finalImg = i+1;
                String name = id_location.get(id).getCityOriginalName();

                String t = (String) ((HashMap<String, Object>)message.get("city")).get("temperature");
                String t_min = (String) ((HashMap<String, Object>)message.get("city")).get("temp_min");
                String t_max = (String) ((HashMap<String, Object>)message.get("city")).get("temp_max");

                String color = (String) ((HashMap<String, Object>)message.get("city")).get("color");
                String iconUrl = (String) ((HashMap<String, Object>)message.get("city")).get("iconUrl");

                Double tDouble = arroundDouble(t);
                Double tDouble_min = arroundDouble(t_min);
                Double tDouble_max = arroundDouble(t_max);

                String newLabel = name + " " + tDouble + "° ("+tDouble_min+"°/"+tDouble_max+"°)";
                this.ui.access(() -> {
                    NativeLabel nl = (NativeLabel) d.getComponentAt(finalI);
                    nl.getStyle().set("color", color);
                    nl.setText(newLabel);

                    Image img_prev = (Image) d.getComponentAt(finalImg);
                    Image img = addImageOverlay( img_prev.getStyle().get("top"), img_prev.getStyle().get("left"), iconUrl, String.valueOf(img_prev.getId()));
                    d.replace( d.getComponentAt(finalImg), img );
                    ui.push();
                });

                CityLocation l = id_location.get(id);
                l.setLabel(newLabel);
                l.setLocationIconUrl(iconUrl);
                id_location.put(id, l);
            }
        }
    }

    private Double arroundDouble(String t) {
        Double number = valueOf(t);
        return
                new BigDecimal(number).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

    }

}

