package com.wise.resource.professionals.marketplace.controller;

import com.wise.resource.professionals.marketplace.component.NavbarButton;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.modules.MainSkeletonComponent;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@FxmlView("AdminView.fxml")
public class AdminController implements MainView  {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EnumUtil enumUtil;

    private AccountEntity accountEntity;
    private ResourceEntity resourceEntity;

    private final FxControllerAndView<MainSkeletonComponent, BorderPane> mainSkeleton;

    public AdminController(FxControllerAndView<MainSkeletonComponent, BorderPane> mainSkeleton) {
        this.mainSkeleton = mainSkeleton;
        this.mainSkeleton.getController().initialize();
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {
        this.accountEntity = accountRepository.findByEmailAndAccountType(
                logInAccountTO.getEmail(),
                enumUtil.accountTypeToEntity(logInAccountTO.getAccountType()));

        this.resourceEntity = this.accountEntity.getResource();
    }

    @FXML
    private void initialize() {
//        mainSkeleton.getController().setMainContent(updateDetails.getView().get());
//        GridPane.setHalignment(updateDetails.getView().get(), HPos.CENTER);

        mainSkeleton.getController().setTitle("Approvals");

        NavbarButton navbarButton = mainSkeleton.getController().addNavbarButton(
                Objects.requireNonNull(getClass().getResource("../images/handshake.png")));
        navbarButton.setActive(true);
    }


}
