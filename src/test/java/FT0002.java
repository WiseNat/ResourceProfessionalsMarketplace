import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.service.CreateAnAccountService;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validator;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Admin account types are ignored when trying to create one. Other account types (resource and project manager) are
 * acknowledged and can be created.
 */
@ExtendWith(MockitoExtension.class)
public class FT0002 {

    @Spy
    @InjectMocks
    private CreateAnAccountService createAnAccountService;

    @Mock
    private Validator validator;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EnumUtil enumUtil;

    private CreateAccountTO createAccountTO;

    @BeforeEach
    public void init() {
        createAccountTO = new CreateAccountTO();

        createAccountTO.setFirstName("FIRSTNAME");
        createAccountTO.setLastName("LASTNAME");
        createAccountTO.setEmail("EMAIL");
        createAccountTO.setPassword("PASSWORD");
    }

    @Test
    public void testIgnoreAdminAccountCreation() {
        createAccountTO.setAccountType(AccountTypeEnum.ADMIN);

        createAnAccountService.createAccount(createAccountTO);
        verify(createAnAccountService, never()).persistAccountAndApproval(any());
    }

    @Nested
    class PositiveFlow {
        @BeforeEach
        public void init() {
            when(validator.validate(any())).thenReturn(new HashSet<>());
            when(enumUtil.accountTypeToEntity(any())).thenReturn(new AccountTypeEntity());
            when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(null);

            doNothing().when(createAnAccountService).persistAccountAndApproval(any());
        }

        @Test
        public void testAcknowledgeResourceAccountCreation() {
            createAccountTO.setAccountType(AccountTypeEnum.RESOURCE);

            createAnAccountService.createAccount(createAccountTO);

            verify(createAnAccountService, times(1)).persistAccountAndApproval(any());
        }

        @Test
        public void testAcknowledgeProjectManagerAccountCreation() {
            createAccountTO.setAccountType(AccountTypeEnum.RESOURCE);

            createAnAccountService.createAccount(createAccountTO);

            verify(createAnAccountService, times(1)).persistAccountAndApproval(any());
        }

    }
}
