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

/**
 * Service class for {@link com.wise.resource.professionals.marketplace.controller.AdminController}
 */
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

    /**
     * Gets a list of approvals that match the search predicates in {@link ApprovalSearchTO}.
     *
     * @param approvalSearchTO the search predicates used when searching for approvals
     * @return a list of {@link ApprovalEntity} which match the given search predicates
     */
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

    /**
     * Denies a given {@link ApprovalEntity}; deletes the approval request and deletes the associated
     * {@link AccountEntity}
     *
     * @param approvalEntity the {@link ApprovalEntity} to be denied.
     */
    public void denyApproval(ApprovalEntity approvalEntity) {
        AccountEntity accountEntity = approvalEntity.getAccount();

        approvalRepository.delete(approvalEntity);
        accountRepository.delete(accountEntity);
    }

    /**
     * Approves a given {@link ApprovalEntity}; deletes the approval request and sets the associated
     * {@link AccountEntity} to approved. If the {@link AccountEntity#accountType} is {@link AccountTypeEnum#RESOURCE} then a
     * default {@link ResourceEntity} will be created for that account as well.
     *
     * @param approvalEntity the {@link ApprovalEntity} to be approved.
     */
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
