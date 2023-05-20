package com.wise.resource.professionals.marketplace.util;

import com.github.javafaker.Faker;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.*;
import com.wise.resource.professionals.marketplace.repository.*;
import com.wise.resource.professionals.marketplace.service.CreateAnAccountService;
import com.wise.resource.professionals.marketplace.to.ApprovalTO;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;

/**
 * Helper methods surrounding populating the database on startup
 */
@Component
@Slf4j
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
    private CreateAnAccountService createAnAccountService;

    @Autowired
    private ResourceUtil resourceUtil;

    @Value("${spring.jpa.properties.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("#{new Boolean('${database-populator.create-fake-data}')}")
    private Boolean createFakeData;

    @Value("#{new Integer('${database-populator.fake-unapproved-accounts-quantity}')}")
    private Integer fakeUnapprovedAccountsQuantity;

    @Value("#{new Integer('${database-populator.fake-available-resources-quantity}')}")
    private Integer fakeAvailableResourcesQuantity;

    @Value("#{new Integer('${database-populator.fake-loaned-resources-quantity}')}")
    private Integer fakeLoanedResourcesQuantity;

    /**
     * An event listener that executes once it receives a {@link ContextRefreshedEvent}. This shouldn't be manually
     * called.
     * <p>
     * On application startup, this initialises the enum tables with their relevant data. It also populates the
     * database with fake data if {@link DatabasePopulatorUtil#createFakeData} is {@code true}.
     *
     * @param event the {@link ContextRefreshedEvent}
     */
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!(hbm2ddlAuto.equals("validate") || hbm2ddlAuto.equals("none") || hbm2ddlAuto.equals("update"))) {
            log.info("Intialising ordinal tables");
            this.initialiseAccountTypeTable();
            this.initialiseBandingTable();
            this.initialiseRoleTables();

            if (createFakeData) {
                log.info("Populating tables with fake data");
                this.populateDevAccounts();
                this.populateFakeUnapprovedAccounts(fakeUnapprovedAccountsQuantity);
                this.populateFakeAvailableResources(fakeAvailableResourcesQuantity);
                this.populateFakeLoanedResources(fakeLoanedResourcesQuantity);
            }
        }
    }

    /**
     * Initialises the Account Type table ({@link AccountTypeRepository}) using data from {@link AccountTypeEnum}
     */
    private void initialiseAccountTypeTable() {
        for (AccountTypeEnum accountType : AccountTypeEnum.values()) {
            AccountTypeEntity accountTypeEntity = new AccountTypeEntity();
            accountTypeEntity.setName(accountType.value);

            accountTypeRepository.save(accountTypeEntity);
        }
    }

    /**
     * Initialises the Banding table ({@link BandingRepository}) using data from {@link BandingEnum}
     */
    private void initialiseBandingTable() {
        for (BandingEnum banding : BandingEnum.values()) {
            BandingEntity bandingEntity = new BandingEntity();
            bandingEntity.setName(banding.value);

            bandingRepository.save(bandingEntity);
        }
    }

    /**
     * Initialises both of the role tables ({@link MainRoleRepository} and {@link SubRoleRepository}) using data from
     * {@link com.wise.resource.professionals.marketplace.constant.RoleMapping#ROLE_MAPPING}
     */
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

    /**
     * Populates the database with development accounts for each account type. These use {@code dev@account} for the
     * email and {@code password} for the password.
     */
    private void populateDevAccounts() {
        AccountEntity accountEntity;

        { // Admin
            accountEntity = new AccountEntity();
            accountEntity.setResource(null);
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(AccountTypeEnum.PROJECT_MANAGER));
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
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(AccountTypeEnum.ADMIN));
            accountEntity.setFirstName("Dev");
            accountEntity.setLastName("Admin");
            accountEntity.setEmail("dev@account");
            accountEntity.setEncodedPassword(accountUtil.hashPassword("password"));
            accountEntity.setIsApproved(true);

            accountRepository.save(accountEntity);
        }

        { // Resource
            ResourceEntity resourceEntity = new ResourceEntity();
            resourceEntity.setBanding(enumUtil.bandingToEntity(BandingEnum.BAND_FIVE));
            resourceEntity.setSubRole(enumUtil.subRoleToEntity(SubRoleEnum.BACKEND_DEVELOPER));
            resourceEntity.setMainRole(enumUtil.mainRoleToEntity(MainRoleEnum.DEVELOPER));
            resourceEntity.setCostPerHour(new BigDecimal("12.5"));
            resourceEntity.setDailyLateFee(resourceUtil.calculateDailyLateFee(resourceEntity.getCostPerHour()));
            resourceEntity.setLoanedClient(null);

            resourceRepository.save(resourceEntity);

            accountEntity = new AccountEntity();
            accountEntity.setResource(resourceEntity);
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(AccountTypeEnum.RESOURCE));
            accountEntity.setFirstName("Dev");
            accountEntity.setLastName("Resource");
            accountEntity.setEmail("dev@account");
            accountEntity.setEncodedPassword(accountUtil.hashPassword("password"));
            accountEntity.setIsApproved(true);

            accountRepository.save(accountEntity);
        }
    }

    /**
     * Populates the database with fake approvals and fake unapproved accounts.
     *
     * @param amount the amount to make
     */
    private void populateFakeUnapprovedAccounts(int amount) {
        Random random = new Random();
        Faker faker = new Faker(new Locale("en-GB"));
        AccountTypeEnum[] validAccountTypes = new AccountTypeEnum[]{AccountTypeEnum.PROJECT_MANAGER, AccountTypeEnum.RESOURCE};

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

            createAnAccountService.persistAccount(createAccountTO);
            createAnAccountService.persistApproval(approvalTO);
        }
    }

    /**
     * Populates the database with fake available resources.
     *
     * @param amount the amount to make
     */
    private void populateFakeAvailableResources(int amount) {
        Random random = new Random();
        Faker faker = new Faker(new Locale("en-GB"));
        BigDecimal[] validBigDecimals = new BigDecimal[]{
                new BigDecimal("10.50"),
                new BigDecimal("12.78"),
                new BigDecimal("24.10"),
                new BigDecimal("17.20"),
                new BigDecimal("8.42"),
        };

        for (int i = 0; i < amount; i++) {
            BandingEnum banding = BandingEnum.values()[random.nextInt(BandingEnum.values().length)];
            MainRoleEnum mainRole = MainRoleEnum.values()[random.nextInt(MainRoleEnum.values().length)];
            BigDecimal costPerHour = validBigDecimals[random.nextInt(validBigDecimals.length)];
            BigDecimal dailyLateFee = resourceUtil.calculateDailyLateFee(costPerHour);

            SubRoleEnum[] subRoles = ROLE_MAPPING.get(mainRole);
            SubRoleEnum subRole = null;
            if (subRoles.length != 0) {
                subRole = subRoles[random.nextInt(subRoles.length)];
            }

            ResourceEntity resourceEntity = new ResourceEntity();
            resourceEntity.setBanding(enumUtil.bandingToEntity(banding));
            resourceEntity.setMainRole(enumUtil.mainRoleToEntity(mainRole));
            resourceEntity.setSubRole(null);
            resourceEntity.setDailyLateFee(dailyLateFee);
            resourceEntity.setCostPerHour(costPerHour);
            resourceEntity.setLoanedClient(null);

            if (subRole != null) {
                resourceEntity.setSubRole(enumUtil.subRoleToEntity(subRole));
            }

            resourceRepository.save(resourceEntity);

            AccountTypeEnum accountType = AccountTypeEnum.RESOURCE;
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = faker.internet().emailAddress(firstName + "." + lastName);
            String password = faker.internet().password();

            AccountEntity accountEntity = new AccountEntity();
            accountEntity.setResource(resourceEntity);
            accountEntity.setAccountType(enumUtil.accountTypeToEntity(accountType));
            accountEntity.setFirstName(firstName);
            accountEntity.setLastName(lastName);
            accountEntity.setEmail(email);
            accountEntity.setEncodedPassword(accountUtil.hashPassword(password));
            accountEntity.setIsApproved(true);

            accountRepository.save(accountEntity);
        }
    }

    /**
     * Populates the database with fake loaned resources. This uses
     * {@link DatabasePopulatorUtil#populateFakeAvailableResources(int)} to create new fake resources and then selects
     * the same amount of resources to become loaned.
     *
     * @param amount the amount to make
     */
    private void populateFakeLoanedResources(int amount) {
        Faker faker = new Faker(new Locale("en-GB"));

        this.populateFakeAvailableResources(amount);

        List<ResourceEntity> resourceEntities = resourceRepository.findAll();
        List<ResourceEntity> loanedResourceEntities = resourceEntities.subList(0, amount);

        for (ResourceEntity resourceEntity : loanedResourceEntities) {
            resourceEntity.setLoanedClient(faker.company().name());

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(System.currentTimeMillis()));

            cal.add(Calendar.YEAR, -5);
            Date beforeDate = cal.getTime();

            cal.add(Calendar.YEAR, 10);
            Date afterDate = cal.getTime();

            resourceEntity.setAvailabilityDate(faker.date().between(beforeDate, afterDate));

            resourceRepository.save(resourceEntity);
        }
    }

}
