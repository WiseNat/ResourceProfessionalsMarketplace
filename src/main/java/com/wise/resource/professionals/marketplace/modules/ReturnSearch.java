package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.ReturnResourceListBox;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

@Component
@Getter
@FxmlView("ReturnSearch.fxml")
public class ReturnSearch {

    @FXML
    private Label title;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField clientField;

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

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AccountRepository accountRepository;

    private FxControllerAndView<ListView, VBox> listView;

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

    public void resetFields() {
        bandField.setTooltip(new Tooltip("Band"));
//        componentUtil.setChoiceBoxPrompt(bandField, "Band");
//        componentUtil.setChoiceBoxPrompt(mainRoleField, "Main Role");
//        componentUtil.setChoiceBoxPrompt(subRoleField, "Sub Role");

        firstNameField.setText("");
        lastNameField.setText("");
        clientField.setText("");
        mainRoleField.setValue(null);
        subRoleField.setValue(null);
        subRoleField.setDisable(true);
        bandField.setValue(null);
        costPerHourField.setText("");
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

    public void populateAllReturnables() {
        List<ResourceEntity> returnableResourceEntities = resourceRepository.findByLoanedClientIsNotNull();

        ArrayList<AccountEntity> returnableAccountEntities = new ArrayList<>();
        for (ResourceEntity returnableResourceEntity : returnableResourceEntities) {
            returnableAccountEntities.add(accountRepository.findByResource(returnableResourceEntity));
        }

        populateReturnables(returnableAccountEntities);
    }

    private void populateReturnables(List<AccountEntity> accountEntities) {
        listView.getController().clearAllChildren();

        for (AccountEntity accountEntity : accountEntities) {
            ListBox returnableResourceListBox = new ReturnResourceListBox(accountEntity);
            listView.getController().addChild(returnableResourceListBox);
        }

        title.setText(accountEntities.size() + " loaned resources found");
    }

    public void setListView(FxControllerAndView<ListView, VBox> listView) {
        this.listView = listView;
    }

}
