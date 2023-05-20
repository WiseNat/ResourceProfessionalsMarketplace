package com.wise.resource.professionals.marketplace.application;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;


/**
 * An {@link ApplicationEvent} which includes a stage. This is created as per the recommendation of building a
 * WeaverFX application.
 */
public class StageReadyEvent extends ApplicationEvent {

    public final Stage stage;

    public StageReadyEvent(Stage stage) {
        super(stage);
        this.stage = stage;
    }
}
