package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;

@Getter
public class ApprovalModal extends Modal {


    private final ApprovalEntity approval;
    @FXML
    Label topText;
    @FXML
    Label bottomText;
    @FXML
    Button approveButton;
    @FXML
    Button denyButton;
    private ApprovalRepository approvalRepository;
    private AccountRepository accountRepository;

    @SneakyThrows
    public ApprovalModal(ApprovalEntity approval) {
        super();

        this.approval = approval;

        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ApprovalModalLeft.fxml"));
        fxmlLoader.setController(this);
        Node leftContainer = fxmlLoader.load();
        setLeftContent(leftContainer);

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ApprovalModalRight.fxml"));
        fxmlLoader.setController(this);
        Node rightContainer = fxmlLoader.load();
        setRightContent(rightContainer);

        init();
    }

    private void init() {
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
