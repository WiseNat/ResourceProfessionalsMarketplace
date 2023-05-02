package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.Modal;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.modules.Approvals;
import com.wise.resource.professionals.marketplace.modules.ApprovalsSearch;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@FxmlView("AdminView.fxml")
public class AdminController implements MainView {


    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private EnumUtil enumUtil;

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<Approvals, VBox> approvals;
    private final FxControllerAndView<ApprovalsSearch, VBox> approvalsSearch;


    public AdminController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<Approvals, VBox> approvals,
            FxControllerAndView<ApprovalsSearch, VBox> approvalsSearch) {
        this.mainSkeleton = mainSkeleton;
        this.approvals = approvals;
        this.approvalsSearch = approvalsSearch;

        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {
    }

    @FXML
    @SneakyThrows
    private void initialize() {
        if (!(approvals.getView().isPresent() && approvalsSearch.getView().isPresent())) {
            throw new IllegalAccessException("A necessary view was not found");
        }

        mainSkeleton.getController().setMainContent(approvals.getView().get());

        mainSkeleton.getController().setRightContent(approvalsSearch.getView().get());
        approvalsSearch.getView().get().setAlignment(Pos.TOP_CENTER);

        mainSkeleton.getController().setTitle("Approvals");
        mainSkeleton.getController().removeSubtitle();

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/handshake.png")));
        navbarButton.setActive(true);

        approvalsSearch.getController().getApplyButton().setOnMouseClicked(this::applySearch);

        populateAllApprovals();
    }

    private void populateAllApprovals() {
        List<ApprovalEntity> pendingApprovals = approvalRepository.findAll();
        populateApprovals(pendingApprovals);
    }

    private void populateApprovals(List<ApprovalEntity> pendingApprovals) {
        approvals.getController().clearAllApprovals();

        approvalsSearch.getController().getTitle().setText(pendingApprovals.size() + " approval requests found");


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
        listBox.removeImage();

        listBox.setOnMouseClicked(this::approvalClicked);

        return listBox;
    }

    private void approvalClicked(MouseEvent mouseEvent) {
        // TODO: Custom Modal class extending Dialog
        Modal<String> dialog = new Modal<>();
        dialog.showAndWait();
    }

    private void applySearch(MouseEvent mouseEvent) {
        ApprovalsSearch controller = approvalsSearch.getController();

        String firstName = controller.getFirstNameField().getText();
        String lastName = controller.getLastNameField().getText();
        String email = controller.getEmailField().getText();
        boolean isResourceAllowed = controller.getResourceBox().isSelected();
        boolean isProjectManagerAllowed = controller.getProjectManagerBox().isSelected();

        List<ApprovalEntity> foundApprovals;

        if (isResourceAllowed && isProjectManagerAllowed) {
            foundApprovals = approvalRepository.findAllApprovalsByPredicates(firstName, lastName, email);
        } else if (isProjectManagerAllowed) {
            AccountTypeEntity accountType = enumUtil.accountTypeToEntity(AccountTypeEnum.ProjectManager);
            foundApprovals = approvalRepository.findApprovalsByPredicatesAndAccountType(firstName, lastName, email, accountType);
        } else if (isResourceAllowed) {
            AccountTypeEntity accountType = enumUtil.accountTypeToEntity(AccountTypeEnum.Resource);
            foundApprovals = approvalRepository.findApprovalsByPredicatesAndAccountType(firstName, lastName, email, accountType);
        } else {
            foundApprovals = new ArrayList<>();
        }

        populateApprovals(foundApprovals);
    }


}
