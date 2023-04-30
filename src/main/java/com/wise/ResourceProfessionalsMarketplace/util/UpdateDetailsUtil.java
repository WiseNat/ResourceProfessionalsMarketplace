package com.wise.ResourceProfessionalsMarketplace.util;

import com.wise.ResourceProfessionalsMarketplace.constant.BandingEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.MainRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.SubRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.ResourceEntity;
import com.wise.ResourceProfessionalsMarketplace.to.ResourceTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UpdateDetailsUtil {
    public ResourceTO createResourceTO(String banding, String subRole, String mainRole, String costPerHour, ResourceEntity resourceEntity) {
        ResourceTO resourceTo = new ResourceTO();
        resourceTo.setBanding(BandingEnum.valueToEnum(banding));
        resourceTo.setSubRole(SubRoleEnum.valueToEnum(subRole));
        resourceTo.setMainRole(MainRoleEnum.valueToEnum(mainRole));
        resourceTo.setLoanedClient(resourceEntity.getLoanedClient());
        resourceTo.setDailyLateFee(resourceEntity.getDailyLateFee());
        resourceTo.setAvailabilityDate(resourceEntity.getAvailabilityDate());
        resourceTo.setCostPerHour(resourceEntity.getCostPerHour());

        if (resourceEntity.getLoanedClient() == null) {
            resourceTo.setCostPerHour(new BigDecimal(costPerHour));
        }

        return resourceTo;
    }
}
