package com.wise.resource.professionals.marketplace;

import com.wise.resource.professionals.marketplace.application.SpringbootJavaFxApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationRunner {

    public static void main(String[] args) {
        javafx.application.Application.launch(SpringbootJavaFxApplication.class, args);
    }

}
