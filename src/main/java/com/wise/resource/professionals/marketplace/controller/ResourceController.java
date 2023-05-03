package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.modules.UpdateDetails;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.UpdateDetailsUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

@Component
@FxmlView("ResourceView.fxml")
public class ResourceController implements MainView {

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<UpdateDetails, VBox> updateDetails;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ResourceRepository resourceRepository;
    @Autowired
    private Validator validator;
    @Autowired
    private ValidatorUtil validatorUtil;
    @Autowired
    private UpdateDetailsUtil updateDetailsUtil;
    @Autowired
    private EnumUtil enumUtil;
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

        if (resourceEntity.getLoanedClient() != null) {
            TextField costPerHourField = updateDetails.getController().getCostPerHourField();
            ((VBox) costPerHourField.getParent()).getChildren().remove(costPerHourField);
        }

        updateDetails.getController().getBandField().setValue(resourceEntity.getBanding().getName());
        updateDetails.getController().getMainRoleField().setValue(resourceEntity.getMainRole().getName());
        updateSubRoles();
        updateDetails.getController().getCostPerHourField().setText(resourceEntity.getCostPerHour().toPlainString());

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

        ResourceTO resourceTo;

        try {
            resourceTo = updateDetailsUtil.createResourceTO(banding, subRole, mainRole, costPerHour, resourceEntity);
        } catch (NumberFormatException e) {
            validatorUtil.markControlNegative(updateDetails.getController().getCostPerHourField(), "negative-control");
            return;
        }

        Set<ConstraintViolation<ResourceTO>> violations = validator.validate(resourceTo);
        this.markTextFields(violations);

        if (violations.size() > 0) {
            return;
        }

        SubRoleEntity subRoleEntity = null;
        if (resourceTo.getSubRole() != null) {
            subRoleEntity = enumUtil.subRoleToEntity(resourceTo.getSubRole());
        }

        resourceEntity.setMainRole(enumUtil.mainRoleToEntity(resourceTo.getMainRole()));
        resourceEntity.setSubRole(subRoleEntity);
        resourceEntity.setBanding(enumUtil.bandingToEntity(resourceTo.getBanding()));
        resourceEntity.setCostPerHour(resourceTo.getCostPerHour());

        resourceRepository.save(resourceEntity);
    }

    private void markTextFields(Set<ConstraintViolation<ResourceTO>> violations) {
        HashMap<String, Control> toFieldToControl = new HashMap<String, Control>() {{
            put("banding", updateDetails.getController().getBandField());
            put("subRole", updateDetails.getController().getSubRoleField());
            put("costPerHour", updateDetails.getController().getCostPerHourField());
        }};

        validatorUtil.markControlAgainstValidatedTO(violations, toFieldToControl, "negative-control");
    }

    private void mainRoleFieldChanged(ActionEvent actionEvent) {
        updateSubRoles();
    }

    private void updateSubRoles() {
        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(updateDetails.getController().getMainRoleField().getValue());
        SubRoleEnum[] subRoles = ROLE_MAPPING.get(mainRole);

        if (subRoles.length == 0) {
            updateDetails.getController().getSubRoleField().setDisable(true);
            updateDetails.getController().getSubRoleField().setValue(null);
        } else {
            updateDetails.getController().getSubRoleField().setDisable(false);

            ObservableList<String> subRoleItems = FXCollections.observableArrayList(
                    Arrays.stream(subRoles).map(e -> e.value).collect(Collectors.toList()));
            updateDetails.getController().getSubRoleField().setItems(subRoleItems);
            updateDetails.getController().getSubRoleField().setValue(subRoleItems.get(0));
        }
    }
}
