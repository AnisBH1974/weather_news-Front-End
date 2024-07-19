package com.example.application.views.main;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;

@CssImport("./styles/image-background-style.css") // Import your CSS file
public class ImageBackgroundView extends Div {

    public ImageBackgroundView() {
        // Set a CSS class to apply background image

        this.setHeight("100");
        this.setWidth("100");;

        addClassName("image-background");

        // Content inside the Div
        // You can add other components or content here
        add(" ");
    }
}

