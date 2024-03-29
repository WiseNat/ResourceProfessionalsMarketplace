package com.wise.resource.professionals.marketplace.util;

import com.wise.resource.professionals.marketplace.application.StageHandler;
import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.controller.AdminController;
import com.wise.resource.professionals.marketplace.controller.MainView;
import com.wise.resource.professionals.marketplace.controller.ProjectManagerController;
import com.wise.resource.professionals.marketplace.controller.ResourceController;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.AccountTypeRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Helper methods surrounding accounts
 */
@Component
public class AccountUtil {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private StageHandler stageHandler;

    @Autowired
    private ResourceController resourceController;

    @Autowired
    private AdminController adminController;

    @Autowired
    private ProjectManagerController projectManagerController;

    /**
     * Authenticates the user given their details
     *
     * @param loginAccount the {@link LogInAccountTO} containing the users details
     * @return true if authenticated, false otherwise
     */
    public boolean authenticate(LogInAccountTO loginAccount) {
        AccountTypeEntity accountTypeEntity = accountTypeRepository.findByName(loginAccount.getAccountType().value);
        AccountEntity account = accountRepository.findByEmailAndAccountType(loginAccount.getEmail(), accountTypeEntity);

        if (account == null) {
            return false;
        }

        if (!account.getIsApproved()) {
            return false;
        }

        String plaintextPassword = loginAccount.getPlaintextPassword();
        String encodedPassword = account.getEncodedPassword();

        return authenticatePassword(plaintextPassword, encodedPassword);
    }

    /**
     * Gets the view controller associated with the given {@link AccountTypeEnum}
     *
     * @param accountType the account type
     * @return FXML View Controller
     */
    @SneakyThrows
    public MainView getAccountViewController(AccountTypeEnum accountType) {
        switch (accountType) {
            case ADMIN:
                return adminController;
            case PROJECT_MANAGER:
                return projectManagerController;
            case RESOURCE:
                return resourceController;
            default:
                throw new IllegalArgumentException("No view exists for this Account Type!");
        }
    }


    /**
     * Authenticates a plaintext password against a BCrypt encoded password
     *
     * @param plaintextPassword the plaintext password
     * @param encodedPassword   the BCrypt encoded password
     * @return true if the password matches the hash, false otherwise
     */
    public boolean authenticatePassword(String plaintextPassword, String encodedPassword) {
        return encoder.matches(plaintextPassword, encodedPassword);
    }

    /**
     * Hashes a given input password using BCrypt
     *
     * @param plaintextPassword the plaintext password to hash
     * @return the BCrypt encoded password
     */
    public String hashPassword(String plaintextPassword) {
        return encoder.encode(plaintextPassword);
    }
}
