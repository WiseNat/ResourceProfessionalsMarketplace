package com.wise.resource.professionals.marketplace.modules;

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

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NegativeControl;

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
    private LoanService loanService;

    @Autowired
    private ComponentUtil componentUtil;

    @Autowired
    private ValidatorUtil validatorUtil;

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

        mainRoleField.setValue(null);
        subRoleField.setValue(null);
        subRoleField.setDisable(true);
        bandField.setValue(null);
        costPerHourField.setText("");
    }

    private void updateSubRoles() {
        String mainRoleString = mainRoleField.getValue();
        componentUtil.updateNullableSubRoles(subRoleField, mainRoleString);
    }

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
                validatorUtil.markControlNegative(costPerHourField, NegativeControl.value);
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
