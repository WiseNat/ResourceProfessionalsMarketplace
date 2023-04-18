module wise.resource.professionals.marketplace.resourceprofessionalsmarketplace {
    requires javafx.controls;
    requires javafx.fxml;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
                
    opens wise.resource.professionals.marketplace.resourceprofessionalsmarketplace to javafx.fxml;
    exports wise.resource.professionals.marketplace.resourceprofessionalsmarketplace;
}