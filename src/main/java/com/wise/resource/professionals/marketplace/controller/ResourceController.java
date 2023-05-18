package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.modules.UpdateDetails;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.to.InvalidFieldsAndDataTO;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.to.RawResourceTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ResourceUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Objects;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NegativeControl;

@Component
@FxmlView("ResourceView.fxml")
public class ResourceController implements MainView {

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<UpdateDetails, VBox> updateDetails;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ResourceUtil resourceUtil;

    @Autowired
    private ComponentUtil componentUtil;

    private AccountEntity accountEntity;
    private ResourceEntity resourceEntity;

    public ResourceController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<UpdateDetails, VBox> updateDetails) {
        this.mainSkeleton = mainSkeleton;
        this.updateDetails = updateDetails;

        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {
        this.accountEntity = accountRepository.findByEmailAndAccountType(
                logInAccountTO.getEmail(),
                enumUtil.accountTypeToEntity(logInAccountTO.getAccountType()));

        this.resourceEntity = this.accountEntity.getResource();

        postInitialize();
    }

    /**
     * Initialisation that relies on accountEntity being set.
     * This would normally be included in initialize
     */
    @SneakyThrows
    private void postInitialize() {
        if (!updateDetails.getView().isPresent()) {
            throw new IllegalAccessException("The view for updateDetails was not found");
        }

        mainSkeleton.getController().setMainContent(updateDetails.getView().get());
        GridPane.setHalignment(updateDetails.getView().get(), HPos.CENTER);

        String name = StringUtils.capitalize(accountEntity.getFirstName()) + " " + StringUtils.capitalize(accountEntity.getLastName());
        mainSkeleton.getController().setTitle("Hi " + name, "You can change your details here");

        mainSkeleton.getController().removeRightContent();

        TextField costPerHourField = updateDetails.getController().getCostPerHourField();
        costPerHourField.setVisible(resourceEntity.getLoanedClient() == null);

        updateDetails.getController().getBandField().setValue(resourceEntity.getBanding().getName());
        updateDetails.getController().getMainRoleField().setValue(resourceEntity.getMainRole().getName());
        updateDetails.getController().getCostPerHourField().setText(resourceEntity.getCostPerHour().toPlainString());

        updateSubRoles();

        updateDetails.getController().getSubRoleField().setValue(resourceEntity.getSubRole().getName());

        updateDetails.getController().getMainRoleField().setOnAction(this::mainRoleFieldChanged);
        updateDetails.getController().getSaveDetailsButton().setOnMouseClicked(this::saveDetailsClicked);

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/account.png")));
        navbarButton.setActive(true);
    }

    private void saveDetailsClicked(MouseEvent mouseEvent) {
        String banding = updateDetails.getController().getBandField().getValue();
        String subRole = updateDetails.getController().getSubRoleField().getValue();
        String mainRole = updateDetails.getController().getMainRoleField().getValue();
        String costPerHour = updateDetails.getController().getCostPerHourField().getText();

        RawResourceTO rawResourceTO = new RawResourceTO(resourceEntity, mainRole, subRole, banding, costPerHour);

        InvalidFieldsAndDataTO<ResourceTO> convertedTO = resourceUtil.createResourceTo(rawResourceTO);

        if (convertedTO.getInvalidFields().length > 0) {
            markTextFields(convertedTO.getInvalidFields());
            return;
        }

        resourceUtil.updateResourceDetails(resourceEntity, convertedTO.getData());
    }

    private void markTextFields(String[] fields) {
        HashMap<String, Control> toFieldToControl = new HashMap<String, Control>() {{
            put("banding", updateDetails.getController().getBandField());
            put("subRole", updateDetails.getController().getSubRoleField());
            put("costPerHour", updateDetails.getController().getCostPerHourField());
        }};

        validatorUtil.markControlAgainstValidatedTO(fields, toFieldToControl, NegativeControl.value);
    }

    private void mainRoleFieldChanged(ActionEvent actionEvent) {
        updateSubRoles();
    }

    private void updateSubRoles() {
        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(updateDetails.getController().getMainRoleField().getValue());

        componentUtil.updateSubRoles(updateDetails.getController().getSubRoleField(), mainRole);
    }
}
