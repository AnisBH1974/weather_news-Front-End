package com.example.application.views.main;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.view.AbstractView;

import java.lang.annotation.Inherited;
import java.util.Map;

@PageTitle("About")
@Route(value = "about", layout = MainView.class)
public class AboutView extends HorizontalLayout {

   public void AboutView() {
       Div container = new Div(); container.getStyle().set("position", "relative");
       NativeLabel textOverlay = new NativeLabel("About this view");
       container.add(textOverlay); // Ajouter le conteneur à la vue principale
       add(container);
   }

}

