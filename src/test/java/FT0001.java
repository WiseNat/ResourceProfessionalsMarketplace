import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.AccountTypeRepository;
import com.wise.resource.professionals.marketplace.to.LogInAccountTO;
import com.wise.resource.professionals.marketplace.util.AccountUtil;
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

    @Test
    public void testAuthenticateWithValidAccount() {
        String email = "My_Test.email@foo.com";
        String plaintextPassword = "S0me_%$P4ssword";
        AccountTypeEnum accountType = AccountTypeEnum.Resource;

        LogInAccountTO logInAccountTO = new LogInAccountTO(email, plaintextPassword, accountType);

        AccountEntity account = new AccountEntity();
        account.setIsApproved(true);
        account.setEncodedPassword(accountUtil.hashPassword(plaintextPassword));

        when(accountTypeRepository.findByName(any())).thenReturn(null);
        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(account);

        assertTrue(accountUtil.authenticate(logInAccountTO));
    }

    @Test
    public void testAuthenticateWithNoAccount() {
        String email = "My_Test.email@foo.com";
        String plaintextPassword = "S0me_%$P4ssword";
        AccountTypeEnum accountType = AccountTypeEnum.Resource;

        LogInAccountTO logInAccountTO = new LogInAccountTO(email, plaintextPassword, accountType);

        when(accountTypeRepository.findByName(any())).thenReturn(null);
        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(null);

        assertFalse(accountUtil.authenticate(logInAccountTO));
    }

    @Test
    public void testAuthenticateWithUnapprovedAccount() {
        String email = "My_Test.email@foo.com";
        String plaintextPassword = "S0me_%$P4ssword";
        AccountTypeEnum accountType = AccountTypeEnum.Resource;

        LogInAccountTO logInAccountTO = new LogInAccountTO(email, plaintextPassword, accountType);

        AccountEntity account = new AccountEntity();
        account.setIsApproved(false);
        account.setEncodedPassword(accountUtil.hashPassword(plaintextPassword));

        when(accountTypeRepository.findByName(any())).thenReturn(null);
        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(account);

        assertFalse(accountUtil.authenticate(logInAccountTO));
    }
}
