package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.util.ComponentUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import lombok.Getter;
import lombok.SneakyThrows;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.wise.resource.professionals.marketplace.constant.StyleEnum.NEGATIVE_LABEL;

@Getter
public class ReturnModal extends Modal {

    private final AccountEntity accountEntity;

    @FXML
    private Label topText;

    @FXML
    private Label middleText;

    @FXML
    private Label bottomText;

    @FXML
    private Button returnButton;

    @FXML
    private Button saveToFileButton;

    @SneakyThrows
    public ReturnModal(AccountEntity accountEntity) {
        super();

        this.accountEntity = accountEntity;

        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ReturnModalLeft.fxml"));
        fxmlLoader.setController(this);
        Node leftContainer = fxmlLoader.load();
        setLeftContent(leftContainer);

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ReturnModalRight.fxml"));
        fxmlLoader.setController(this);
        Node rightContainer = fxmlLoader.load();
        setRightContent(rightContainer);

        init();
    }

    private void init() {
        ComponentUtil componentUtil = new ComponentUtil();

        String name = accountEntity.getFirstName() + " " + accountEntity.getLastName();
        String band = accountEntity.getResource().getBanding().getName();
        String costPerHour = "£" + accountEntity.getResource().getCostPerHour().toPlainString() + " per Hour";
        String client = accountEntity.getResource().getLoanedClient();
        String role = accountEntity.getResource().getMainRole().getName();
        BigDecimal dailyLateFee = accountEntity.getResource().getDailyLateFee();

        SubRoleEntity subRole = accountEntity.getResource().getSubRole();
        if (subRole != null) {
            role += ", " + subRole.getName();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date dueDate = accountEntity.getResource().getAvailabilityDate();
        String dueDateString = dateFormat.format(accountEntity.getResource().getAvailabilityDate());
        Date today = new Date(System.currentTimeMillis());

        if (dueDate.before(today)) {
            BigDecimal daysLate = new BigDecimal(ChronoUnit.DAYS.between(dueDate.toInstant(), today.toInstant()));
            BigDecimal lateFee = dailyLateFee.multiply(daysLate);

            middleText.setText("Loaned to " + client);
            bottomText.setText("Overdue since " + dueDateString + "\nLate fee for " + client + " is £"
                    + componentUtil.formatBigDecimal(lateFee));

            componentUtil.safeAddStyleClass(bottomText, NEGATIVE_LABEL.value);
        } else {
            componentUtil.removeNode(middleText);
            bottomText.setText("Loaned to " + client + "\nAvailable " + dueDateString);

            returnButton.setText("Return early");
        }

        this.setInnerTitle(name);
        topText.setText(role + "\n" + band + "\n" + costPerHour + "\nDaily late fee £" +
                componentUtil.formatBigDecimal(dailyLateFee));

        saveToFileButton.setOnMouseClicked(this::saveToFileButtonPressed);
    }

    private void saveToFileButtonPressed(MouseEvent mouseEvent) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        StringBuilder content = new StringBuilder();
        content
                .append("Name: ")
                .append(accountEntity.getFirstName())
                .append(" ")
                .append(accountEntity.getLastName())
                .append("\nMain role: ")
                .append(accountEntity.getResource().getMainRole().getName());

        if (accountEntity.getResource().getSubRole() != null) {
            content
                    .append("\nSub role: ")
                    .append(accountEntity.getResource().getSubRole().getName());
        }

        content
                .append("\nBand: ")
                .append(accountEntity.getResource().getBanding().getName())
                .append("\nCost Per Hour: £")
                .append(accountEntity.getResource().getCostPerHour())
                .append("\nDaily late fee: £")
                .append(accountEntity.getResource().getDailyLateFee())
                .append("\n\nLoaned to Client: ")
                .append(accountEntity.getResource().getLoanedClient())
                .append("\nAvailability date: ")
                .append(dateFormat.format(accountEntity.getResource().getAvailabilityDate()));

        saveDetailsToFile(this.getScene().getWindow(), content.toString());
    }
}
