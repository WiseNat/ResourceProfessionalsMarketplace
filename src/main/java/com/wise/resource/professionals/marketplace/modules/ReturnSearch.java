package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.ListView;
import com.wise.resource.professionals.marketplace.component.ReturnResourceListBox;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.to.ReturnSearchTO;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import com.wise.resource.professionals.marketplace.util.ReturnUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private Button applyButton;

    @FXML
    private Button resetButton;

    @Autowired
    private ReturnUtil returnUtil;

    @Autowired
    private ComponentUtil componentUtil;

    private ListView listView;

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
        mainRoleField.setTooltip(new Tooltip("Main Role"));
        subRoleField.setTooltip(new Tooltip("Sub Role"));

        firstNameField.setText("");
        lastNameField.setText("");
        clientField.setText("");
        mainRoleField.setValue(null);
        subRoleField.setValue(null);
        subRoleField.setDisable(true);
        bandField.setValue(null);
    }

    private void updateSubRoles() {
        String mainRoleString = mainRoleField.getValue();
        componentUtil.updateNullableSubRoles(subRoleField, mainRoleString);
    }

    public void populatePredicateReturnables() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String client = clientField.getText();
        BandingEnum banding = BandingEnum.valueToEnum(bandField.getValue());
        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(mainRoleField.getValue());

        String subRoleString = subRoleField.getValue();
        SubRoleEnum subRole = null;
        if (subRoleString != null) {
            subRole = SubRoleEnum.valueToEnum(subRoleString);
        }

        ReturnSearchTO returnSearchTO = new ReturnSearchTO();
        returnSearchTO.setFirstName(firstName);
        returnSearchTO.setLastName(lastName);
        returnSearchTO.setClient(client);
        returnSearchTO.setSubRole(subRole);
        returnSearchTO.setMainRole(mainRole);
        returnSearchTO.setBanding(banding);

        List<AccountEntity> foundReturnables = returnUtil.getReturnables(returnSearchTO);

        populateReturnables(foundReturnables);
    }

    private void populateReturnables(List<AccountEntity> accountEntities) {
        listView.clearAllChildren();

        for (AccountEntity accountEntity : accountEntities) {
            ListBox returnableResourceListBox = new ReturnResourceListBox(accountEntity);
            listView.addChild(returnableResourceListBox);
        }

        title.setText(accountEntities.size() + " loaned resources found");
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

}
