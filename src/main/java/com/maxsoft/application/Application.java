package com.maxsoft.application;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "medicina-app")
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
              
        TimeZone.setDefault(TimeZone.getTimeZone("America/Santo_Domingo"));
        
        SpringApplication.run(Application.class, args);

        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        System.out.println("Max Memory: " + maxMemory / 1024 / 1024 + " MB");
        System.out.println("Allocated Memory: " + allocatedMemory / 1024 / 1024 + " MB");
        System.out.println("Free Memory in allocated: " + freeMemory / 1024 / 1024 + " MB");
        System.out.println("Used Memory: " + (allocatedMemory - freeMemory) / 1024 / 1024 + " MB");

    }
}
