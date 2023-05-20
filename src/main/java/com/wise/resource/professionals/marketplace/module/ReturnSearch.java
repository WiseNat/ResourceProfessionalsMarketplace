package com.wise.resource.professionals.marketplace.module;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.ListView;
import com.wise.resource.professionals.marketplace.component.ReturnResourceListBox;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.service.ReturnService;
import com.wise.resource.professionals.marketplace.to.ReturnSearchTO;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
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

/**
 * Controller class for the ReturnSearch.fxml module
 */
@Component
@Getter
@FxmlView("ReturnSearch.fxml")
public class ReturnSearch {

    @Autowired
    private ReturnService returnService;

    @Autowired
    private ComponentUtil componentUtil;

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

    /**
     * Method for when the main role choicebox value is changed. Shouldn't be directly called.
     * <p>
     * Calls {@link ReturnSearch#updateSubRoles()}
     */
    private void mainRoleFieldChanged(ActionEvent actionEvent) {
        updateSubRoles();
    }

    /**
     * Method for when the reset button is clicked. Shouldn't be directly called.
     * <p>
     * Calls {@link ReturnSearch#resetFields()}
     */
    private void resetButtonClicked(MouseEvent mouseEvent) {
        resetFields();
    }

    /**
     * Resets all the user input fields back to their default states.
     */
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

    /**
     * This updates the values in {@link ReturnSearch#subRoleField} based on the value chosen in
     * {@link ReturnSearch#mainRoleField}.
     */
    private void updateSubRoles() {
        String mainRoleString = mainRoleField.getValue();
        componentUtil.updateNullableSubRoles(subRoleField, mainRoleString);
    }

    /**
     * Populates returnable resources using the returnable resources found using the search field predicates.
     */
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

        List<AccountEntity> foundReturnables = returnService.getReturnables(returnSearchTO);

        populateReturnables(foundReturnables);
    }

    /**
     * Populates the {@link ReturnSearch#listView} with multiple {@link ReturnResourceListBox} which are created from
     * the given list of {@link AccountEntity}.
     *
     * @param accountEntities each {@link AccountEntity} in this list is used to create an individual
     *                        {@link ReturnResourceListBox}
     */
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
