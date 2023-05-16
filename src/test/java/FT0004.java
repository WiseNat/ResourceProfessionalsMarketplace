import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.CreateAnAccountUtil;
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

@ExtendWith(MockitoExtension.class)
public class FT0004 {

    @Spy
    @InjectMocks
    private CreateAnAccountUtil createAnAccountUtil;

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
        createAccountTO.setAccountType(AccountTypeEnum.Admin);

        createAnAccountUtil.createAccount(createAccountTO);
        verify(createAnAccountUtil, never()).persistAccountAndApproval(any());
    }

    @Nested
    class PositiveFlow {
        @BeforeEach
        public void init() {
            when(validator.validate(any())).thenReturn(new HashSet<>());
            when(enumUtil.accountTypeToEntity(any())).thenReturn(null);
            when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(null);

            doNothing().when(createAnAccountUtil).persistAccountAndApproval(any());
        }

        @Test
        public void testAcknowledgeResourceAccountCreation() {
            createAccountTO.setAccountType(AccountTypeEnum.Resource);

            createAnAccountUtil.createAccount(createAccountTO);

            verify(createAnAccountUtil, times(1)).persistAccountAndApproval(any());
        }

        @Test
        public void testAcknowledgeProjectManagerAccountCreation() {
            createAccountTO.setAccountType(AccountTypeEnum.Resource);

            createAnAccountUtil.createAccount(createAccountTO);

            verify(createAnAccountUtil, times(1)).persistAccountAndApproval(any());
        }

    }
}
