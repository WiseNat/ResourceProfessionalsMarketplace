package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.component.MainSkeleton;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.to.LogInAccountTO;
import com.wise.ResourceProfessionalsMarketplace.util.EnumUtil;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.SneakyThrows;
import net.rgielen.fxweaver.core.FxControllerAndView;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@FxmlView("ResourceView.fxml")
public class ResourceController implements MainView{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EnumUtil enumUtil;


    private final FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton;
    private final FxControllerAndView<UpdateDetails, VBox> updateDetails;
    private LogInAccountTO accountTO;

    public ResourceController(
            FxControllerAndView<MainSkeleton, BorderPane> mainSkeleton,
            FxControllerAndView<UpdateDetails, VBox> updateDetails) {
        this.mainSkeleton = mainSkeleton;
        this.updateDetails = updateDetails;

        this.mainSkeleton.getController().initialize();
    }

    @FXML
    @SneakyThrows
    public void initialize() {
        if (!updateDetails.getView().isPresent()) {
            throw new IllegalAccessException("The view for updateDetails was not found");
        }


        // TODO: Figure out which line is better...
        //mainSkeleton.getController().setMainContent(stageHandler.getScene(UpdateDetails.class).getRoot());
        //mainSkeleton.getController().setMainContent(fxWeaver.loadView(UpdateDetails.class));
        mainSkeleton.getController().setMainContent(updateDetails.getView().get());
    }

    @Override
    public void setAccountTO(LogInAccountTO logInAccountTO) {
        this.accountTO = logInAccountTO;

        AccountEntity accountEntity = accountRepository.findByEmailAndAccountType(
                accountTO.getEmail(),
                enumUtil.accountTypeToEntity(accountTO.getAccountType()));

        String name = accountEntity.getFirstName() + " " + accountEntity.getLastName();
        mainSkeleton.getController().setTitle("Hi " + name, "You can change your details here");
    }
}
