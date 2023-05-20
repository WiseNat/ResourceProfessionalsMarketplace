package com.wise.resource.professionals.marketplace.service;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.to.LoanSearchTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.to.ReturnSearchTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for {@link com.wise.resource.professionals.marketplace.module.ReturnSearch}
 */
@Service
public class ReturnService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EnumUtil enumUtil;

    /**
     * Gets a list of returnable resources that match the given search predicates in {@link ReturnSearchTO}.
     *
     * @param returnSearchTO the search predicates used when searching for returnable resources.
     * @return a list of {@link AccountEntity} which match the given search predicates.
     */
    public List<AccountEntity> getReturnables(ReturnSearchTO returnSearchTO) {
        return accountRepository.findAllWithPredicates(
                returnSearchTO.getFirstName(),
                returnSearchTO.getLastName(),
                returnSearchTO.getClient(),
                enumUtil.bandingToEntity(returnSearchTO.getBanding()),
                enumUtil.mainRoleToEntity(returnSearchTO.getMainRole()),
                enumUtil.subRoleToEntity(returnSearchTO.getSubRole())
        );
    }
}
