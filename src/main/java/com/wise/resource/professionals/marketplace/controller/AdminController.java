package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.ApprovalListBox;
import com.wise.resource.professionals.marketplace.component.ApprovalModal;
import com.wise.resource.professionals.marketplace.component.ListView;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.module.ApprovalsSearch;
import com.wise.resource.professionals.marketplace.module.MainSkeleton;
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

/**
 * Controller class for AdminView.fxml which extends {@link MainView}
 */
@Component
@FxmlView("AdminView.fxml")
public class AdminController implements MainView {

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<ApprovalsSearch, VBox> approvalsSearch;
    private final ListView listView;
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

    /**
     * Method for when the search button is clicked. Shouldn't be directly called.
     * <p>
     * Calls {@link AdminController#populatePredicateApprovals()}
     */
    private void searchClicked(MouseEvent mouseEvent) {
        populatePredicateApprovals();
    }


    /**
     * Populates approvals using the approvals found using the search field predicates.
     */
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

    /**
     * Populates the {@link AdminController#listView} with multiple {@link ApprovalListBox} which are created from the
     * given list of {@link ApprovalEntity}.
     *
     * @param pendingApprovals each {@link ApprovalEntity} in this list is used to create an individual
     *                         {@link ApprovalListBox}
     */
    private void populateApprovals(List<ApprovalEntity> pendingApprovals) {
        listView.clearAllChildren();

        approvalsSearch.getController().getTitle().setText(pendingApprovals.size() + " approval requests found");

        for (ApprovalEntity pendingApproval : pendingApprovals) {
            ApprovalListBox approvalListBox = new ApprovalListBox(pendingApproval);
            approvalListBox.setOnMouseClicked(e -> approvalClicked(approvalListBox));

            listView.addChild(approvalListBox);
        }
    }

    /**
     * Method for when an {@link ApprovalListBox} is clicked. This creates an {@link ApprovalModal} and calls
     * {@link ApprovalModal#showAndWait()}
     *
     * @param listBox the {@link ApprovalListBox} which was clicked
     */
    private void approvalClicked(ApprovalListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        ApprovalModal dialog = new ApprovalModal(listBox.getApproval());
        dialog.setBlurNodes(nodes);
        dialog.getApproveButton().setOnMouseClicked(e -> this.approveButtonClicked(dialog));
        dialog.getDenyButton().setOnMouseClicked(e -> this.denyButtonClicked(dialog));
        dialog.showAndWait();
    }

    /**
     * Method for when an {@link ApprovalModal#approveButton} is clicked. This calls
     * {@link AdminService#approveApproval(ApprovalEntity)}, refreshes the list of approvals, and closes the modal.
     *
     * @param approvalModal the {@link ApprovalModal} which contains the approve button that was clicked
     */
    private void approveButtonClicked(ApprovalModal approvalModal) {
        adminService.approveApproval(approvalModal.getApproval());

        populatePredicateApprovals();

        approvalModal.closeModal();
    }

    /**
     * Method for when an {@link ApprovalModal#denyButton} is clicked. This calls
     * {@link AdminService#denyApproval(ApprovalEntity)}, refreshes the list of approvals, and closes the modal.
     *
     * @param approvalModal the {@link ApprovalModal} which contains the deny button that was clicked
     */
    private void denyButtonClicked(ApprovalModal approvalModal) {
        adminService.denyApproval(approvalModal.getApproval());

        populatePredicateApprovals();

        approvalModal.closeModal();
    }

}
