package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.InvalidFieldsAndDataTO;
import com.wise.resource.professionals.marketplace.to.RawResourceTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Component
public class ResourceUtil {

    @Autowired
    private Validator validator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * Calculates the {@code Daily Late Fee} from the given {@code costPerHour} where
     * {@code dailyLateFee = (costPerHour * 8) * 5}
     *
     * @param costPerHour a BigDecimal for the cost per hour
     * @return the daily late fee
     */
    public BigDecimal calculateDailyLateFee(BigDecimal costPerHour) {
        return costPerHour.multiply(new BigDecimal("40"));
    }

    public InvalidFieldsAndDataTO<ResourceTO> createResourceTo(RawResourceTO rawResourceTO) {
        ResourceEntity resourceEntity = rawResourceTO.getResourceEntity();

        BandingEnum banding = BandingEnum.valueToEnum(rawResourceTO.getBanding());
        MainRoleEnum mainRole = MainRoleEnum.valueToEnum(rawResourceTO.getMainRole());
        SubRoleEnum subRole = SubRoleEnum.valueToEnum(rawResourceTO.getSubRole());
        String loanedClient = resourceEntity.getLoanedClient();
        BigDecimal dailyLateFee = resourceEntity.getDailyLateFee();
        Date availabilityDate = resourceEntity.getAvailabilityDate();
        BigDecimal costPerHour = resourceEntity.getCostPerHour();

        ResourceTO resourceTo = new ResourceTO();
        resourceTo.setBanding(banding);
        resourceTo.setSubRole(subRole);
        resourceTo.setMainRole(mainRole);
        resourceTo.setLoanedClient(loanedClient);
        resourceTo.setDailyLateFee(dailyLateFee);
        resourceTo.setAvailabilityDate(availabilityDate);
        resourceTo.setCostPerHour(costPerHour);

        if (resourceEntity.getLoanedClient() == null) {
            try {
                resourceTo.setCostPerHour(new BigDecimal(rawResourceTO.getCostPerHour()));
                resourceTo.setDailyLateFee(calculateDailyLateFee(resourceTo.getCostPerHour()));
            } catch (NumberFormatException e) {
                resourceTo.setCostPerHour(null);
            }
        }

        Set<ConstraintViolation<ResourceTO>> violations = validator.validate(resourceTo);

        return validatorUtil.populateInvalidFieldsAndDataTO(violations, resourceTo);
    }

    public void updateResourceDetails(ResourceEntity resourceEntity, ResourceTO resourceTo) {
        SubRoleEntity subRoleEntity = null;
        if (resourceTo.getSubRole() != null) {
            subRoleEntity = enumUtil.subRoleToEntity(resourceTo.getSubRole());
        }

        resourceEntity.setMainRole(enumUtil.mainRoleToEntity(resourceTo.getMainRole()));
        resourceEntity.setSubRole(subRoleEntity);
        resourceEntity.setBanding(enumUtil.bandingToEntity(resourceTo.getBanding()));
        resourceEntity.setCostPerHour(resourceTo.getCostPerHour());
        resourceEntity.setDailyLateFee(resourceTo.getDailyLateFee());

        resourceRepository.save(resourceEntity);
    }

}
