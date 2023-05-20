import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.*;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.util.AdminUtil;
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

@ExtendWith(MockitoExtension.class)
public class FT0006 {

    @InjectMocks
    private AdminUtil adminUtil;

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

    @Test
    public void testApprovingApprovalWithResourceAccountType() {
        approvalEntity.getAccount().getAccountType().setName(AccountTypeEnum.Resource.value);

        when(resourceUtil.calculateDailyLateFee(any())).thenReturn(new BigDecimal("15.0"));
        when(enumUtil.mainRoleToEntity(any())).thenReturn(new MainRoleEntity());
        when(enumUtil.bandingToEntity(any())).thenReturn(new BandingEntity());

        adminUtil.approveApproval(approvalEntity);

        verify(resourceRepository, times(1)).save(any());
        verify(approvalRepository, times(1)).delete(any());
        verify(accountRepository, times(1)).save(any());
    }

    @Test
    public void testApprovingApprovalWithProjectManagerAccountType() {
        approvalEntity.getAccount().getAccountType().setName(AccountTypeEnum.ProjectManager.value);

        adminUtil.approveApproval(approvalEntity);

        verify(resourceRepository, never()).save(any());
        verify(approvalRepository, times(1)).delete(any());
        verify(accountRepository, times(1)).save(any());
    }
}
