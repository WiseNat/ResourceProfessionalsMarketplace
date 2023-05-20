package com.wise.resource.professionals.marketplace.module;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.ListView;
import com.wise.resource.professionals.marketplace.component.LoanResourceListBox;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.service.LoanService;
import com.wise.resource.professionals.marketplace.to.LoanSearchTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
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

import java.math.BigDecimal;
import java.util.List;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NEGATIVE_CONTROL;

/**
 * Controller class for the LoanSearch.fxml module
 */
@Component
@Getter
@FxmlView("LoanSearch.fxml")
public class LoanSearch {

    @Autowired
    private LoanService loanService;

    @Autowired
    private ComponentUtil componentUtil;

    @Autowired
    private ValidatorUtil validatorUtil;

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
     * Calls {@link LoanSearch#updateSubRoles()}
     */
    private void mainRoleFieldChanged(ActionEvent actionEvent) {
        updateSubRoles();
    }

    /**
     * Method for when the reset button is clicked. Shouldn't be directly called.
     * <p>
     * Calls {@link LoanSearch#resetFields()}
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

        mainRoleField.setValue(null);
        subRoleField.setValue(null);
        subRoleField.setDisable(true);
        bandField.setValue(null);
        costPerHourField.setText("");
    }

    /**
     * This updates the values in {@link LoanSearch#subRoleField} based on the value chosen in
     * {@link LoanSearch#mainRoleField}.
     */
    private void updateSubRoles() {
        String mainRoleString = mainRoleField.getValue();
        componentUtil.updateNullableSubRoles(subRoleField, mainRoleString);
    }

    /**
     * Populates loanable resources using the loanable resources found using the search field predicates.
     */
    public void populatePredicateLoanables() {
        BandingEnum banding = BandingEnum.valueToEnum(bandField.getValue());
        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(mainRoleField.getValue());

        String subRoleString = subRoleField.getValue();
        SubRoleEnum subRole = null;
        if (subRoleString != null) {
            subRole = SubRoleEnum.valueToEnum(subRoleString);
        }

        String costPerHourString = costPerHourField.getText();
        BigDecimal costPerHour = null;
        if (!costPerHourString.isEmpty()) {
            try {
                costPerHour = new BigDecimal(costPerHourField.getText());
            } catch (NumberFormatException e) {
                validatorUtil.markControlNegative(costPerHourField, NEGATIVE_CONTROL.value);
                return;
            }
        }

        LoanSearchTO loanSearchTO = new LoanSearchTO();
        loanSearchTO.setBanding(banding);
        loanSearchTO.setMainRole(mainRole);
        loanSearchTO.setSubRole(subRole);
        loanSearchTO.setCostPerHour(costPerHour);

        List<ResourceCollectionTO> foundLoanables = loanService.getLoanables(loanSearchTO);

        populateLoanables(foundLoanables);
    }

    /**
     * Populates the {@link LoanSearch#listView} with multiple {@link LoanResourceListBox} which are created from the
     * given list of {@link ResourceCollectionTO}.
     *
     * @param resourceCollections each {@link ResourceCollectionTO} in this list is used to create an individual
     *                            {@link LoanResourceListBox}
     */
    private void populateLoanables(List<ResourceCollectionTO> resourceCollections) {
        listView.clearAllChildren();

        int totalResources = 0;

        for (ResourceCollectionTO resourceCollection : resourceCollections) {
            totalResources += resourceCollection.getQuantity();

            ListBox loanableResourceListBox = new LoanResourceListBox(resourceCollection);
            listView.addChild(loanableResourceListBox);
        }

        title.setText(resourceCollections.size() + " collections found\n" + totalResources + " loanable resources found");
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }
}
