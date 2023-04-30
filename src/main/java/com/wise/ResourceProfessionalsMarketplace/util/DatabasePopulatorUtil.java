package com.wise.ResourceProfessionalsMarketplace.util;

import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.BandingEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.MainRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.constant.SubRoleEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.*;
import com.wise.ResourceProfessionalsMarketplace.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

import static com.wise.ResourceProfessionalsMarketplace.constant.RoleMapping.ROLE_MAPPING;

@Component
public class DatabasePopulatorUtil {

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private BandingRepository bandingRepository;

    @Autowired
    private MainRoleRepository mainRoleRepository;

    @Autowired
    private SubRoleRepository subRoleRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private EnumUtil enumUtil;

    @Autowired
    private AccountUtil accountUtil;


    @Value("${spring.jpa.properties.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!(hbm2ddlAuto.equals("validate") || hbm2ddlAuto.equals("none") || hbm2ddlAuto.equals("update"))) {
            this.initialiseAccountTypeTable();
            this.initialiseBandingTable();
            this.initialiseRoleTables();

            this.populateAccountTableWithFakeData();
        }


    }

    private void initialiseAccountTypeTable() {
        for (AccountTypeEnum accountType : AccountTypeEnum.values()) {
            AccountTypeEntity accountTypeEntity = new AccountTypeEntity();
            accountTypeEntity.setName(accountType.value);

            accountTypeRepository.save(accountTypeEntity);
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

    private void populateAccountTableWithFakeData() {
        AccountEntity accountEntity;

        { // Admin
            accountEntity = new AccountEntity();
            accountEntity.setResource(null);
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(AccountTypeEnum.Admin));
            accountEntity.setFirstName("Dev");
            accountEntity.setLastName("Admin");
            accountEntity.setEmail("dev@admin");
            accountEntity.setEncodedPassword(accountUtil.hashPassword("password"));
            accountEntity.setIsApproved(true);

            accountRepository.save(accountEntity);
        }

        { // Resource
            ResourceEntity resourceEntity = new ResourceEntity();
            resourceEntity.setBanding(enumUtil.bandingToEntity(BandingEnum.BandSix));
            resourceEntity.setSubRole(enumUtil.subRoleToEntity(SubRoleEnum.BackendDeveloper));
            resourceEntity.setMainRole(enumUtil.mainRoleToEntity(MainRoleEnum.Developer));
            resourceEntity.setDailyLateFee(100.0);
            resourceEntity.setCostPerHour(new BigDecimal("12.5"));
            resourceEntity.setLoanedClient(null);

            resourceRepository.save(resourceEntity);

            accountEntity = new AccountEntity();
            accountEntity.setResource(resourceEntity);
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(AccountTypeEnum.Resource));
            accountEntity.setFirstName("Dev");
            accountEntity.setLastName("Resource");
            accountEntity.setEmail("dev@resource");
            accountEntity.setEncodedPassword(accountUtil.hashPassword("password"));
            accountEntity.setIsApproved(true);

            accountRepository.save(accountEntity);
        }

    }
}
