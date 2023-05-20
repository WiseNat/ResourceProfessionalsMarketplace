package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LoanSearchTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LoanUtil {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ResourceUtil resourceUtil;


    public List<ResourceCollectionTO> getLoanables(LoanSearchTO loanSearchTO) {
        List<ResourceRepository.IResourceCollection> foundLoanables = resourceRepository.findAllByCollectionWithPredicates(
                enumUtil.bandingToEntity(loanSearchTO.getBanding()),
                enumUtil.mainRoleToEntity(loanSearchTO.getMainRole()),
                enumUtil.subRoleToEntity(loanSearchTO.getSubRole()),
                loanSearchTO.getCostPerHour()
        );

        return iResourceCollectionToResourceCollectionTO(foundLoanables);
    }

    public List<ResourceCollectionTO> iResourceCollectionToResourceCollectionTO(List<ResourceRepository.IResourceCollection> iResourceCollections) {

        ArrayList<ResourceCollectionTO> resourceCollections = new ArrayList<>();

        for (ResourceRepository.IResourceCollection iResourceCollection : iResourceCollections) {
            ResourceTO resourceTO = new ResourceTO();
            resourceTO.setBanding(BandingEnum.valueToEnum(iResourceCollection.getBanding().getName()));
            resourceTO.setMainRole(MainRoleEnum.valueToEnum(iResourceCollection.getMainRole().getName()));
            resourceTO.setCostPerHour(iResourceCollection.getCostPerHour());
            resourceTO.setDailyLateFee(resourceUtil.calculateDailyLateFee(iResourceCollection.getCostPerHour()));

            Optional<SubRoleEntity> subRole = iResourceCollection.getSubRole();
            subRole.ifPresent(subRoleEntity -> resourceTO.setSubRole(SubRoleEnum.valueToEnum(subRoleEntity.getName())));

            ResourceCollectionTO resourceCollectionTO = new ResourceCollectionTO();
            resourceCollectionTO.setQuantity(iResourceCollection.getCount());
            resourceCollectionTO.setResource(resourceTO);

            resourceCollections.add(resourceCollectionTO);
        }

        return resourceCollections;
    }
}
