package com.wise.ResourceProfessionalsMarketplace.util;

import com.wise.ResourceProfessionalsMarketplace.constant.*;
import com.wise.ResourceProfessionalsMarketplace.entity.*;
import com.wise.ResourceProfessionalsMarketplace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.wise.ResourceProfessionalsMarketplace.constant.RoleMapping.ROLE_MAPPING;

@Component
public class DatabasePopulatorUtil {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private ApprovalTypeRepository approvalTypeRepository;

    @Autowired
    private BandingRepository bandingRepository;

    @Autowired
    private MainRoleRepository mainRoleRepository;

    @Autowired
    private SubRoleRepository subRoleRepository;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.initialiseAccountTypeTable();
        this.initialiseApprovalTypeTable();
        this.initialiseBandingTable();
        this.initialiseRoleTables();
    }

    private void initialiseAccountTypeTable() {
        for (AccountTypeEnum accountType : AccountTypeEnum.values()) {
            AccountTypeEntity accountTypeEntity = new AccountTypeEntity();
            accountTypeEntity.setName(accountType.value);

            accountTypeRepository.save(accountTypeEntity);
        }
    }

    private void initialiseApprovalTypeTable() {
        for (ApprovalTypeEnum approvalType : ApprovalTypeEnum.values()) {
            ApprovalTypeEntity approvalTypeEntity = new ApprovalTypeEntity();
            approvalTypeEntity.setName(approvalType.value);

            approvalTypeRepository.save(approvalTypeEntity);
        }
    }

    private void initialiseBandingTable() {
        for (BandingEnum banding : BandingEnum.values()) {
            BandingEntity bandingEntity = new BandingEntity();
            bandingEntity.setName(banding.value);

            bandingRepository.save(bandingEntity);
        }
    }

    private void initialiseRoleTables() {
        for (Map.Entry<MainRoleEnum, SubRoleEnum[]> entry : ROLE_MAPPING.entrySet()) {
            MainRoleEnum mainRole = entry.getKey();
            SubRoleEnum[] subRoles = entry.getValue();

            MainRoleEntity mainRoleEntity = new MainRoleEntity();
            mainRoleEntity.setName(mainRole.value);

            mainRoleRepository.save(mainRoleEntity);

            for (SubRoleEnum subRole : subRoles) {
                SubRoleEntity subRoleEntity = new SubRoleEntity();
                subRoleEntity.setName(subRole.value);
                subRoleEntity.setMainRole(mainRoleEntity);

                subRoleRepository.save(subRoleEntity);
            }
        }
    }
}
