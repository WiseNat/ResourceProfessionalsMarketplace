package com.wise.ResourceProfessionalsMarketplace.util;

import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.BandingEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.MainRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ApprovalEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.ResourceEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.ApprovalRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.ResourceRepository;
import com.wise.ResourceProfessionalsMarketplace.to.ApprovalTO;
import com.wise.ResourceProfessionalsMarketplace.to.CreateAccountTO;
import com.wise.ResourceProfessionalsMarketplace.to.ResourceTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Date;

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
        ResourceEntity resourceEntity = null;

        if (accountTO.getAccountType() == AccountTypeEnum.Resource) {
            ResourceTO resourceTO = new ResourceTO();
            resourceTO.setLoanedClient(null);
            resourceTO.setDailyLateFee(100.0);
            resourceTO.setCostPerHour(new BigDecimal("10.0"));
            resourceTO.setAvailabilityDate(null);

            resourceEntity = new ResourceEntity();
            BeanUtils.copyProperties(resourceTO, resourceEntity, "banding, subrole, mainRole");

            resourceEntity.setMainRole(enumUtil.mainRoleToEntity(MainRoleEnum.BusinessAnalyst));
            resourceEntity.setBanding(enumUtil.bandingToEntity(BandingEnum.BandOne));

            resourceRepository.save(resourceEntity);
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
