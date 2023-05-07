package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.LoanModal;
import com.wise.resource.professionals.marketplace.component.LoanableResourceListBox;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.modules.ListView;
import com.wise.resource.professionals.marketplace.modules.LoanSearch;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LoanSearchTO;
import com.wise.resource.professionals.marketplace.to.LoanTO;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
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
    private final FxControllerAndView<ListView, VBox> listView;
    private final FxControllerAndView<LoanSearch, VBox> loanSearch;
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
            FxControllerAndView<ListView, VBox> listView,
            FxControllerAndView<LoanSearch, VBox> loanSearch) {
        this.mainSkeleton = mainSkeleton;
        this.listView = listView;
        this.loanSearch = loanSearch;

        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {

    }

    @FXML
    @SneakyThrows
    private void initialize() {
        initialiseLoansView();
    }

    @SneakyThrows
    private void initialiseLoansView() {
        if (!(listView.getView().isPresent() && loanSearch.getView().isPresent())) {
            throw new IllegalAccessException("A necessary view was not found");
        }

        mainSkeleton.getController().setMainContent(listView.getView().get());

        mainSkeleton.getController().setRightContent(loanSearch.getView().get());
        loanSearch.getView().get().setAlignment(Pos.TOP_CENTER);

        mainSkeleton.getController().setTitle("Loan an Individual");
        mainSkeleton.getController().removeSubtitle();

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/handshake.png")));
        navbarButton.setActive(true);

        mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/return.png")));

        loanSearch.getController().getApplyButton().setOnMouseClicked(this::loanSearchClicked);

        populateAllLoanables();
    }

    private void loanSearchClicked(MouseEvent mouseEvent) {
        populatePredicateLoanables();
    }

    private void populatePredicateLoanables() {
        BandingEnum banding = BandingEnum.valueToEnum(loanSearch.getController().getBandField().getValue());
        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(loanSearch.getController().getMainRoleField().getValue());

        String subRoleString = loanSearch.getController().getSubRoleField().getValue();
        SubRoleEnum subRole = null;
        if (subRoleString != null) {
            subRole = SubRoleEnum.valueToEnum(subRoleString);
        }

        String costPerHourString = loanSearch.getController().getCostPerHourField().getText();
        BigDecimal costPerHour = null;
        if (!costPerHourString.isEmpty()) {
            try {
                costPerHour = new BigDecimal(loanSearch.getController().getCostPerHourField().getText());
            } catch (NumberFormatException e) {
                validatorUtil.markControlNegative(loanSearch.getController().getCostPerHourField(), "negative-control");
                return;
            }
        }

        LoanSearchTO loanSearchTO = new LoanSearchTO();
        loanSearchTO.setBanding(banding);
        loanSearchTO.setMainRole(mainRole);
        loanSearchTO.setSubRole(subRole);
        loanSearchTO.setCostPerHour(costPerHour);

        applyLoanSearch(loanSearchTO);
    }

    private void populateAllLoanables() {
        List<ResourceRepository.IResourceCollection> rawResourceCollections = resourceRepository.findAllByCollection();
        List<ResourceCollectionTO> resourceCollections = loanUtil.iResourceCollectionToResourceCollectionTO(rawResourceCollections);

        populateLoanables(resourceCollections);
    }

    private void populateLoanables(List<ResourceCollectionTO> resourceCollections) {
        listView.getController().clearAllChildren();

        int totalResources = 0;

        for (ResourceCollectionTO resourceCollection : resourceCollections) {
            totalResources += resourceCollection.getQuantity();

            ListBox approval = createLoanableResourceListBox(resourceCollection);
            listView.getController().addChild(approval);
        }

        loanSearch.getController().getTitle().setText(
                resourceCollections.size() + " collections found\n" + totalResources + " loanable resources found");
    }

    private LoanableResourceListBox createLoanableResourceListBox(ResourceCollectionTO resourceCollection) {
        LoanableResourceListBox listBox = new LoanableResourceListBox(resourceCollection);
        listBox.setOnMouseClicked(e -> loanableResourceClicked(listBox));

        return listBox;
    }

    private void loanableResourceClicked(LoanableResourceListBox listBox) {
        Node[] nodes = new Node[]{mainSkeleton.getController().getScrollpane().getScene().getRoot()};

        LoanModal dialog = new LoanModal(listBox.getResourceCollection());
        dialog.setBlurNodes(nodes);
        dialog.getLoanButton().setOnMouseClicked(e -> this.loanButtonClicked(dialog));
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

        populatePredicateLoanables();

        loanModal.closeDialog();
    }

    private void applyLoanSearch(LoanSearchTO loanSearchTO) {
        List<ResourceRepository.IResourceCollection> foundLoanables = resourceRepository.findAllByCollectionWithPredicates(
                enumUtil.bandingToEntity(loanSearchTO.getBanding()),
                enumUtil.mainRoleToEntity(loanSearchTO.getMainRole()),
                enumUtil.subRoleToEntity(loanSearchTO.getSubRole()),
                loanSearchTO.getCostPerHour()
        );

        List<ResourceCollectionTO> resourceCollections = loanUtil.iResourceCollectionToResourceCollectionTO(foundLoanables);

        populateLoanables(resourceCollections);
    }
}
