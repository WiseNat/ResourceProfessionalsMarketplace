package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.ApprovalListBox;
import com.wise.resource.professionals.marketplace.component.ApprovalModal;
import com.wise.resource.professionals.marketplace.component.ListView;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.modules.ApprovalsSearch;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.service.AdminService;
import com.wise.resource.professionals.marketplace.to.ApprovalSearchTO;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@FxmlView("AdminView.fxml")
public class AdminController implements MainView {

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final ListView listView;
    private final FxControllerAndView<ApprovalsSearch, VBox> approvalsSearch;
    @Autowired
    private AdminService adminService;

    public AdminController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<ApprovalsSearch, VBox> approvalsSearch) {
        this.mainSkeleton = mainSkeleton;
        this.approvalsSearch = approvalsSearch;
        this.listView = new ListView();

        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {
    }

    @FXML
    @SneakyThrows
    private void initialize() {
        if (!(approvalsSearch.getView().isPresent())) {
            throw new IllegalAccessException("A necessary view was not found");
        }

        mainSkeleton.getController().setMainContent(listView);

        mainSkeleton.getController().setRightContent(approvalsSearch.getView().get());
        approvalsSearch.getView().get().setAlignment(Pos.TOP_CENTER);

        mainSkeleton.getController().setTitle("Approvals");
        mainSkeleton.getController().removeSubtitle();

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/approval.png")));
        navbarButton.setActive(true);

        approvalsSearch.getController().getApplyButton().setOnMouseClicked(this::searchClicked);

        populatePredicateApprovals();
    }

    private void searchClicked(MouseEvent mouseEvent) {
        populatePredicateApprovals();
    }

    private void populatePredicateApprovals() {
        ApprovalsSearch controller = approvalsSearch.getController();

        String firstName = controller.getFirstNameField().getText();
        String lastName = controller.getLastNameField().getText();
        String email = controller.getEmailField().getText();
        boolean isResourceAllowed = controller.getResourceBox().isSelected();
        boolean isProjectManagerAllowed = controller.getProjectManagerBox().isSelected();

        ApprovalSearchTO approvalSearchTO = new ApprovalSearchTO(
                isResourceAllowed, isProjectManagerAllowed, firstName, lastName, email);

        List<ApprovalEntity> foundApprovals = adminService.getApprovals(approvalSearchTO);

        populateApprovals(foundApprovals);
    }

    private void populateApprovals(List<ApprovalEntity> pendingApprovals) {
        listView.clearAllChildren();

        approvalsSearch.getController().getTitle().setText(pendingApprovals.size() + " approval requests found");

        for (ApprovalEntity pendingApproval : pendingApprovals) {
            ApprovalListBox approvalListBox = new ApprovalListBox(pendingApproval);
            approvalListBox.setOnMouseClicked(e -> approvalClicked(approvalListBox));

            listView.addChild(approvalListBox);
        }
    }

    private void approvalClicked(ApprovalListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        ApprovalModal dialog = new ApprovalModal(listBox.getApproval());
        dialog.setBlurNodes(nodes);
        dialog.getApproveButton().setOnMouseClicked(e -> this.approveButtonClicked(dialog));
        dialog.getDenyButton().setOnMouseClicked(e -> this.denyButtonClicked(dialog));
        dialog.showAndWait();
    }

    private void approveButtonClicked(ApprovalModal approvalModal) {
        adminService.approveApproval(approvalModal.getApproval());

        populatePredicateApprovals();

        approvalModal.closeModal();
    }

    private void denyButtonClicked(ApprovalModal approvalModal) {
        adminService.denyApproval(approvalModal.getApproval());

        populatePredicateApprovals();

        approvalModal.closeModal();
    }

}
