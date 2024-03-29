package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import lombok.Getter;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.HashMap;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NEGATIVE_CONTROL;
import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NEGATIVE_DATE_PICKER_DAY_CELL;

/**
 * An extension of {@link Modal} which provides automatic initialisation and helper methods specific to loans.
 */
@Getter
public class LoanModal extends Modal {

    private final ResourceCollectionTO resourceCollectionTO;

    @FXML
    private Label topText;

    @FXML
    private Label bottomText;

    @FXML
    private TextField clientField;

    @FXML
    private Spinner<Integer> amountField;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button loanButton;

    @FXML
    private Button saveToFileButton;

    /**
     * Creates the LoanModal along with initialising the content using the given {@link ResourceCollectionTO}
     *
     * @param resourceCollectionTO used to initialise content within the modal.
     */
    @SneakyThrows
    public LoanModal(ResourceCollectionTO resourceCollectionTO) {
        super();

        this.resourceCollectionTO = resourceCollectionTO;

        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource("../module/LoanModalLeft.fxml"));
        fxmlLoader.setController(this);
        Node leftContainer = fxmlLoader.load();
        setLeftContent(leftContainer);

        fxmlLoader = new FXMLLoader(getClass().getResource("../module/LoanModalRight.fxml"));
        fxmlLoader.setController(this);
        Node rightContainer = fxmlLoader.load();
        setRightContent(rightContainer);

        ResourceTO resourceTO = resourceCollectionTO.getResource();

        String banding = resourceTO.getBanding().value;
        String mainRole = resourceTO.getMainRole().value;
        String subRole = "";
        String costPerHour = resourceTO.getCostPerHour().toPlainString();
        long quantity = resourceCollectionTO.getQuantity();

        SubRoleEnum subRoleEnum = resourceTO.getSubRole();
        if (subRoleEnum != null) {
            subRole = resourceTO.getSubRole().value + ", ";
        }

        this.setInnerTitle(mainRole);
        topText.setText(subRole + "Band " + banding + "\n"
                + "£" + costPerHour + " per Hour");
        bottomText.setText(quantity + " available");

        ((SpinnerValueFactory.IntegerSpinnerValueFactory) amountField.getValueFactory()).setMax(Math.toIntExact(quantity));

        dateField.setShowWeekNumbers(false);

        ComponentUtil componentUtil = new ComponentUtil();
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {

                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            componentUtil.safeAddStyleClass(this, NEGATIVE_DATE_PICKER_DAY_CELL.value);
                        }
                    }
                };
            }
        };

        dateField.setDayCellFactory(dayCellFactory);

        saveToFileButton.setOnMouseClicked(this::saveToFileButtonPressed);
    }

    /**
     * Saves the details of the associated {@link ResourceCollectionTO} to a file.
     */
    private void saveToFileButtonPressed(MouseEvent mouseEvent) {
        StringBuilder content = new StringBuilder();
        content
                .append("Main role: ")
                .append(resourceCollectionTO.getResource().getMainRole().value);

        if (resourceCollectionTO.getResource().getSubRole() != null) {
            content
                    .append("\nSub Role: ")
                    .append(resourceCollectionTO.getResource().getSubRole().value);

        }

        content
                .append("\nBand: ")
                .append(resourceCollectionTO.getResource().getBanding().value)
                .append("\nCost Per Hour: £")
                .append(resourceCollectionTO.getResource().getCostPerHour())
                .append("\n\nAvailable copies: ")
                .append(resourceCollectionTO.getQuantity());

        saveDetailsToFile(this.getScene().getWindow(), content.toString());
    }

    /**
     * Styles the given fields as negative. This is useful for validation.
     *
     * @param fields the fields to be marked.
     */
    public void markFields(String[] fields) {
        ValidatorUtil validatorUtil = new ValidatorUtil();

        HashMap<String, Control> toFieldToControl = new HashMap<String, Control>() {{
            put("clientName", clientField);
            put("amount", amountField.getEditor());
            put("availabilityDate", dateField.getEditor());
        }};

        validatorUtil.markControlAgainstValidatedTO(fields, toFieldToControl, NEGATIVE_CONTROL.value);
    }
}
