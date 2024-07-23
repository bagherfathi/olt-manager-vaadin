package com.gohardani.oltmanager.ui.view;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.gohardani.oltmanager.ui.MainLayout;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;

/**
 * @author Alejandro Duarte
 */
@PermitAll
@Route(value = "", layout = MainLayout.class)
public class HomeView extends VerticalLayout {

    public HomeView() {
        add(
                new H1("TCT OLT Manager"),
                new Html("<span>" +
                        "This is the  app for the" +
                        " <a href='https://www.oltmanager.com'>TCT OLT Managing</a>." +
                        "</span>")
        );
    }

}
