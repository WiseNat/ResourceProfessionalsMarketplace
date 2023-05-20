import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.service.ResourceService;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class FT0008 {

    @InjectMocks
    private ResourceService resourceService;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private EnumUtil enumUtil;

    private ResourceEntity resourceEntity;

    private ResourceTO resourceTO;

    @BeforeEach
    public void init() {
        resourceEntity = new ResourceEntity();
        resourceTO = new ResourceTO();
    }

    @Test
    public void testUpdateDetailsWithNullSubRole() {
        resourceTO.setSubRole(null);

        resourceService.updateResourceDetails(resourceEntity, resourceTO);

        verify(resourceRepository).save(resourceEntity);
    }

    @Test
    public void testUpdateDetailsWithSubRole() {
        resourceTO.setSubRole(SubRoleEnum.BACKEND_DEVELOPER);

        resourceService.updateResourceDetails(resourceEntity, resourceTO);

        verify(resourceRepository).save(resourceEntity);
    }

}
