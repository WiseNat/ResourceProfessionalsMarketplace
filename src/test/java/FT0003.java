import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.CreateAnAccountUtil;
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
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class FT0003 {

    @Spy
    @InjectMocks
    private CreateAnAccountUtil createAnAccountUtil;

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
        createAccountTO.setAccountType(AccountTypeEnum.Resource);

        doReturn(new HashSet<>()).when(validator).validate(any());
        doReturn(new AccountTypeEntity()).when(enumUtil).accountTypeToEntity(any());
    }

    @Test
    public void testTerminateFlowIfAccountExists() {
        doReturn(new AccountEntity()).when(accountRepository).findByEmailAndAccountType(any(), any());
        doReturn(new String[]{}).when(reflectionUtil).getFields(any());

        createAnAccountUtil.createAccount(createAccountTO);

        verify(createAnAccountUtil, never()).persistAccountAndApproval(any());
    }

    @Test
    public void testContinueFlowIfAccountDoesNotExist() {
        doReturn(null).when(accountRepository).findByEmailAndAccountType(any(), any());
        doNothing().when(createAnAccountUtil).persistAccountAndApproval(any());

        createAnAccountUtil.createAccount(createAccountTO);

        verify(createAnAccountUtil, times(1)).persistAccountAndApproval(any());
    }
}
