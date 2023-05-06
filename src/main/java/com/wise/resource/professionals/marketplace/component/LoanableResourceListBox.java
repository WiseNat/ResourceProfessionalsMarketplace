package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import lombok.Getter;
import lombok.SneakyThrows;

@Getter
public class LoanableResourceListBox extends ListBox {

    private final ResourceCollectionTO resourceCollection;

    @SneakyThrows
    public LoanableResourceListBox(ResourceCollectionTO resourceCollection) {
        super();

        this.resourceCollection = resourceCollection;

        init();
    }

    private void init() {
        String banding = resourceCollection.getResource().getBanding().value;
        String mainRole = resourceCollection.getResource().getMainRole().value;
        String subRole = "";
        String costPerHour = resourceCollection.getResource().getCostPerHour().toPlainString();
        String quantity = String.valueOf(resourceCollection.getQuantity());

        SubRoleEnum subRoleEnum = resourceCollection.getResource().getSubRole();
        if (subRoleEnum != null) {
            subRole = resourceCollection.getResource().getSubRole().value + ", ";
        }

        this.setTitleText(mainRole);
        this.setLeftSubtext(subRole + "Band " + banding + "\n"
                + "£" + costPerHour + " per Hour");
        this.setRightSubtext(quantity + " available");

        // TODO: Add images..
        this.removeImage();
    }
}
