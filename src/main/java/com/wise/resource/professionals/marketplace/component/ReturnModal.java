package com.wise.resource.professionals.marketplace.component;

import javafx.fxml.FXMLLoader;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class ReturnModal extends Modal {

    @SneakyThrows
    public ReturnModal() {
        super();

        // TODO: This...
        FXMLLoader fxmlLoader;

        init();
    }

    private void init() {
        this.setInnerTitle("INNER TITLE TEXT");
    }

}
