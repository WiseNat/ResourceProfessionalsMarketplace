package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.*;
import com.wise.resource.professionals.marketplace.modules.LoanSearch;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.modules.ReturnSearch;
import com.wise.resource.professionals.marketplace.to.InvalidFieldsAndDataTO;
import com.wise.resource.professionals.marketplace.to.LoanTO;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.to.RawLoanTO;
import com.wise.resource.professionals.marketplace.util.ProjectManagerUtil;
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

import java.time.LocalDate;
import java.util.Objects;

@Component
@FxmlView("ProjectManagerView.fxml")
public class ProjectManagerController implements MainView {

    @Autowired
    private ProjectManagerUtil projectManagerUtil;

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<LoanSearch, VBox> loanSearch;
    private final FxControllerAndView<ReturnSearch, VBox> returnSearch;

    private final ListView listView;
    private NavbarButton loanNavbarButton;
    private NavbarButton returnNavbarButton;

    public ProjectManagerController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<LoanSearch, VBox> loanSearch,
            FxControllerAndView<ReturnSearch, VBox> returnSearch
    ) {
        this.mainSkeleton = mainSkeleton;
        this.listView = new ListView();
        this.loanSearch = loanSearch;
        this.returnSearch = returnSearch;

        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {

    }

    @FXML
    @SneakyThrows
    private void initialize() {
        mainSkeleton.getController().setMainContent(listView);
        mainSkeleton.getController().removeSubtitle();

        loanSearch.getController().setListView(listView);
        returnSearch.getController().setListView(listView);

        loanNavbarButton = mainSkeleton.getController().addNavbarButton(Objects.requireNonNull(getClass().getResource("../images/handshake.png")));
        returnNavbarButton = mainSkeleton.getController().addNavbarButton(Objects.requireNonNull(getClass().getResource("../images/return.png")));

        loanNavbarButton.setOnMouseClicked(this::loanNavbarButtonClicked);
        returnNavbarButton.setOnMouseClicked(this::returnNavbarButtonClicked);

        loanSearch.getController().resetFields();
        returnSearch.getController().resetFields();

        initialiseLoansView();
    }

    private void returnNavbarButtonClicked(MouseEvent mouseEvent) {
        if (!returnNavbarButton.isActive()) {
            returnNavbarButton.setActive(true);
            loanNavbarButton.setActive(false);

            initialiseReturnsView();
        }
    }

    private void loanNavbarButtonClicked(MouseEvent mouseEvent) {
        if (!loanNavbarButton.isActive()) {
            loanNavbarButton.setActive(true);
            returnNavbarButton.setActive(false);

            initialiseLoansView();
        }
    }

    private void applyMouseClickedEventToLoanableResources() {
        for (Node node : listView.getChildren()) {
            node.setOnMouseClicked(e -> loanableResourceClicked((LoanResourceListBox) node));
        }
    }

    private void applyMouseClickedEventToReturnableResources() {
        for (Node node : listView.getChildren()) {
            node.setOnMouseClicked(e -> returnableResourceClicked((ReturnResourceListBox) node));
        }
    }

    private void populatePredicateLoanables() {
        loanSearch.getController().populatePredicateLoanables();
        applyMouseClickedEventToLoanableResources();
    }

    private void populatePredicateReturnables() {
        returnSearch.getController().populatePredicateReturnables();
        applyMouseClickedEventToReturnableResources();
    }

    @SneakyThrows
    private void initialiseLoansView() {
        if (!(loanSearch.getView().isPresent())) {
            throw new IllegalAccessException("A necessary view was not found");
        }

        mainSkeleton.getController().setRightContent(loanSearch.getView().get());
        loanSearch.getView().get().setAlignment(Pos.TOP_CENTER);

        mainSkeleton.getController().setTitle("Loan Resources");

        loanNavbarButton.setActive(true);
        returnNavbarButton.setActive(false);

        loanSearch.getController().getApplyButton().setOnMouseClicked(this::loanSearchClicked);

        populatePredicateLoanables();
    }

    @SneakyThrows
    private void initialiseReturnsView() {
        if (!(returnSearch.getView().isPresent())) {
            throw new IllegalAccessException("A necessary view was not found");
        }

        mainSkeleton.getController().setRightContent(returnSearch.getView().get());
        returnSearch.getView().get().setAlignment(Pos.TOP_CENTER);

        mainSkeleton.getController().setTitle("Return a Resource");

        listView.clearAllChildren();

        loanNavbarButton.setActive(false);
        returnNavbarButton.setActive(true);

        returnSearch.getController().getApplyButton().setOnMouseClicked(this::returnSearchClicked);

        populatePredicateReturnables();
    }

    private void loanSearchClicked(MouseEvent mouseEvent) {
        populatePredicateLoanables();
    }

    private void returnSearchClicked(MouseEvent mouseEvent) {
        populatePredicateReturnables();
    }

    private void loanableResourceClicked(LoanResourceListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        LoanModal dialog = new LoanModal(listBox.getResourceCollection());
        dialog.setBlurNodes(nodes);
        dialog.getLoanButton().setOnMouseClicked(e -> this.loanButtonClicked(dialog));
        dialog.showAndWait();
    }

    private void returnableResourceClicked(ReturnResourceListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        ReturnModal dialog = new ReturnModal(listBox.getAccountEntity());
        dialog.getReturnButton().setOnMouseClicked(e -> this.returnButtonClicked(dialog));
        dialog.setBlurNodes(nodes);
        dialog.showAndWait();
    }

    private void loanButtonClicked(LoanModal loanModal) {
        String clientName = loanModal.getClientField().getText();
        Integer amount = loanModal.getAmountField().getValue();
        LocalDate availabilityDate = loanModal.getDateField().getValue();

        RawLoanTO rawLoanTO = new RawLoanTO(loanModal.getResourceCollectionTO(), clientName, amount, availabilityDate);

        InvalidFieldsAndDataTO<LoanTO> convertedTO = projectManagerUtil.createLoanTo(rawLoanTO);

        if (convertedTO.getInvalidFields().length > 0) {
            loanModal.markTextFields(convertedTO.getInvalidFields());
            return;
        }

        projectManagerUtil.loanResource(convertedTO.getData());

        populatePredicateLoanables();

        loanModal.closeDialog();
    }

    private void returnButtonClicked(ReturnModal returnModal) {
        projectManagerUtil.returnResource(returnModal.getAccountEntity().getResource());

        populatePredicateReturnables();

        returnModal.closeDialog();
    }
}
