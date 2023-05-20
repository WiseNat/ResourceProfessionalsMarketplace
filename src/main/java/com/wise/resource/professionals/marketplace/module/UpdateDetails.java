package com.wise.resource.professionals.marketplace.module;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

/**
 * Controller class for the UpdateDetails.fxml module
 */
@Component
@Getter
@FxmlView("UpdateDetails.fxml")
public class UpdateDetails {
    @FXML
    private ChoiceBox<String> mainRoleField;

    @FXML
    private ChoiceBox<String> subRoleField;

    @FXML
    private ChoiceBox<String> bandField;

    @FXML
    private TextField costPerHourField;

    @FXML
    private Button saveDetailsButton;

    @FXML
    public void initialize() {
        ObservableList<String> bandingItems = FXCollections.observableArrayList(BandingEnum.getAllValues());
        bandField.setItems(bandingItems);

        ObservableList<String> mainRoleItems = FXCollections.observableArrayList(MainRoleEnum.getAllValues());
        mainRoleField.setItems(mainRoleItems);
    }
}
