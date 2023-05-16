import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.AccountTypeRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.util.AccountUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FT0001 {

    @InjectMocks
    private AccountUtil accountUtil;

    @Mock
    private AccountTypeRepository accountTypeRepository;

    @Mock
    private AccountRepository accountRepository;

    private LogInAccountTO logInAccountTO;

    @BeforeEach
    public void init() {
        String email = "My_Test.email@foo.com";
        String plaintextPassword = "S0me_%$P4ssword";
        AccountTypeEnum accountType = AccountTypeEnum.Resource;

        logInAccountTO = new LogInAccountTO(email, plaintextPassword, accountType);

        when(accountTypeRepository.findByName(any())).thenReturn(new AccountTypeEntity());
    }

    @Test
    public void testAuthenticateWithValidAccount() {
        AccountEntity account = new AccountEntity();
        account.setIsApproved(true);
        account.setEncodedPassword(accountUtil.hashPassword(logInAccountTO.getPlaintextPassword()));

        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(account);

        assertTrue(accountUtil.authenticate(logInAccountTO));
    }

    @Test
    public void testAuthenticateWithInvalidPassword() {
        AccountEntity account = new AccountEntity();
        account.setIsApproved(true);
        account.setEncodedPassword("some random password; this definitely won't match");

        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(account);

        assertFalse(accountUtil.authenticate(logInAccountTO));
    }

    @Test
    public void testAuthenticateWithUnapprovedAccount() {
        AccountEntity account = new AccountEntity();
        account.setIsApproved(false);

        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(account);

        assertFalse(accountUtil.authenticate(logInAccountTO));
    }

    @Test
    public void testAuthenticateWithNoAccount() {
        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(null);

        assertFalse(accountUtil.authenticate(logInAccountTO));
    }
}
