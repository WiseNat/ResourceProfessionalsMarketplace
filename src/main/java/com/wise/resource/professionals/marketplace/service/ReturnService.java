package com.wise.resource.professionals.marketplace.service;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.to.ReturnSearchTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EnumUtil enumUtil;

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
