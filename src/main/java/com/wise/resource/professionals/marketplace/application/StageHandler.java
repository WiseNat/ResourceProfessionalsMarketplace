package com.wise.resource.professionals.marketplace.application;

import com.wise.resource.professionals.marketplace.controller.LogInController;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import static com.wise.resource.professionals.marketplace.constant.ApplicationEnum.DEFAULT_STYLESHEET_PATH;

/**
 * {@code StageHandler} deals with any high level initialisation to do with JavaFX. It typically works with both
 * {@link Scene} and {@link Stage} instances, enabling easier management of them. Typically, this class will be used to
 * build the application on startup, get and swap scenes.
 */
@Getter
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
        Scene scene = this.getScene(LogInController.class);
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

    /**
     * Gets the scene associated with a given controller class.
     * This controller class will initially be loaded through the {@link FxWeaver#loadView(Class)} method.
     * <p>
     * Example usage: {@code getScene(MyController.class)}
     *
     * @param controllerClass a controller class which uses the {@link net.rgielen.fxweaver.core.FxmlView} annotation
     * @return a new scene for the given controller class
     */
    public <C> Scene getScene(Class<C> controllerClass) {
        return new Scene(fxWeaver.loadView(controllerClass));
    }

    /**
     * Gets the scene for a given controller class and swaps the scene to that,
     *
     * @param controllerClass a controller class which uses the {@link net.rgielen.fxweaver.core.FxmlView} annotation
     */
    public <C> void swapScene(Class<C> controllerClass) {
        Scene scene = this.getScene(controllerClass);
        this.swapScene(scene);
    }

    /**
     * Swaps the current scene to a new scene and applies the default style sheet to it.
     *
     * @param scene the scene to be swapped to.
     */
    public void swapScene(Scene scene) {
        this.sceneInit(scene);
        stage.setScene(scene);
    }

    /**
     * Any initialisation methods that need to be run for a scene.
     *
     * @param scene the scene the initialisation should be run on.
     */
    public void sceneInit(Scene scene) {
        scene.getStylesheets().add(DEFAULT_STYLESHEET_PATH.value);
    }
}
