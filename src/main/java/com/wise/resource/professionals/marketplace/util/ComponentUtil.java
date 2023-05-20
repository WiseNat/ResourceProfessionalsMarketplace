package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

/**
 * Helper methods surrounding JavaFX components
 */
@Component
public class ComponentUtil {

    /**
     * Adds a style class to the given node if the node doesn't have the style class already.
     *
     * @param node       the node to have the style class added to
     * @param styleClass the style class to add to the node
     */
    public void safeAddStyleClass(Node node, String styleClass) {
        if (!node.getStyleClass().contains(styleClass)) {
            node.getStyleClass().add(styleClass);
        }
    }

    /**
     * Removes a node from a parent. The parent of the node must be a {@link Pane} or a subclass of this.
     *
     * @param node the node to remove
     */
    public void removeNode(Node node) {
        ((Pane) node.getParent()).getChildren().remove(node);
    }

    /**
     * Converts a {@link BigDecimal} to a {@link String}, formatting it with 2 decimal places.
     *
     * @param bigDecimal the {@link BigDecimal} to format.
     * @return the formatted {@link BigDecimal}
     */
    public String formatBigDecimal(BigDecimal bigDecimal) {
        return String.format("%,.2f", bigDecimal.setScale(2, RoundingMode.HALF_EVEN));
    }

    /**
     * Updates the items in {@code subRoleField} using the given {@code mainRole}. This also sets the
     * {@code subRoleField} value to a non-null value if sub roles exist for it.
     *
     * @param subRoleField the {@link ChoiceBox} sub role field to be updated
     * @param mainRole     the {@link MainRoleEnum} to use when finding sub roles.
     */
    public void updateSubRoles(ChoiceBox<String> subRoleField, MainRoleEnum mainRole) {
        updateSubRoles(subRoleField, mainRole, true);
    }

    /**
     * Updates the items in {@code subRoleField} using the given {@code mainRole}. This also sets the
     * {@code subRoleField} value to a null value, even if sub roles exist for it.
     *
     * @param subRoleField the {@link ChoiceBox} sub role field to be updated
     * @param mainRole     a String which is converted to a {@link MainRoleEnum} to use when finding sub roles.
     */
    public void updateNullableSubRoles(ChoiceBox<String> subRoleField, String mainRole) {
        subRoleField.setValue(null);

        if (mainRole == null) {
            subRoleField.setDisable(true);
            return;
        }

        MainRoleEnum mainRoleEnum = MainRoleEnum.valueToEnum(mainRole);

        updateNullableSubRoles(subRoleField, mainRoleEnum);
    }

    /**
     * Updates the items in {@code subRoleField} using the given {@code mainRole}. This also sets the
     * {@code subRoleField} value to a null value, even if sub roles exist for it.
     *
     * @param subRoleField the {@link ChoiceBox} sub role field to be updated
     * @param mainRole     a {@link MainRoleEnum} to use when finding sub roles.
     */
    public void updateNullableSubRoles(ChoiceBox<String> subRoleField, MainRoleEnum mainRole) {
        updateSubRoles(subRoleField, mainRole, false);
    }

    /**
     * Updates the items in {@code subRoleField} using the given {@code mainRole}.
     * <p>
     * If {@code updateSubRoleFieldValue} is true, then the subRoleField will have its value set to either null, if
     * there are no sub roles for the given main role, or the first sub role in the list of sub roles.
     * <p>
     * If {@code updateSubRoleFieldValue} is false, then the subRoleField will only have the list of items changed.
     *
     * @param subRoleField the {@link ChoiceBox} sub role field to be updated
     * @param mainRole     a String which is converted to a {@link MainRoleEnum} to use when finding sub roles.
     */
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
