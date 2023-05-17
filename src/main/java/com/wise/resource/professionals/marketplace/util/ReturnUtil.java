package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.to.ReturnSearchTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReturnUtil {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    EnumUtil enumUtil;

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
