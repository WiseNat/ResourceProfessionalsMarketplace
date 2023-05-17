import com.wise.resource.professionals.marketplace.util.ProjectManagerUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FT0009 {

    @InjectMocks
    private ProjectManagerUtil projectManagerUtil;

    @BeforeEach
    public void init() {
    }

    @Test
    public void testGetLoanableResourcesWithNoPredicates() {}

    @Test
    public void testGetLoanableResourcesWithOnlyResource() {}

    @Test
    public void testGetLoanableResourcesWithOnlyProjectManager() {}

    @Test
    public void testGetLoanableResourcesWithResourceAndProjectManager() {}
}
