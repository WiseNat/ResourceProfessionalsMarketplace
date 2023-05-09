package com.wise.resource.professionals.marketplace.component;

import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import lombok.Getter;
import lombok.SneakyThrows;

import static com.wise.resource.professionals.marketplace.constant.RoleIconMapping.MAIN_ROLE_ICON_MAPPING;
import static com.wise.resource.professionals.marketplace.constant.RoleIconMapping.SUB_ROLE_ICON_MAPPING;

@Getter
public class LoanResourceListBox extends ListBox {

    private final ResourceCollectionTO resourceCollection;

    @SneakyThrows
    public LoanResourceListBox(ResourceCollectionTO resourceCollection) {
        super();

        this.resourceCollection = resourceCollection;

        init();
    }

    private void init() {
        MainRoleEnum mainRoleEnum = resourceCollection.getResource().getMainRole();
        SubRoleEnum subRoleEnum = resourceCollection.getResource().getSubRole();


        String banding = resourceCollection.getResource().getBanding().value;
        String mainRole = mainRoleEnum.value;
        String subRole = "";
        String costPerHour = resourceCollection.getResource().getCostPerHour().toPlainString();
        String quantity = String.valueOf(resourceCollection.getQuantity());

        if (subRoleEnum != null) {
            subRole = resourceCollection.getResource().getSubRole().value + ", ";

            if (SUB_ROLE_ICON_MAPPING.containsKey(subRoleEnum)) {
                this.setSubImageUrl(SUB_ROLE_ICON_MAPPING.get(subRoleEnum).value);
            }
        }

        this.setTitleText(mainRole);
        this.setLeftSubtext(subRole + "Band " + banding + "\n"
                + "£" + costPerHour + " per Hour");
        this.setRightSubtext(quantity + " available");

        if (MAIN_ROLE_ICON_MAPPING.containsKey(mainRoleEnum)) {
            System.out.println(mainRoleEnum.value + ", " + MAIN_ROLE_ICON_MAPPING.get(mainRoleEnum).value);
            this.setMainImageUrl(MAIN_ROLE_ICON_MAPPING.get(mainRoleEnum).value);
        }
    }
}
