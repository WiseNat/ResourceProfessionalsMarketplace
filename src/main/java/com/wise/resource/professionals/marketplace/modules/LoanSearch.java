package com.wise.resource.professionals.marketplace.modules;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.LoanResourceListBox;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LoanSearchTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.LoanUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
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

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private LoanUtil loanUtil;

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

    private void resetFields() {
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
                validatorUtil.markControlNegative(costPerHourField, "negative-control");
                return;
            }
        }

        LoanSearchTO loanSearchTO = new LoanSearchTO();
        loanSearchTO.setBanding(banding);
        loanSearchTO.setMainRole(mainRole);
        loanSearchTO.setSubRole(subRole);
        loanSearchTO.setCostPerHour(costPerHour);

        applyLoanSearch(loanSearchTO);
    }

    private void applyLoanSearch(LoanSearchTO loanSearchTO) {
        List<ResourceRepository.IResourceCollection> foundLoanables = resourceRepository.findAllByCollectionWithPredicates(
                enumUtil.bandingToEntity(loanSearchTO.getBanding()),
                enumUtil.mainRoleToEntity(loanSearchTO.getMainRole()),
                enumUtil.subRoleToEntity(loanSearchTO.getSubRole()),
                loanSearchTO.getCostPerHour()
        );

        List<ResourceCollectionTO> resourceCollections = loanUtil.iResourceCollectionToResourceCollectionTO(foundLoanables);

        populateLoanables(resourceCollections);
    }

    public void populateAllLoanables() {
        List<ResourceRepository.IResourceCollection> rawResourceCollections = resourceRepository.findAllByCollection();
        List<ResourceCollectionTO> resourceCollections = loanUtil.iResourceCollectionToResourceCollectionTO(rawResourceCollections);

        populateLoanables(resourceCollections);
    }

    private void populateLoanables(List<ResourceCollectionTO> resourceCollections) {
        listView.getController().clearAllChildren();

        int totalResources = 0;

        for (ResourceCollectionTO resourceCollection : resourceCollections) {
            totalResources += resourceCollection.getQuantity();

            ListBox loanableResourceListBox = new LoanResourceListBox(resourceCollection);
            listView.getController().addChild(loanableResourceListBox);
        }

        title.setText(resourceCollections.size() + " collections found\n" + totalResources + " loanable resources found");
    }

    public void setListView(FxControllerAndView<ListView, VBox> listView) {
        this.listView = listView;
    }
}
