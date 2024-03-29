import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.service.AdminService;
import com.wise.resource.professionals.marketplace.to.ApprovalSearchTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * A list of approval entities is found based on the given search predicates.
 */
@ExtendWith(MockitoExtension.class)
public class FT0004 {

    @InjectMocks
    private AdminService adminService;

    @Spy
    private ApprovalRepository approvalRepository;

    @Spy
    private EnumUtil enumUtil;

    private ApprovalSearchTO approvalSearchTO;

    @BeforeEach
    public void init() {
        String firstName = "SomeFirstName";
        String lastName = "SomeSurname";
        String email = "myFake@email.com";

        boolean isResourceAllowed = false;
        boolean isProjectManagerAllowed = false;

        approvalSearchTO = new ApprovalSearchTO(
                isResourceAllowed, isProjectManagerAllowed, firstName, lastName, email);
    }

    /**
     * FTC0011
     */
    @Test
    public void testGetApprovalsWithNoPredicates() {
        List<ApprovalEntity> foundApprovals = adminService.getApprovals(approvalSearchTO);

        assertTrue(foundApprovals.isEmpty());
    }

    /**
     * FTC0012
     */
    @Test
    public void testGetApprovalsWithOnlyResource() {
        approvalSearchTO.setResourceAllowed(true);

        List<ApprovalEntity> fakeApprovalEntities = Arrays.asList(new ApprovalEntity(), new ApprovalEntity());

        when(approvalRepository.findApprovalsByPredicatesAndAccountType(any(), any(), any(), any())).thenReturn(fakeApprovalEntities);
        when(enumUtil.accountTypeToEntity(any())).thenReturn(new AccountTypeEntity());

        List<ApprovalEntity> foundApprovals = adminService.getApprovals(approvalSearchTO);

        verify(enumUtil, times(1)).accountTypeToEntity(AccountTypeEnum.RESOURCE);
        verify(approvalRepository, times(1)).findApprovalsByPredicatesAndAccountType(
                any(), any(), any(), any()
        );

        assertFalse(foundApprovals.isEmpty());
    }

    /**
     * FTC0013
     */
    @Test
    public void testGetApprovalsWithOnlyProjectManager() {
        approvalSearchTO.setProjectManagerAllowed(true);

        List<ApprovalEntity> fakeApprovalEntities = Arrays.asList(new ApprovalEntity(), new ApprovalEntity());

        when(approvalRepository.findApprovalsByPredicatesAndAccountType(any(), any(), any(), any())).thenReturn(fakeApprovalEntities);
        when(enumUtil.accountTypeToEntity(any())).thenReturn(new AccountTypeEntity());

        List<ApprovalEntity> foundApprovals = adminService.getApprovals(approvalSearchTO);

        verify(enumUtil, times(1)).accountTypeToEntity(AccountTypeEnum.PROJECT_MANAGER);
        verify(approvalRepository, times(1)).findApprovalsByPredicatesAndAccountType(
                any(), any(), any(), any()
        );

        assertFalse(foundApprovals.isEmpty());
    }

    /**
     * FTC0014
     */
    @Test
    public void testGetApprovalsWithResourceAndProjectManager() {
        approvalSearchTO.setResourceAllowed(true);
        approvalSearchTO.setProjectManagerAllowed(true);

        List<ApprovalEntity> fakeApprovalEntities = Arrays.asList(new ApprovalEntity(), new ApprovalEntity());
        when(approvalRepository.findAllApprovalsByPredicates(any(), any(), any())).thenReturn(fakeApprovalEntities);

        List<ApprovalEntity> foundApprovals = adminService.getApprovals(approvalSearchTO);

        verify(approvalRepository, times(1)).findAllApprovalsByPredicates(any(), any(), any());

        assertFalse(foundApprovals.isEmpty());
    }
}
