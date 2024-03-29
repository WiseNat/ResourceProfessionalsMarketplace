import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.repository.AccountRepository;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Denying an approval through AdminUtil always deletes the approval and deletes the unapproved account records.
 */
@ExtendWith(MockitoExtension.class)
public class FT0005 {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private ApprovalRepository approvalRepository;

    @Mock
    private AccountRepository accountRepository;

    private ApprovalEntity approvalEntity;

    @BeforeEach
    public void init() {
        approvalEntity = new ApprovalEntity();
    }

    /**
     * FTC0015
     */
    @Test
    public void testDenyingApproval() {
        adminService.denyApproval(approvalEntity);

        verify(approvalRepository, times(1)).delete(approvalEntity);
        verify(accountRepository, times(1)).delete(approvalEntity.getAccount());
    }
}
