package com.wise.ResourceProfessionalsMarketplace;

import com.wise.ResourceProfessionalsMarketplace.application.SpringbootJavaFxApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApplicationRunner {

    public static void main(String[] args) {
        javafx.application.Application.launch(SpringbootJavaFxApplication.class, args);
    }

}
