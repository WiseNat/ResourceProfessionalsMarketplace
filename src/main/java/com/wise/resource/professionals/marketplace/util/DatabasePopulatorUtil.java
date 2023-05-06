package com.wise.resource.professionals.marketplace.util;

import com.github.javafaker.Faker;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.*;
import com.wise.resource.professionals.marketplace.repository.*;
import com.wise.resource.professionals.marketplace.to.ApprovalTO;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

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

    @Autowired
    private CreateAccountUtil createAccountUtil;


    @Value("${spring.jpa.properties.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;


    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!(hbm2ddlAuto.equals("validate") || hbm2ddlAuto.equals("none") || hbm2ddlAuto.equals("update"))) {
            this.initialiseAccountTypeTable();
            this.initialiseBandingTable();
            this.initialiseRoleTables();

            this.populateDevAccounts();
            this.populateFakeUnapprovedAccounts(20);
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

    private void populateDevAccounts() {
        AccountEntity accountEntity;

        { // Admin
            accountEntity = new AccountEntity();
            accountEntity.setResource(null);
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(AccountTypeEnum.ProjectManager));
            accountEntity.setFirstName("Dev");
            accountEntity.setLastName("Project Manager");
            accountEntity.setEmail("dev@account");
            accountEntity.setEncodedPassword(accountUtil.hashPassword("password"));
            accountEntity.setIsApproved(true);

            accountRepository.save(accountEntity);
        }

        { // Admin
            accountEntity = new AccountEntity();
            accountEntity.setResource(null);
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(AccountTypeEnum.Admin));
            accountEntity.setFirstName("Dev");
            accountEntity.setLastName("Admin");
            accountEntity.setEmail("dev@account");
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
            accountEntity.setEmail("dev@account");
            accountEntity.setEncodedPassword(accountUtil.hashPassword("password"));
            accountEntity.setIsApproved(true);

            accountRepository.save(accountEntity);
        }
    }

    private void populateFakeUnapprovedAccounts(int amount) {
        Random random = new Random();
        Faker faker = new Faker(new Locale("en-GB"));
        AccountTypeEnum[] validAccountTypes = new AccountTypeEnum[]{AccountTypeEnum.ProjectManager, AccountTypeEnum.Resource};

        for (int i = 0; i < amount; i++) {
            AccountTypeEnum accountType = validAccountTypes[random.nextInt(validAccountTypes.length)];
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress(firstName + "." + lastName);
            String password = faker.internet().password();
            String encodedPassword = accountUtil.hashPassword(password);
            boolean isApproved = false;

            CreateAccountTO createAccountTO = new CreateAccountTO();
            createAccountTO.setAccountType(accountType);
            createAccountTO.setFirstName(firstName);
            createAccountTO.setLastName(lastName);
            createAccountTO.setEmail(email);
            createAccountTO.setPassword(password);
            createAccountTO.setEncodedPassword(encodedPassword);
            createAccountTO.setIsApproved(isApproved);

            ApprovalTO approvalTO = new ApprovalTO();
            approvalTO.setAccount(createAccountTO);
            approvalTO.setDate(faker.date().birthday());

            createAccountUtil.persistAccount(createAccountTO);
            createAccountUtil.persistApproval(approvalTO);
        }


//        CreateAccountTO[] createAccountTOS = new CreateAccountTO[]{
//                new CreateAccountTO(), new CreateAccountTO()
//        };
//        Random rnd = new Random();
//
//        createAccountTOS[0].setFirstName("John");
//        createAccountTOS[0].setLastName("Cleese");
//        createAccountTOS[0].setEmail("John.Cleese@company.com");
//        createAccountTOS[0].setIsApproved(false);
//        createAccountTOS[0].setPassword("pass");
//        createAccountTOS[0].setEncodedPassword(accountUtil.hashPassword("pass"));
//        createAccountTOS[0].setAccountType(AccountTypeEnum.Resource);
//
//        BeanUtils.copyProperties(createAccountTOS[0], createAccountTOS[1]);
//
//        createAccountTOS[1].setAccountType(AccountTypeEnum.ProjectManager);
//
//        for (CreateAccountTO createAccountTO : createAccountTOS) {
//            createAccountUtil.persistAccount(createAccountTO);
//
//            ApprovalTO approvalTO = new ApprovalTO();
//            approvalTO.setAccount(createAccountTO);
//            approvalTO.setDate(new Date(-946771200000L + (Math.abs(rnd.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000))));
//
//            createAccountUtil.persistApproval(approvalTO);
//        }
    }
}
