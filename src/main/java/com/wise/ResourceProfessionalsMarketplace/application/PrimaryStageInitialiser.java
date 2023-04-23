package com.wise.ResourceProfessionalsMarketplace.application;

import com.wise.ResourceProfessionalsMarketplace.controller.LogInController;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PrimaryStageInitialiser implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    @Autowired
    public PrimaryStageInitialiser(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        Scene scene = new Scene(fxWeaver.loadView(LogInController.class), 400, 300);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("../styles/application.css")).toExternalForm());

        Stage stage = event.stage;
        stage.setTitle("Resource Professionals Marketplace");
        stage.setScene(scene);

        // Workaround to prevent maximised JavaFX application blinking on startup
        // https://stackoverflow.com/questions/41606606/start-the-application-window-maximized-in-javafx-fxml-not-working-properly
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());
        stage.setMaximized(true);

        stage.show();
    }
}
