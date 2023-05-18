package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.InvalidFieldsAndDataTO;
import com.wise.resource.professionals.marketplace.to.LoanTO;
import com.wise.resource.professionals.marketplace.to.RawLoanTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class ProjectManagerUtil {

    @Autowired
    private Validator validator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ResourceRepository resourceRepository;

    public InvalidFieldsAndDataTO<LoanTO> createLoanTo(RawLoanTO rawLoanTO) {
        ResourceCollectionTO resourceCollectionTO = rawLoanTO.getResourceCollectionTO();
        int amount = rawLoanTO.getAmount();
        String clientName = rawLoanTO.getClientName();
        Date availabilityDate = Date.from(rawLoanTO.getAvailabilityDate().atStartOfDay(ZoneId.systemDefault()).toInstant());

        LoanTO loanTO = new LoanTO();
        loanTO.setResourceCollectionTO(resourceCollectionTO);
        loanTO.setAmount(amount);
        loanTO.setClientName(clientName);
        loanTO.setAvailabilityDate(availabilityDate);

        Set<ConstraintViolation<LoanTO>> violations = validator.validate(loanTO);

        return validatorUtil.populateInvalidFieldsAndDataTO(violations, loanTO);
    }

    public void loanResource(LoanTO loanTO) {
        BandingEntity banding = enumUtil.bandingToEntity(loanTO.getResourceCollectionTO().getResource().getBanding());
        MainRoleEntity mainRole = enumUtil.mainRoleToEntity(loanTO.getResourceCollectionTO().getResource().getMainRole());
        SubRoleEntity subRole = enumUtil.subRoleToEntity(loanTO.getResourceCollectionTO().getResource().getSubRole());
        BigDecimal costPerHour = loanTO.getResourceCollectionTO().getResource().getCostPerHour();

        List<ResourceEntity> resources = resourceRepository.findByBandingAndMainRoleAndSubRoleAndCostPerHour(
                banding, mainRole, subRole, costPerHour);

        Collections.shuffle(resources);

        int amountToLoan = loanTO.getAmount();

        if (amountToLoan > resources.size()) {
            amountToLoan = resources.size();
        }

        List<ResourceEntity> selectedResources = resources.subList(0, amountToLoan);

        String client = loanTO.getClientName();

        for (ResourceEntity resourceEntity : selectedResources) {
            resourceEntity.setAvailabilityDate(loanTO.getAvailabilityDate());
            resourceEntity.setLoanedClient(client);

            resourceRepository.save(resourceEntity);
        }
    }

    public void returnResource(ResourceEntity resourceEntity) {
        resourceEntity.setLoanedClient(null);
        resourceEntity.setAvailabilityDate(null);

        resourceRepository.save(resourceEntity);
    }
}
