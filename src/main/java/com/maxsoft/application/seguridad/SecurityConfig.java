/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.maxsoft.application.seguridad;

/**
 *
 * @author Maximiliano
 */
//import com.cc.application.views.login.LoginView;
import com.maxsoft.application.views.login.LoginView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Configura el logout
        http.logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .invalidateHttpSession(true)
            .deleteCookies("JSESSIONID")
            .permitAll()
        );

        // Llama a la configuración por defecto de Vaadin
        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Usuario en memoria (puedes usar JDBC o JPA si deseas)

        UserDetails user = User.withUsername("admin")
                .password("{noop}123") // {noop} evita codificación
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
    
    
}
