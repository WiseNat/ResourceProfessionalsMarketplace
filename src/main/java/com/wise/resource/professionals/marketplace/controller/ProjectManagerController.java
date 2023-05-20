package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.*;
import com.wise.resource.professionals.marketplace.module.LoanSearch;
import com.wise.resource.professionals.marketplace.module.MainSkeleton;
import com.wise.resource.professionals.marketplace.module.ReturnSearch;
import com.wise.resource.professionals.marketplace.service.ProjectManagerService;
import com.wise.resource.professionals.marketplace.to.InvalidFieldsAndDataTO;
import com.wise.resource.professionals.marketplace.to.LoanTO;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.to.RawLoanTO;
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

/**
 * Controller class for ProjectManagerView.fxml which extends {@link MainView}
 */
@Component
@FxmlView("ProjectManagerView.fxml")
public class ProjectManagerController implements MainView {

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<LoanSearch, VBox> loanSearch;
    private final FxControllerAndView<ReturnSearch, VBox> returnSearch;
    private final ListView listView;
    @Autowired
    private ProjectManagerService projectManagerService;
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

    /**
     * Method for when the return navbar button is clicked. Shouldn't be directly called.
     * <p>
     * This changes the state of the button and swaps the view to the returns view.
     */
    private void returnNavbarButtonClicked(MouseEvent mouseEvent) {
        if (!returnNavbarButton.isActive()) {
            returnNavbarButton.setActive(true);
            loanNavbarButton.setActive(false);

            initialiseReturnsView();
        }
    }

    /**
     * Method for when the loan navbar button is clicked. Shouldn't be directly called.
     * <p>
     * This changes the state of the button and swaps the view to the loans view.
     */
    private void loanNavbarButtonClicked(MouseEvent mouseEvent) {
        if (!loanNavbarButton.isActive()) {
            loanNavbarButton.setActive(true);
            returnNavbarButton.setActive(false);

            initialiseLoansView();
        }
    }

    /**
     * Sets the mouse clicked event for all child nodes in the {@link ProjectManagerController#listView} as
     * {@link ProjectManagerController#loanableResourceClicked(LoanResourceListBox)}
     */
    private void applyMouseClickedEventToLoanableResources() {
        for (Node node : listView.getChildren()) {
            node.setOnMouseClicked(e -> loanableResourceClicked((LoanResourceListBox) node));
        }
    }

    /**
     * Sets the mouse clicked event for all child nodes in the {@link ProjectManagerController#listView} as
     * {@link ProjectManagerController#returnableResourceClicked(ReturnResourceListBox)}
     */
    private void applyMouseClickedEventToReturnableResources() {
        for (Node node : listView.getChildren()) {
            node.setOnMouseClicked(e -> returnableResourceClicked((ReturnResourceListBox) node));
        }
    }

    /**
     * Populates loanable resources using the loanable resources found using the search field predicates. This also
     * calls {@link ProjectManagerController#applyMouseClickedEventToLoanableResources()}.
     */
    private void populatePredicateLoanables() {
        loanSearch.getController().populatePredicateLoanables();
        applyMouseClickedEventToLoanableResources();
    }

    /**
     * Populates returnable resources using the returnable resources found using the search field predicates. This also
     * calls {@link ProjectManagerController#applyMouseClickedEventToReturnableResources()}.
     */
    private void populatePredicateReturnables() {
        returnSearch.getController().populatePredicateReturnables();
        applyMouseClickedEventToReturnableResources();
    }

    /**
     * Initialises the loans subview
     */
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

    /**
     * Initialises the returns subview
     */
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

    /**
     * Method for when the loan search button is clicked. Shouldn't be directly called.
     */
    private void loanSearchClicked(MouseEvent mouseEvent) {
        populatePredicateLoanables();
    }

    /**
     * Method for when the return search button is clicked. Shouldn't be directly called.
     */
    private void returnSearchClicked(MouseEvent mouseEvent) {
        populatePredicateReturnables();
    }

    /**
     * Method for when a {@link LoanResourceListBox} is clicked. Shouldn't be directly called.
     * This creates a new {@link LoanModal} and then shows it.
     */
    private void loanableResourceClicked(LoanResourceListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        LoanModal dialog = new LoanModal(listBox.getResourceCollection());
        dialog.setBlurNodes(nodes);
        dialog.getLoanButton().setOnMouseClicked(e -> this.loanButtonClicked(dialog));
        dialog.showAndWait();
    }

    /**
     * Method for when a {@link ReturnResourceListBox} is clicked. Shouldn't be directly called.
     * This creates a new {@link ReturnModal} and then shows it.
     */
    private void returnableResourceClicked(ReturnResourceListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        ReturnModal dialog = new ReturnModal(listBox.getAccountEntity());
        dialog.getReturnButton().setOnMouseClicked(e -> this.returnButtonClicked(dialog));
        dialog.setBlurNodes(nodes);
        dialog.showAndWait();
    }

    /**
     * Method for when the loan button is clicked within a {@link LoanModal}.
     * <p>
     * This populates a {@link RawLoanTO} with the user inputs and validates this. If it is invalid, the invalid fields
     * are marked; otherwise, the resources are loaned, the loanable resources are refreshed and the modal is closed.
     */
    private void loanButtonClicked(LoanModal loanModal) {
        String clientName = loanModal.getClientField().getText();
        Integer amount = loanModal.getAmountField().getValue();
        LocalDate availabilityDate = loanModal.getDateField().getValue();

        RawLoanTO rawLoanTO = new RawLoanTO(loanModal.getResourceCollectionTO(), clientName, amount, availabilityDate);

        InvalidFieldsAndDataTO<LoanTO> convertedTO = projectManagerService.createLoanTo(rawLoanTO);

        if (convertedTO.getInvalidFields().length > 0) {
            loanModal.markFields(convertedTO.getInvalidFields());
            return;
        }

        projectManagerService.loanResource(convertedTO.getData());

        populatePredicateLoanables();

        loanModal.closeModal();
    }

    /**
     * Method for when the return button is clicked within a {@link LoanModal}.
     * <p>
     * This returns the resource from the {@link com.wise.resource.professionals.marketplace.entity.ResourceEntity} in
     * {@link ReturnModal#accountEntity}. The returnable resources are refreshed and the modal is closed.
     */
    private void returnButtonClicked(ReturnModal returnModal) {
        projectManagerService.returnResource(returnModal.getAccountEntity().getResource());

        populatePredicateReturnables();

        returnModal.closeModal();
    }
}
