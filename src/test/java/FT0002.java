import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.service.CreateAnAccountService;
import com.wise.resource.professionals.marketplace.to.CreateAccountTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Spy
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Spy
    private ValidatorUtil validatorUtil = new ValidatorUtil();

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
        createAccountTO.setEmail("myemail@domain.com");
        createAccountTO.setPassword("PASSWORD");
    }

    /**
     * FTC0005
     */
    @Test
    public void testIgnoreAdminAccountCreation() {
        createAccountTO.setAccountType(AccountTypeEnum.ADMIN);

        String[] violatingFields = createAnAccountService.createAccount(createAccountTO);

        verify(createAnAccountService, never()).persistAccountAndApproval(any());
        assertArrayEquals(violatingFields, new String[]{"accountType"});
    }

    /**
     * FTC0008
     */
    @Test
    public void testIgnoreUnknownAccountCreation() {
        createAccountTO.setAccountType(AccountTypeEnum.valueToEnum("FAKE VALUE"));

        String[] violatingFields = createAnAccountService.createAccount(createAccountTO);

        verify(createAnAccountService, never()).persistAccountAndApproval(any());
        assertArrayEquals(violatingFields, new String[]{"accountType"});
    }

    /**
     * FTC0037
     */
    @Test
    public void testInvalidCreateAccountToWithValidAccountType() {
        createAccountTO.setResource(null);
        createAccountTO.setFirstName(null);
        createAccountTO.setLastName(null);
        createAccountTO.setEmail(null);
        createAccountTO.setEncodedPassword(null);
        createAccountTO.setPassword(null);
        createAccountTO.setIsApproved(null);

        createAccountTO.setAccountType(AccountTypeEnum.RESOURCE);

        String[] violatingFields = createAnAccountService.createAccount(createAccountTO);
        verify(createAnAccountService, never()).persistAccountAndApproval(any());

        assertThat(
                Arrays.asList("firstName", "lastName", "email", "password"),
                Matchers.containsInAnyOrder(violatingFields));
    }

    @Nested
    class PositiveFlow {
        @BeforeEach
        public void init() {
            when(enumUtil.accountTypeToEntity(any())).thenReturn(new AccountTypeEntity());
            when(accountRepository.findByEmailAndAccountType(any(), any())).thenReturn(null);

            doNothing().when(createAnAccountService).persistAccountAndApproval(any());
        }

        /**
         * FTC0006
         */
        @Test
        public void testAcknowledgeResourceAccountCreation() {
            createAccountTO.setAccountType(AccountTypeEnum.RESOURCE);

            createAnAccountService.createAccount(createAccountTO);

            verify(createAnAccountService, times(1)).persistAccountAndApproval(any());
        }

        /**
         * FTC0007
         */
        @Test
        public void testAcknowledgeProjectManagerAccountCreation() {
            createAccountTO.setAccountType(AccountTypeEnum.PROJECT_MANAGER);

            createAnAccountService.createAccount(createAccountTO);

            verify(createAnAccountService, times(1)).persistAccountAndApproval(any());
        }
    }
}
