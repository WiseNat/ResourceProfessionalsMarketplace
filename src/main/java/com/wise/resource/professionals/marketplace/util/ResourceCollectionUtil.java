package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ResourceCollectionUtil {

    public List<ResourceCollectionTO> groupResourceEntities(List<ResourceEntity> resourceEntities) {
        ArrayList<ResourceCollectionTO> resourceCollectionTOS = new ArrayList<>();

        for (ResourceEntity resourceEntity : resourceEntities) {
            ResourceTO resourceTo = new ResourceTO();
            resourceTo.setBanding(BandingEnum.valueToEnum(resourceEntity.getBanding().getName()));
            resourceTo.setSubRole(SubRoleEnum.valueToEnum(resourceEntity.getSubRole().getName()));
            resourceTo.setMainRole(MainRoleEnum.valueToEnum(resourceEntity.getMainRole().getName()));
            resourceTo.setCostPerHour(resourceEntity.getCostPerHour());
            resourceTo.setDailyLateFee(resourceEntity.getDailyLateFee());


        }

        return null;
    }

}
