package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import lombok.SneakyThrows;

public class ApprovalModal<T> extends Modal<T> {

    @SneakyThrows
    public ApprovalModal() {
        super();

        FXMLLoader fxmlLoader;

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ApprovalModalLeft.fxml"));
        Node leftContainer = fxmlLoader.load();
        setLeftContent(leftContainer);

        fxmlLoader = new FXMLLoader(getClass().getResource("../modules/ApprovalModalRight.fxml"));
        Node rightContainer = fxmlLoader.load();
        setRightContent(rightContainer);
    }
}
