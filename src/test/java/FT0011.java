import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.AccountEntity;
import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.service.ReturnService;
import com.wise.resource.professionals.marketplace.to.ReturnSearchTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * A list of returnable resource entities is found based on the given search predicates.
 */
@ExtendWith(MockitoExtension.class)
public class FT0011 {

    @InjectMocks
    private ReturnService returnService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private EnumUtil enumUtil;

    private ReturnSearchTO returnSearchTO;

    @BeforeEach
    public void init() {
        returnSearchTO = new ReturnSearchTO();

        when(enumUtil.bandingToEntity(any())).thenReturn(new BandingEntity());
        when(enumUtil.mainRoleToEntity(any())).thenReturn(new MainRoleEntity());
        when(enumUtil.subRoleToEntity(any())).thenReturn(new SubRoleEntity());
    }

    @Test
    public void testGetLoanedResourcesWithNoneFound() {
        when(accountRepository.findAllWithPredicates(any(), any(), any(), any(), any(), any())).thenReturn(new ArrayList<>());

        List<AccountEntity> foundReturnables = returnService.getReturnables(returnSearchTO);

        assertTrue(foundReturnables.isEmpty());
    }

    @Test
    public void testGetLoanedResourcesWithNoResources() {

        when(accountRepository.findAllWithPredicates(any(), any(), any(), any(), any(), any())).thenReturn(new ArrayList<>());

        returnSearchTO.setFirstName("");
        returnSearchTO.setLastName("");
        returnSearchTO.setClient("");
        returnSearchTO.setSubRole(SubRoleEnum.FRONTEND_DEVELOPER);
        returnSearchTO.setMainRole(MainRoleEnum.DEVELOPER);
        returnSearchTO.setBanding(BandingEnum.BAND_ONE);

        List<AccountEntity> foundReturnables = returnService.getReturnables(returnSearchTO);

        assertTrue(foundReturnables.isEmpty());
    }

    @Test
    public void testGetLoanedResourcesWithOneResource() {

        when(accountRepository.findAllWithPredicates(any(), any(), any(), any(), any(), any())).thenReturn(Collections.singletonList(new AccountEntity()));

        returnSearchTO.setFirstName("");
        returnSearchTO.setLastName("");
        returnSearchTO.setClient("");
        returnSearchTO.setSubRole(SubRoleEnum.FRONTEND_DEVELOPER);
        returnSearchTO.setMainRole(MainRoleEnum.DEVELOPER);
        returnSearchTO.setBanding(BandingEnum.BAND_ONE);

        List<AccountEntity> foundReturnables = returnService.getReturnables(returnSearchTO);

        assertFalse(foundReturnables.isEmpty());
    }
}
