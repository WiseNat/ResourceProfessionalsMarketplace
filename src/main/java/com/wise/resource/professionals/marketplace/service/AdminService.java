package com.wise.resource.professionals.marketplace.service;

import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.ApprovalSearchTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ResourceUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private ResourceUtil resourceUtil;

    public List<ApprovalEntity> getApprovals(ApprovalSearchTO approvalSearchTO) {
        List<ApprovalEntity> foundApprovals;

        if (approvalSearchTO.isResourceAllowed() && approvalSearchTO.isProjectManagerAllowed()) {
            foundApprovals = approvalRepository.findAllApprovalsByPredicates(
                    approvalSearchTO.getFirstName(), approvalSearchTO.getLastName(), approvalSearchTO.getEmail());
        } else if (approvalSearchTO.isProjectManagerAllowed()) {
            AccountTypeEntity accountType = enumUtil.accountTypeToEntity(AccountTypeEnum.PROJECT_MANAGER);
            foundApprovals = approvalRepository.findApprovalsByPredicatesAndAccountType(
                    approvalSearchTO.getFirstName(), approvalSearchTO.getLastName(), approvalSearchTO.getEmail(), accountType);
        } else if (approvalSearchTO.isResourceAllowed()) {
            AccountTypeEntity accountType = enumUtil.accountTypeToEntity(AccountTypeEnum.RESOURCE);
            foundApprovals = approvalRepository.findApprovalsByPredicatesAndAccountType(
                    approvalSearchTO.getFirstName(), approvalSearchTO.getLastName(), approvalSearchTO.getEmail(), accountType);
        } else {
            foundApprovals = new ArrayList<>();
        }

        return foundApprovals;
    }

    public void denyApproval(ApprovalEntity approvalEntity) {
        AccountEntity accountEntity = approvalEntity.getAccount();

        approvalRepository.delete(approvalEntity);
        accountRepository.delete(accountEntity);
    }

    public void approveApproval(ApprovalEntity approvalEntity) {
        AccountEntity accountEntity = approvalEntity.getAccount();
        accountEntity.setIsApproved(true);

        if (AccountTypeEnum.valueToEnum(accountEntity.getAccountType().getName()) == AccountTypeEnum.RESOURCE) {
            ResourceTO resourceTO = new ResourceTO();
            resourceTO.setLoanedClient(null);
            resourceTO.setCostPerHour(new BigDecimal("10.0"));
            resourceTO.setDailyLateFee(resourceUtil.calculateDailyLateFee(resourceTO.getCostPerHour()));
            resourceTO.setAvailabilityDate(null);

            ResourceEntity resourceEntity = new ResourceEntity();
            BeanUtils.copyProperties(resourceTO, resourceEntity, "banding, subrole, mainRole");

            resourceEntity.setMainRole(enumUtil.mainRoleToEntity(MainRoleEnum.BUSINESS_ANALYST));
            resourceEntity.setBanding(enumUtil.bandingToEntity(BandingEnum.BAND_ONE));

            resourceRepository.save(resourceEntity);

            accountEntity.setResource(resourceEntity);
        }

        approvalRepository.delete(approvalEntity);
        accountRepository.save(accountEntity);
    }
}
