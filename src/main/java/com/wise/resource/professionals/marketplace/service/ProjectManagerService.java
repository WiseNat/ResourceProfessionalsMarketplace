package com.wise.resource.professionals.marketplace.service;

import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.*;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Service class for {@link com.wise.resource.professionals.marketplace.controller.ProjectManagerController}
 */
@Service
public class ProjectManagerService {

    @Autowired
    private Validator validator;

    @Autowired
    private ValidatorUtil validatorUtil;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ResourceRepository resourceRepository;

    /**
     * Creates a {@link LoanTO} from a given {@link RawLoanTO} while also performing validation.
     *
     * @param rawLoanTO the {@link RawLoanTO} to transform and validate
     * @return a {@link InvalidFieldsAndDataTO} containing the {@link RawLoanTO} as {@link InvalidFieldsAndDataTO#data}
     * and any violating fields as {@link InvalidFieldsAndDataTO#invalidFields}
     */
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

    /**
     * Finds all resources that match the details of {@link LoanTO#resourceCollectionTO} and loans out
     * {@link LoanTO#amount} of them to the {@link LoanTO#clientName} until {@link LoanTO#availabilityDate}.
     *
     * @param loanTO details of the resources and when/who to loan them to
     */
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

    /**
     * Returns a loaned resource. Sets their {@link ResourceEntity#loanedClient} and
     * {@link ResourceEntity#availabilityDate} to null.
     *
     * @param resourceEntity the loaned resource entity to be returned.
     */
    public void returnResource(ResourceEntity resourceEntity) {
        resourceEntity.setLoanedClient(null);
        resourceEntity.setAvailabilityDate(null);

        resourceRepository.save(resourceEntity);
    }
}
