package com.wise.ResourceProfessionalsMarketplace.util;

import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ApprovalEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ResourceEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.ApprovalRepository;
import com.wise.ResourceProfessionalsMarketplace.to.ApprovalTO;
import com.wise.ResourceProfessionalsMarketplace.to.CreateAccountTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Component
public class CreateAccountUtil {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ApprovalRepository approvalRepository;

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
        ResourceEntity resourceEntity = null;

        if (accountTO.getResource() != null) {
            // TODO: Fix this..
            resourceEntity = new ResourceEntity();
        }

        AccountEntity accountEntity = new AccountEntity();
        BeanUtils.copyProperties(accountTO, accountEntity, "resource, accountType");

        accountEntity.setResource(resourceEntity);
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
