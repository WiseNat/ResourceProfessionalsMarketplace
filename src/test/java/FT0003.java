import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.service.CreateAnAccountService;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ReflectionUtil;
import org.junit.jupiter.api.BeforeEach;
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
 * When creating an account, if an existing account entity is found the flow is terminated. Otherwise, the flow
 * continues.
 */
@ExtendWith(MockitoExtension.class)
public class FT0003 {

    @Spy
    @InjectMocks
    private CreateAnAccountService createAnAccountService;

    @Mock
    private Validator validator;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EnumUtil enumUtil;

    @Mock
    private ReflectionUtil reflectionUtil;

    private CreateAccountTO createAccountTO;

    @BeforeEach
    public void init() {
        createAccountTO = new CreateAccountTO();

        createAccountTO.setFirstName("FIRSTNAME");
        createAccountTO.setLastName("LASTNAME");
        createAccountTO.setEmail("EMAIL");
        createAccountTO.setPassword("PASSWORD");
        createAccountTO.setAccountType(AccountTypeEnum.RESOURCE);

        when(validator.validate(any())).thenReturn(new HashSet<>());
        when(enumUtil.accountTypeToEntity(any())).thenReturn(new AccountTypeEntity());
    }

    @Test
    public void testTerminateFlowIfAccountExists() {
        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(new AccountEntity());
        when(reflectionUtil.getFields(any())).thenReturn(new String[]{});

        createAnAccountService.createAccount(createAccountTO);

        verify(createAnAccountService, never()).persistAccountAndApproval(any());
    }

    @Test
    public void testContinueFlowIfAccountDoesNotExist() {
        when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(null);

        doNothing().when(createAnAccountService).persistAccountAndApproval(any());

        createAnAccountService.createAccount(createAccountTO);

        verify(createAnAccountService, times(1)).persistAccountAndApproval(any());
    }
}
