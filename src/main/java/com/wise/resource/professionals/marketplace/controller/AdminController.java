package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.modules.Approvals;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Component
@FxmlView("AdminView.fxml")
public class AdminController implements MainView  {


    @Autowired
    ApprovalRepository approvalRepository;

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<Approvals, VBox> approvals;


    public AdminController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<Approvals, VBox> approvals) {
        this.mainSkeleton = mainSkeleton;
        this.approvals = approvals;

        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {
    }

    @FXML
    @SneakyThrows
    private void initialize() {
        if (!approvals.getView().isPresent()) {
            throw new IllegalAccessException("The view for updateDetails was not found");
        }

        mainSkeleton.getController().setMainContent(approvals.getView().get());
        GridPane.setHalignment(approvals.getView().get(), HPos.CENTER);
        GridPane.setValignment(approvals.getView().get(), VPos.TOP);

        mainSkeleton.getController().setTitle("Approvals");

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/handshake.png")));
        navbarButton.setActive(true);

        populateApprovals();
    }

    private void populateApprovals() {
        approvals.getController().clearAllApprovals();

        List<ApprovalEntity> pendingApprovals = approvalRepository.findAll();

        for (ApprovalEntity pendingApproval : pendingApprovals) {
            ListBox approval = createApprovalListBox(pendingApproval);
            approvals.getController().addApproval(approval);
        }
    }

    private ListBox createApprovalListBox(ApprovalEntity approval) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy @ HH:mm");

        String accountType = approval.getAccount().getAccountType().getName();
        String name = approval.getAccount().getFirstName() + " " + approval.getAccount().getLastName();
        String email = approval.getAccount().getEmail();
        String date = dateFormat.format(approval.getDate());

        ListBox listBox = new ListBox();
        listBox.setTitleText(accountType + " Account Creation");
        listBox.setLeftSubtext(name + "\n" + email);
        listBox.setRightSubtext(date);

        return listBox;
    }


}
