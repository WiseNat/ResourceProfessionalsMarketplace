package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;

/**
 * An extension of {@link Modal} which provides automatic initialisation specific to approvals. It also stores the
 * given {@link ApprovalEntity} for later use.
 */
@Getter
public class ApprovalModal extends Modal {


    private final ApprovalEntity approval;

    @FXML
    private Label topText;

    @FXML
    private Label bottomText;

    @FXML
    private Button approveButton;

    @FXML
    private Button denyButton;

    /**
     * Loads in the appropriate FXMLs needed for both the {@link Modal#setLeftContent(Node)} and
     * {@link Modal#setRightContent(Node)} methods.
     *
     * @param approval the associated approval for this {@code ApprovalModal}
     */
    @SneakyThrows
    public ApprovalModal(ApprovalEntity approval) {
        super();

        this.approval = approval;

        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource("../module/ApprovalModalLeft.fxml"));
        fxmlLoader.setController(this);
        Node leftContainer = fxmlLoader.load();
        setLeftContent(leftContainer);

        fxmlLoader = new FXMLLoader(getClass().getResource("../module/ApprovalModalRight.fxml"));
        fxmlLoader.setController(this);
        Node rightContainer = fxmlLoader.load();
        setRightContent(rightContainer);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy @ HH:mm");

        String accountType = approval.getAccount().getAccountType().getName();
        String name = approval.getAccount().getFirstName() + " " + approval.getAccount().getLastName();
        String email = approval.getAccount().getEmail();
        String date = dateFormat.format(approval.getDate());

        this.setInnerTitle(accountType + "\nAccount Creation");
        topText.setText(name + "\n" + email);
        bottomText.setText("Request made at:\n" + date);
    }
}
