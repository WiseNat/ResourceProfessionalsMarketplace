package com.wise.resource.professionals.marketplace.application;

import javafx.stage.Stage;
import org.springframework.context.ApplicationEvent;


public class StageReadyEvent extends ApplicationEvent {

    public final Stage stage;

    public StageReadyEvent(Stage stage) {
        super(stage);
        this.stage = stage;
    }
}
