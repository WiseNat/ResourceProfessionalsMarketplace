package com.wise.resource.professionals.marketplace.application;

import com.wise.resource.professionals.marketplace.ApplicationRunner;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * {@code SpringbootJavaFxApplication} acts as an injector of Springboot into a JavaFX application.
 * This is created as per the recommendation of building a WeaverFX application.
 */
public class SpringbootJavaFxApplication extends javafx.application.Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        this.context = new SpringApplicationBuilder()
                .sources(ApplicationRunner.class)
                .run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage primaryStage) {
        context.publishEvent(new StageReadyEvent(primaryStage));
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }
}
