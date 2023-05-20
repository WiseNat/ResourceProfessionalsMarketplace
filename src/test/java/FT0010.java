import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.service.ProjectManagerService;
import com.wise.resource.professionals.marketplace.to.LoanTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.ResourceUtil;
import lombok.Data;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FT0010 {

    @Captor
    private ArgumentCaptor<ResourceEntity> resourceEntityCaptor;
    @InjectMocks
    private ProjectManagerService projectManagerService;
    @Spy
    private ResourceUtil resourceUtil;
    @Mock
    private ResourceRepository resourceRepository;
    @Mock
    private EnumUtil enumUtil;

    @BeforeEach
    public void init() {
        when(enumUtil.bandingToEntity(any())).thenReturn(new BandingEntity());
        when(enumUtil.mainRoleToEntity(any())).thenReturn(new MainRoleEntity());
        when(enumUtil.subRoleToEntity(any())).thenReturn(new SubRoleEntity());
    }

    @Test
    public void testLoanResourceWithValidLoanTO() {

        int availableResources = 10;
        int resourcesToLoan = 2;

        BloatedLoanTO bloatedLoanTO = new BloatedLoanTO(
                BandingEnum.BAND_ONE,
                MainRoleEnum.DEVELOPER,
                SubRoleEnum.FRONTEND_DEVELOPER,
                new BigDecimal("15.5"),
                availableResources,
                resourcesToLoan,
                "Fake Client",
                3
        );

        List<ResourceEntity> fakeResourceEntities = bloatedLoanTO.getFakeResourceEntityList();

        when(resourceRepository.findByBandingAndMainRoleAndSubRoleAndCostPerHour(any(), any(), any(), any()))
                .thenReturn(fakeResourceEntities);

        projectManagerService.loanResource(bloatedLoanTO.getLoanTO());


        verify(resourceRepository, times(resourcesToLoan)).save(resourceEntityCaptor.capture());

        assertThat(
                fakeResourceEntities,
                Matchers.hasItems(resourceEntityCaptor.getAllValues().toArray(new ResourceEntity[0])));

        assertEquals(resourceEntityCaptor.getAllValues().size(), resourcesToLoan);
    }

    @Test
    public void testLoanResourceWithNotEnoughAvailableResourcesAndNullSubRole() {
        int availableResources = 3;
        int resourcesToLoan = 5;

        BloatedLoanTO bloatedLoanTO = new BloatedLoanTO(
                BandingEnum.BAND_ONE,
                MainRoleEnum.DEVELOPER,
                null,
                new BigDecimal("15.5"),
                availableResources,
                resourcesToLoan,
                "Fake Client",
                3
        );

        List<ResourceEntity> fakeResourceEntities = bloatedLoanTO.getFakeResourceEntityList();

        when(resourceRepository.findByBandingAndMainRoleAndSubRoleAndCostPerHour(any(), any(), any(), any()))
                .thenReturn(fakeResourceEntities);

        projectManagerService.loanResource(bloatedLoanTO.getLoanTO());


        verify(resourceRepository, times(availableResources)).save(resourceEntityCaptor.capture());

        assertThat(
                fakeResourceEntities,
                Matchers.hasItems(resourceEntityCaptor.getAllValues().toArray(new ResourceEntity[0])));

        assertEquals(resourceEntityCaptor.getAllValues().size(), availableResources);
    }

    @Data
    private class BloatedLoanTO {

        private LoanTO loanTO;

        public BloatedLoanTO(BandingEnum banding, MainRoleEnum mainRole, SubRoleEnum subRole, BigDecimal costPerHour, int availableResources, int resourcesToLoan, String clientName, int availabilityDateYearsFromToday) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date(System.currentTimeMillis()));
            cal.add(Calendar.YEAR, availabilityDateYearsFromToday);

            ResourceTO resourceTO = new ResourceTO();
            resourceTO.setBanding(banding);
            resourceTO.setMainRole(mainRole);
            resourceTO.setSubRole(subRole);
            resourceTO.setCostPerHour(costPerHour);
            resourceTO.setDailyLateFee(resourceUtil.calculateDailyLateFee(costPerHour));

            ResourceCollectionTO resourceCollectionTO = new ResourceCollectionTO();
            resourceCollectionTO.setResource(resourceTO);
            resourceCollectionTO.setQuantity(availableResources);

            loanTO = new LoanTO();
            loanTO.setResourceCollectionTO(resourceCollectionTO);
            loanTO.setClientName(clientName);
            loanTO.setAmount(resourcesToLoan);
            loanTO.setAvailabilityDate(cal.getTime());
        }

        public List<ResourceEntity> getFakeResourceEntityList() {
            List<ResourceEntity> resourceEntities = new ArrayList<>();

            ResourceTO resourceTO = loanTO.getResourceCollectionTO().getResource();

            BandingEntity banding = new BandingEntity();
            MainRoleEntity mainRole = new MainRoleEntity();
            SubRoleEntity subRole = null;

            banding.setName(resourceTO.getBanding().value);
            mainRole.setName(resourceTO.getMainRole().value);

            if (resourceTO.getSubRole() != null) {
                subRole = new SubRoleEntity();
                subRole.setName(resourceTO.getSubRole().value);
            }

            for (int i = 0; i < loanTO.getResourceCollectionTO().getQuantity(); i++) {
                ResourceEntity resourceEntity = new ResourceEntity();
                resourceEntity.setBanding(banding);
                resourceEntity.setMainRole(mainRole);
                resourceEntity.setSubRole(subRole);
                resourceEntity.setDailyLateFee(resourceTO.getDailyLateFee());
                resourceEntity.setCostPerHour(resourceTO.getCostPerHour());
                resourceEntity.setId((long) i);

                resourceEntities.add(resourceEntity);
            }

            return resourceEntities;
        }
    }
}
