package com.wise.ResourceProfessionalsMarketplace.controller;

import com.wise.ResourceProfessionalsMarketplace.component.MainSkeleton;
import javafx.fxml.FXML;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;

@Component
@FxmlView("ResourceView.fxml")
public class ResourceController {
    @FXML
    private MainSkeleton skeleton;

    @FXML
    public void initialize() {
//        skeleton.get
    }
}
