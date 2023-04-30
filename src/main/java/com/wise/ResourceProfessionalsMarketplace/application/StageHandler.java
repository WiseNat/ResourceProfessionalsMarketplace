package com.wise.ResourceProfessionalsMarketplace.application;

import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.controller.LogInController;
import com.wise.ResourceProfessionalsMarketplace.controller.MainView;
import com.wise.ResourceProfessionalsMarketplace.controller.ResourceController;
import com.wise.ResourceProfessionalsMarketplace.to.LogInAccountTO;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Getter
@Component
public class StageHandler implements ApplicationListener<StageReadyEvent> {

    private final FxWeaver fxWeaver;

    private Stage stage;

    @Autowired
    private ResourceController resourceController;

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

    public <C> Scene getScene(Class<C> controllerClass) {
        return new Scene(fxWeaver.loadView(controllerClass));
    }

    public <C> void swapScene(Class<C> controllerClass) {
        Scene scene = this.getScene(controllerClass);
        this.swapScene(scene);
    }

    public <C> void swapScene(Scene scene) {
        this.sceneInit(scene);
        stage.setScene(scene);
    }

    public void sceneInit(Scene scene) {
        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("../styles/application.css")).toExternalForm()
        );
    }
}
