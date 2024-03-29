import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.*;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.service.AdminService;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ResourceUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Approving an approval through AdminUtil always deletes the approval and approves the unapproved account record. If
 * the approval entity pointed to a resource account type, then a resource record is also created for that account.
 */
@ExtendWith(MockitoExtension.class)
public class FT0006 {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private ApprovalRepository approvalRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ResourceUtil resourceUtil;

    @Mock
    private EnumUtil enumUtil;

    private ApprovalEntity approvalEntity;

    @BeforeEach
    public void init() {
        AccountTypeEntity accountTypeEntity = new AccountTypeEntity();

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setAccountType(accountTypeEntity);

        approvalEntity = new ApprovalEntity();
        approvalEntity.setAccount(accountEntity);

        doNothing().when(approvalRepository).delete(any());
    }

    /**
     * FTC0016
     */
    @Test
    public void testApprovingApprovalWithResourceAccountType() {
        approvalEntity.getAccount().getAccountType().setName(AccountTypeEnum.RESOURCE.value);

        when(resourceUtil.calculateDailyLateFee(any())).thenReturn(new BigDecimal("15.0"));
        when(enumUtil.mainRoleToEntity(any())).thenReturn(new MainRoleEntity());
        when(enumUtil.bandingToEntity(any())).thenReturn(new BandingEntity());

        adminService.approveApproval(approvalEntity);

        verify(resourceRepository, times(1)).save(any());
        verify(approvalRepository, times(1)).delete(any());
        verify(accountRepository, times(1)).save(any());
    }

    /**
     * FTC0017
     */
    @Test
    public void testApprovingApprovalWithProjectManagerAccountType() {
        approvalEntity.getAccount().getAccountType().setName(AccountTypeEnum.PROJECT_MANAGER.value);

        adminService.approveApproval(approvalEntity);

        verify(resourceRepository, never()).save(any());
        verify(approvalRepository, times(1)).delete(any());
        verify(accountRepository, times(1)).save(any());
    }
}
