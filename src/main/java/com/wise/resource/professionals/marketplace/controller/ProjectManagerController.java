package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.*;
import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.modules.LoanSearch;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.modules.ReturnSearch;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LoanTO;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.LoanUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.*;

@Component
@FxmlView("ProjectManagerView.fxml")
public class ProjectManagerController implements MainView {

    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<LoanSearch, VBox> loanSearch;
    private final FxControllerAndView<ReturnSearch, VBox> returnSearch;

    private final ListView listView;
    private NavbarButton loanNavbarButton;
    private NavbarButton returnNavbarButton;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private LoanUtil loanUtil;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private Validator validator;

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
        loanSearch.getController().populateAllLoanables();

        applyMouseClickedEventToLoanableResources();
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
        returnSearch.getController().populateAllReturnables();

        applyMouseClickedEventToReturnableResources();
    }

    private void loanSearchClicked(MouseEvent mouseEvent) {
        loanSearch.getController().populatePredicateLoanables();

        applyMouseClickedEventToLoanableResources();
    }

    private void returnSearchClicked(MouseEvent mouseEvent) {
        returnSearch.getController().populatePredicateReturnables();

        applyMouseClickedEventToReturnableResources();
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
        LoanTO loanTO = new LoanTO();
        loanTO.setAmount(loanModal.getAmountField().getValue());
        loanTO.setClientName(loanModal.getClientField().getText());

        Set<ConstraintViolation<LoanTO>> violations = validator.validate(loanTO);
        HashMap<String, Control> toFieldToControl = new HashMap<String, Control>() {{
            put("clientName", loanModal.getClientField());
            put("amount", loanModal.getAmountField());
        }};

        validatorUtil.markControlAgainstValidatedTO(violations, toFieldToControl, "negative-control");

        if (violations.size() > 0) {
            return;
        }

        BandingEntity banding = enumUtil.bandingToEntity(loanModal.getResourceCollectionTO().getResource().getBanding());
        MainRoleEntity mainRole = enumUtil.mainRoleToEntity(loanModal.getResourceCollectionTO().getResource().getMainRole());
        SubRoleEntity subRole = enumUtil.subRoleToEntity(loanModal.getResourceCollectionTO().getResource().getSubRole());
        BigDecimal costPerHour = loanModal.getResourceCollectionTO().getResource().getCostPerHour();

        List<ResourceEntity> resources = resourceRepository.findByBandingAndMainRoleAndSubRoleAndCostPerHour(
                banding, mainRole, subRole, costPerHour);

        Collections.shuffle(resources);

        List<ResourceEntity> selectedResources = resources.subList(0, loanModal.getAmountField().getValue());

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        cal.add(Calendar.YEAR, 1);
        Date availabilityDate = cal.getTime();

        String client = loanModal.getClientField().getText();

        for (ResourceEntity resourceEntity : selectedResources) {
            resourceEntity.setAvailabilityDate(availabilityDate);
            resourceEntity.setLoanedClient(client);

            resourceRepository.save(resourceEntity);
        }

        loanSearch.getController().populatePredicateLoanables();

        applyMouseClickedEventToLoanableResources();

        loanModal.closeDialog();
    }

    private void returnButtonClicked(ReturnModal returnModal) {
        ResourceEntity resourceEntity = returnModal.getAccountEntity().getResource();
        resourceEntity.setLoanedClient(null);
        resourceEntity.setAvailabilityDate(null);

        resourceRepository.save(resourceEntity);

        returnSearch.getController().populatePredicateReturnables();

        applyMouseClickedEventToReturnableResources();

        returnModal.closeDialog();
    }

}
