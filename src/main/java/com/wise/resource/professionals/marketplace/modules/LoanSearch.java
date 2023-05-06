package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.component.MinMaxField;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

@Component
@Getter
@FxmlView("LoanSearch.fxml")
public class LoanSearch {
    @FXML
    private Label title;

    @FXML
    private ChoiceBox<String> mainRoleField;

    @FXML
    private ChoiceBox<String> subRoleField;

    @FXML
    private MinMaxField bandField;

    @FXML
    private MinMaxField costPerHourField;

    @FXML
    private Button applyButton;

    @FXML
    private Button resetButton;

    @FXML
    public void initialize() {
        bandField.setPromptText("Band");
        costPerHourField.setPromptText("Cost Per Hour");

        ObservableList<String> mainRoleItems = FXCollections.observableArrayList(MainRoleEnum.getAllValues());
        mainRoleField.setItems(mainRoleItems);

        mainRoleField.setOnAction(this::mainRoleFieldChanged);
        resetButton.setOnMouseClicked(this::resetButtonClicked);

        resetFields();
    }

    private void mainRoleFieldChanged(ActionEvent actionEvent) {
        updateSubRoles();
    }

    private void resetButtonClicked(MouseEvent mouseEvent) {
        resetFields();
    }

    private void resetFields() {
        bandField.getMaxField().setText("");
        bandField.getMinField().setText("");

        costPerHourField.getMaxField().setText("");
        costPerHourField.getMinField().setText("");

        mainRoleField.setValue(null);
        subRoleField.setValue(null);
        subRoleField.setDisable(true);
    }

    private void updateSubRoles() {
        String mainRoleString = mainRoleField.getValue();

        if (mainRoleString == null) {
            subRoleField.setDisable(true);
            subRoleField.setValue(null);
            return;
        }

        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(mainRoleString);
        SubRoleEnum[] subRoles = ROLE_MAPPING.get(mainRole);

        if (subRoles.length == 0) {
            subRoleField.setDisable(true);
            subRoleField.setValue(null);
        } else {
            subRoleField.setDisable(false);

            ObservableList<String> subRoleItems = FXCollections.observableArrayList(
                    Arrays.stream(subRoles).map(e -> e.value).collect(Collectors.toList()));

            subRoleField.setItems(subRoleItems);
            subRoleField.setValue(subRoleItems.get(0));
        }
    }
}
