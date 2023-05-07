package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.ApprovalListBox;
import com.wise.resource.professionals.marketplace.component.ApprovalModal;
import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.modules.ApprovalsSearch;
import com.wise.resource.professionals.marketplace.modules.ListView;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@FxmlView("AdminView.fxml")
public class AdminController implements MainView {


    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<ListView, VBox> listView;
    private final FxControllerAndView<ApprovalsSearch, VBox> approvalsSearch;
    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private EnumUtil enumUtil;


    public AdminController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<ListView, VBox> listView,
            FxControllerAndView<ApprovalsSearch, VBox> approvalsSearch) {
        this.mainSkeleton = mainSkeleton;
        this.listView = listView;
        this.approvalsSearch = approvalsSearch;

        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {
    }

    @FXML
    @SneakyThrows
    private void initialize() {
        if (!(listView.getView().isPresent() && approvalsSearch.getView().isPresent())) {
            throw new IllegalAccessException("A necessary view was not found");
        }

        mainSkeleton.getController().setMainContent(listView.getView().get());

        mainSkeleton.getController().setRightContent(approvalsSearch.getView().get());
        approvalsSearch.getView().get().setAlignment(Pos.TOP_CENTER);

        mainSkeleton.getController().setTitle("Approvals");
        mainSkeleton.getController().removeSubtitle();

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/approval.png")));
        navbarButton.setActive(true);

        approvalsSearch.getController().getApplyButton().setOnMouseClicked(this::searchClicked);

        populateAllApprovals();
    }

    private void searchClicked(MouseEvent mouseEvent) {
        applySearch();
    }

    private void populateAllApprovals() {
        List<ApprovalEntity> pendingApprovals = approvalRepository.findAll();
        populateApprovals(pendingApprovals);
    }

    private void populateApprovals(List<ApprovalEntity> pendingApprovals) {
        listView.getController().clearAllChildren();

        approvalsSearch.getController().getTitle().setText(pendingApprovals.size() + " approval requests found");

        for (ApprovalEntity pendingApproval : pendingApprovals) {
            ListBox approval = createApprovalListBox(pendingApproval);
            listView.getController().addChild(approval);
        }
    }

    private ApprovalListBox createApprovalListBox(ApprovalEntity approval) {
        ApprovalListBox listBox = new ApprovalListBox(approval);
        listBox.setOnMouseClicked(e -> approvalClicked(listBox));

        return listBox;
    }

    private void approvalClicked(ApprovalListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        ApprovalModal dialog = new ApprovalModal(listBox.getApproval());
        dialog.setBlurNodes(nodes);
        dialog.getApproveButton().setOnMouseClicked(e -> this.approveButtonClicked(dialog));
        dialog.getDenyButton().setOnMouseClicked(e -> this.denyButtonClicked(dialog));
        dialog.showAndWait();
    }

    private void applySearch() {
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

    private void denyButtonClicked(ApprovalModal approvalModal) {
        AccountEntity account = approvalModal.getApproval().getAccount();

        approvalRepository.delete(approvalModal.getApproval());
        accountRepository.delete(account);

        applySearch();

        approvalModal.closeDialog();
    }

    private void approveButtonClicked(ApprovalModal approvalModal) {
        AccountEntity account = approvalModal.getApproval().getAccount();
        account.setIsApproved(true);

        if (AccountTypeEnum.valueToEnum(account.getAccountType().getName()) == AccountTypeEnum.Resource) {
            ResourceTO resourceTO = new ResourceTO();
            resourceTO.setLoanedClient(null);
            resourceTO.setDailyLateFee(100.0);
            resourceTO.setCostPerHour(new BigDecimal("10.0"));
            resourceTO.setAvailabilityDate(null);

            ResourceEntity resourceEntity = new ResourceEntity();
            BeanUtils.copyProperties(resourceTO, resourceEntity, "banding, subrole, mainRole");

            resourceEntity.setMainRole(enumUtil.mainRoleToEntity(MainRoleEnum.BusinessAnalyst));
            resourceEntity.setBanding(enumUtil.bandingToEntity(BandingEnum.BandOne));

            resourceRepository.save(resourceEntity);

            account.setResource(resourceEntity);
        }

        approvalRepository.delete(approvalModal.getApproval());
        accountRepository.save(account);

        applySearch();

        approvalModal.closeDialog();
    }


}
