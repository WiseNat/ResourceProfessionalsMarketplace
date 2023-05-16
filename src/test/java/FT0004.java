import com.wise.resource.professionals.marketplace.constant.AccountTypeEnum;
import com.wise.resource.professionals.marketplace.entity.AccountTypeEntity;
import com.wise.resource.professionals.marketplace.entity.ApprovalEntity;
import com.wise.resource.professionals.marketplace.repository.ApprovalRepository;
import com.wise.resource.professionals.marketplace.to.ApprovalSearchTO;
import com.wise.resource.professionals.marketplace.util.AdminUtil;
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

@ExtendWith(MockitoExtension.class)
public class FT0004 {

    @InjectMocks
    private AdminUtil adminUtil;

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

    @Test
    public void testGetApprovalsWithNoPredicates() {
        List<ApprovalEntity> foundApprovals = adminUtil.getApprovals(approvalSearchTO);

        assertTrue(foundApprovals.isEmpty());
    }

    @Test
    public void testGetApprovalsWithOnlyResource() {
        approvalSearchTO.setResourceAllowed(true);

        List<ApprovalEntity> fakeApprovalEntities = Arrays.asList(new ApprovalEntity(), new ApprovalEntity());
        when(approvalRepository.findApprovalsByPredicatesAndAccountType(any(), any(), any(), any())).thenReturn(fakeApprovalEntities);
        when(enumUtil.accountTypeToEntity(any())).thenReturn(new AccountTypeEntity());

        List<ApprovalEntity> foundApprovals = adminUtil.getApprovals(approvalSearchTO);

        verify(enumUtil, times(1)).accountTypeToEntity(AccountTypeEnum.Resource);
        verify(approvalRepository, times(1)).findApprovalsByPredicatesAndAccountType(
                any(), any(), any(), any()
        );

        assertFalse(foundApprovals.isEmpty());
    }

    @Test
    public void testGetApprovalsWithOnlyProjectManager() {
        approvalSearchTO.setProjectManagerAllowed(true);

        List<ApprovalEntity> fakeApprovalEntities = Arrays.asList(new ApprovalEntity(), new ApprovalEntity());
        when(approvalRepository.findApprovalsByPredicatesAndAccountType(any(), any(), any(), any())).thenReturn(fakeApprovalEntities);
        when(enumUtil.accountTypeToEntity(any())).thenReturn(new AccountTypeEntity());

        List<ApprovalEntity> foundApprovals = adminUtil.getApprovals(approvalSearchTO);

        verify(enumUtil, times(1)).accountTypeToEntity(AccountTypeEnum.ProjectManager);
        verify(approvalRepository, times(1)).findApprovalsByPredicatesAndAccountType(
                any(), any(), any(), any()
        );

        assertFalse(foundApprovals.isEmpty());
    }

    @Test
    public void testGetApprovalsWithResourceAndProjectManager() {
        approvalSearchTO.setResourceAllowed(true);
        approvalSearchTO.setProjectManagerAllowed(true);

        List<ApprovalEntity> fakeApprovalEntities = Arrays.asList(new ApprovalEntity(), new ApprovalEntity());
        when(approvalRepository.findAllApprovalsByPredicates(any(), any(), any())).thenReturn(fakeApprovalEntities);

        List<ApprovalEntity> foundApprovals = adminUtil.getApprovals(approvalSearchTO);

        verify(approvalRepository, times(1)).findAllApprovalsByPredicates(any(), any(), any());

        assertFalse(foundApprovals.isEmpty());
    }
}
