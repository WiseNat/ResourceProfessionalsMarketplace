package com.wise.ResourceProfessionalsMarketplace.util;

import com.wise.ResourceProfessionalsMarketplace.application.StageHandler;
import com.wise.ResourceProfessionalsMarketplace.constant.AccountTypeEnum;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountEntity;
import com.wise.ResourceProfessionalsMarketplace.entity.AccountTypeEntity;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountRepository;
import com.wise.ResourceProfessionalsMarketplace.repository.AccountTypeRepository;
import com.wise.ResourceProfessionalsMarketplace.to.LoginAccountTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AccountUtil {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private StageHandler stageHandler;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();


    /**
     * Begins the login process for a user with their given details
     *
     * @param email       the email for the account
     * @param password    the plaintext password for the account
     * @param accountType the account type of the account (each email can have an associated account per account type)
     */
    public void login(String email, String password, AccountTypeEnum accountType) {
        LoginAccountTO loginAccount = new LoginAccountTO(email, password, accountType);
        boolean isAuthenticated = authenticate(loginAccount);

        if (isAuthenticated) {
            Class<Object> sceneController = getAccountView(loginAccount.getAccountType());
            stageHandler.swapScene(sceneController);
        } else {
            System.out.println("Invalid email or password");
        }
    }

    /**
     * Authenticates the user given their details
     *
     * @param loginAccount the {@link LoginAccountTO} containing the users details
     * @return true if authenticated, false otherwise
     */
    public boolean authenticate(LoginAccountTO loginAccount) {
        AccountTypeEntity accountTypeEntity = accountTypeRepository.findByName(loginAccount.getAccountType().value);
        AccountEntity account = accountRepository.findByEmailAndAccountType(loginAccount.getEmail(), accountTypeEntity);

        if (account == null) {
            System.out.println("[No account exists]");
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
    public <C> Class<C> getAccountView(AccountTypeEnum accountType) {
        switch (accountType) {
            case Admin:
                System.out.println("ADMIN VIEW");
                break;
            case ProjectManager:
                System.out.println("PROJECT MANAGER VIEW");
                break;
            case Resource:
                System.out.println("RESOURCE VIEW");
                break;
            default:
                throw new IllegalArgumentException("No view exists for this Account Type!");
        }

        return null;
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
