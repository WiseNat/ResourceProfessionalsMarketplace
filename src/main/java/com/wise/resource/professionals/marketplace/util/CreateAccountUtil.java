package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.ApprovalTO;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CreateAccountUtil {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private EnumUtil enumUtil;

    public void persistAccountAndApproval(CreateAccountTO createAccountTO) {
        persistAccount(createAccountTO);

        ApprovalTO approvalTO = new ApprovalTO();
        approvalTO.setAccount(createAccountTO);
        approvalTO.setDate(new Date(System.currentTimeMillis()));

        persistApproval(approvalTO);
    }

    public void persistAccount(CreateAccountTO accountTO) {
        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(accountTO, accountEntity, "resource, accountType");

        accountEntity.setResource(null);
        accountEntity.setAccountType(enumUtil.accountTypeToEntity(accountTO.getAccountType()));

        accountRepository.save(accountEntity);
    }

    public void persistApproval(ApprovalTO approvalTO) {
        ApprovalEntity approvalEntity = new ApprovalEntity();
        BeanUtils.copyProperties(approvalTO, approvalEntity, "account");

        String email = approvalTO.getAccount().getEmail();
        AccountTypeEntity accountTypeEntity = enumUtil.accountTypeToEntity(approvalTO.getAccount().getAccountType());
        approvalEntity.setAccount(accountRepository.findByEmailAndAccountType(email, accountTypeEntity));

        approvalRepository.save(approvalEntity);
    }
}
