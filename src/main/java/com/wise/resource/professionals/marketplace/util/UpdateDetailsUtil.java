package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UpdateDetailsUtil {

    @Autowired
    private ResourceUtil resourceUtil;

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
            resourceTo.setDailyLateFee(resourceUtil.costPerHourToDailyLateFee(resourceTo.getCostPerHour()));
        }

        return resourceTo;
    }
}
