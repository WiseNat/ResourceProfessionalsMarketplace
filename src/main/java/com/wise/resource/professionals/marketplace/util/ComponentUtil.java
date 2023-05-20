package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

@Component
public class ComponentUtil {
    public void safeAddStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }

    public <T> void setChoiceBoxPrompt(ChoiceBox<T> choiceBox, String promptText) {
        Platform.runLater(() -> {
            @SuppressWarnings("unchecked")
            SkinBase<?> skin = (SkinBase<?>) choiceBox.getSkin();
            for (Node child : skin.getChildren()) {
                if (child instanceof Label) {
                    Label label = (Label) child;
                    if (label.getText().isEmpty()) {
                        label.setText(promptText);
                    }
                    return;
                }
            }
        });
    }

    public void removeNode(Node node) {
        ((Pane) node.getParent()).getChildren().remove(node);
    }

    public String formatBigDecimal(BigDecimal bigDecimal) {
        return String.format("%,.2f", bigDecimal.setScale(2, RoundingMode.HALF_EVEN));
    }

    public void updateSubRoles(ChoiceBox<String> subRoleField, MainRoleEnum mainRole) {
        updateSubRoles(subRoleField, mainRole, true);
    }

    public void updateNullableSubRoles(ChoiceBox<String> subRoleField, String mainRole) {
        subRoleField.setValue(null);

        if (mainRole == null) {
            subRoleField.setDisable(true);
            return;
        }

        MainRoleEnum mainRoleEnum = MainRoleEnum.valueToEnum(mainRole);

        updateNullableSubRoles(subRoleField, mainRoleEnum);
    }

    public void updateNullableSubRoles(ChoiceBox<String> subRoleField, MainRoleEnum mainRole) {
        updateSubRoles(subRoleField, mainRole, false);
    }

    public void updateSubRoles(ChoiceBox<String> subRoleField, MainRoleEnum mainRole, boolean updateSubRoleFieldValue) {
        SubRoleEnum[] subRoles = ROLE_MAPPING.get(mainRole);

        if (subRoles.length == 0) {
            subRoleField.setDisable(true);

            if (updateSubRoleFieldValue) {
                subRoleField.setValue(null);
            }
        } else {
            subRoleField.setDisable(false);

            ObservableList<String> subRoleItems = FXCollections.observableArrayList(
                    Arrays.stream(subRoles).map(e -> e.value).collect(Collectors.toList()));
            subRoleField.setItems(subRoleItems);

            if (updateSubRoleFieldValue) {
                subRoleField.setValue(subRoleItems.get(0));
            }
        }
    }
}
