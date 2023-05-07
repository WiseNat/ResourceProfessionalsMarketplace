package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import lombok.Getter;
import lombok.SneakyThrows;

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
    private Button loanButton;

    @SneakyThrows
    public LoanModal(ResourceCollectionTO resourceCollectionTO) {
        super();

        this.resourceCollectionTO = resourceCollectionTO;

        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/LoanModalLeft.fxml"));
        fxmlLoader.setController(this);
        Node leftContainer = fxmlLoader.load();
        setLeftContent(leftContainer);

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/LoanModalRight.fxml"));
        fxmlLoader.setController(this);
        Node rightContainer = fxmlLoader.load();
        setRightContent(rightContainer);

        init();
    }

    private void init() {
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
    }

}
