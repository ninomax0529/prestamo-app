/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Maximiliano
 */

package com.maxsoft.application.views.login;

import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;

@Route("/login")
@PermitAll
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private final LoginOverlay loginForm = new LoginOverlay();

    public LoginView() {
        loginForm.setTitle("Prestamo");
        loginForm.setDescription("App de Prestamo");
        loginForm.setAction("login");
        loginForm.setOpened(true);       
        add(loginForm);
        setAlignItems(Alignment.CENTER
        
        );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }
}

