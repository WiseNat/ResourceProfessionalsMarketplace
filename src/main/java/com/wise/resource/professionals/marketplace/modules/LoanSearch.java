package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ChoiceBox<String> bandField;

    @FXML
    private TextField costPerHourField;

    @FXML
    private Button applyButton;

    @FXML
    private Button resetButton;

    @Autowired
    private ComponentUtil componentUtil;

    @FXML
    public void initialize() {
        ObservableList<String> mainRoleItems = FXCollections.observableArrayList(MainRoleEnum.getAllValues());
        mainRoleField.setItems(mainRoleItems);

        ObservableList<String> bandItems = FXCollections.observableArrayList(BandingEnum.getAllValues());
        bandField.setItems(bandItems);

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
        componentUtil.setChoiceBoxPrompt(bandField, "Band");
        componentUtil.setChoiceBoxPrompt(mainRoleField, "Main Role");
        componentUtil.setChoiceBoxPrompt(subRoleField, "Sub Role");

        costPerHourField.setText("");
        bandField.setValue(null);
        mainRoleField.setValue(null);
        subRoleField.setValue(null);
        subRoleField.setDisable(true);
    }

    private void updateSubRoles() {
        String mainRoleString = mainRoleField.getValue();
        subRoleField.setValue(null);

        if (mainRoleString == null) {
            subRoleField.setDisable(true);
            return;
        }

        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(mainRoleString);
        SubRoleEnum[] subRoles = ROLE_MAPPING.get(mainRole);

        if (subRoles.length == 0) {
            subRoleField.setDisable(true);
        } else {
            subRoleField.setDisable(false);

            ObservableList<String> subRoleItems = FXCollections.observableArrayList(
                    Arrays.stream(subRoles).map(e -> e.value).collect(Collectors.toList()));

            subRoleField.setItems(subRoleItems);
        }
    }
}
