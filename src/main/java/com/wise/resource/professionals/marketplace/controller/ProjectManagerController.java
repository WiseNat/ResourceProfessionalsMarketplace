package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.ListBox;
import com.wise.resource.professionals.marketplace.component.LoanableResourceListBox;
import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.modules.ListView;
import com.wise.resource.professionals.marketplace.modules.LoanSearch;
import com.wise.resource.professionals.marketplace.modules.MainSkeleton;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.util.LoanUtil;
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

import java.util.List;
import java.util.Objects;

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
                Objects.requireNonNull(getClass().getResource("../images/returns.png")));

        loanSearch.getController().getApplyButton().setOnMouseClicked(this::loanSearchClicked);

        populateAllLoanables();
    }

    private void loanSearchClicked(MouseEvent mouseEvent) {
        applyLoanSearch();
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
        // TODO: Create Modal
    }

    private void applyLoanSearch() {
        // TODO: This
    }
}
