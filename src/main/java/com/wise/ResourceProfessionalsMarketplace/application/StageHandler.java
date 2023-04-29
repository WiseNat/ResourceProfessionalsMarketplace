package com.wise.ResourceProfessionalsMarketplace.application;

import com.wise.ResourceProfessionalsMarketplace.controller.ResourceController;
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
public class StageHandler implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    private Stage stage;

    @Autowired
    public StageHandler(FxWeaver fxWeaver) {
        this.fxWeaver = fxWeaver;
    }

    /**
     * Initialises the JavaFX applciation
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(StageReadyEvent event) {
//        Scene scene = this.getScene(LogInController.class);
        Scene scene = this.getScene(ResourceController.class);
        this.sceneInit(scene);

        stage = event.stage;
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

    public <C> Scene getScene(Class<C> controllerClass) {
        return new Scene(fxWeaver.loadView(controllerClass));
    }

    public <C> void swapScene(Class<C> controllerClass) {
        Scene scene = this.getScene(controllerClass);
        this.sceneInit(scene);
        stage.setScene(scene);
    }

    public void sceneInit(Scene scene) {
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("../styles/application.css")).toExternalForm()
        );
    }
}
