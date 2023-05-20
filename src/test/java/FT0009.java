import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.constant.SubRoleEnum;
import com.wise.resource.professionals.marketplace.entity.BandingEntity;
import com.wise.resource.professionals.marketplace.entity.MainRoleEntity;
import com.wise.resource.professionals.marketplace.entity.SubRoleEntity;
import com.wise.resource.professionals.marketplace.repository.ResourceRepository;
import com.wise.resource.professionals.marketplace.to.LoanSearchTO;
import com.wise.resource.professionals.marketplace.to.ResourceCollectionTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.EnumUtil;
import com.wise.resource.professionals.marketplace.util.LoanUtil;
import com.wise.resource.professionals.marketplace.util.ResourceUtil;
import lombok.Data;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FT0009 {

    @InjectMocks
    private LoanUtil loanUtil;

    @Mock
    private ResourceRepository resourceRepository;

    @Mock
    private EnumUtil enumUtil;

    @Spy
    private ResourceUtil resourceUtil;

    private LoanSearchTO loanSearchTO;

    @BeforeEach
    public void init() {
        loanSearchTO = new LoanSearchTO();
    }

    @Test
    public void testGetLoanableResourcesWithNoneFound() {
        when(resourceRepository.findAllByCollectionWithPredicates(any(), any(), any(), any())).thenReturn(new ArrayList<>());

        List<ResourceCollectionTO> foundLoanableResources = loanUtil.getLoanables(loanSearchTO);

        assertTrue(foundLoanableResources.isEmpty());
    }

    @Test
    public void testGetLoanableResourcesWithOneResource() {

        BandingEnum banding = BandingEnum.BandOne;
        MainRoleEnum mainRole = MainRoleEnum.Developer;
        SubRoleEnum subRole = SubRoleEnum.FrontendDeveloper;
        BigDecimal costPerHour = new BigDecimal("10.0");

        List<ResourceRepository.IResourceCollection> foundResults = Collections.singletonList(
                new FakeIResourceCollection(1L, banding, mainRole, subRole, costPerHour)
        );

        when(resourceRepository.findAllByCollectionWithPredicates(any(), any(), any(), any())).thenReturn(foundResults);

        List<ResourceCollectionTO> foundLoanableResources = loanUtil.getLoanables(loanSearchTO);

        assertFalse(foundLoanableResources.isEmpty());

        ResourceTO expectedResourceTO = new ResourceTO();
        expectedResourceTO.setMainRole(mainRole);
        expectedResourceTO.setSubRole(subRole);
        expectedResourceTO.setBanding(banding);
        expectedResourceTO.setDailyLateFee(costPerHour.multiply(new BigDecimal("40")));
        expectedResourceTO.setCostPerHour(costPerHour);

        ResourceCollectionTO expectedResourceCollectionTO = new ResourceCollectionTO();
        expectedResourceCollectionTO.setResource(expectedResourceTO);
        expectedResourceCollectionTO.setQuantity(1);

        assertEquals(foundLoanableResources.get(0), expectedResourceCollectionTO);
    }

    @Test
    public void testGetLoanableResourcesWithOneResourceWithNullSubRole() {

        BandingEnum banding = BandingEnum.BandOne;
        MainRoleEnum mainRole = MainRoleEnum.UXDesigner;
        BigDecimal costPerHour = new BigDecimal("10.0");

        List<ResourceRepository.IResourceCollection> foundResults = Collections.singletonList(
                new FakeIResourceCollection(1L, banding, mainRole, null, costPerHour)
        );

        when(resourceRepository.findAllByCollectionWithPredicates(any(), any(), any(), any())).thenReturn(foundResults);

        List<ResourceCollectionTO> foundLoanableResources = loanUtil.getLoanables(loanSearchTO);

        assertFalse(foundLoanableResources.isEmpty());

        ResourceTO expectedResourceTO = new ResourceTO();
        expectedResourceTO.setMainRole(mainRole);
        expectedResourceTO.setSubRole(null);
        expectedResourceTO.setBanding(banding);
        expectedResourceTO.setDailyLateFee(costPerHour.multiply(new BigDecimal("40")));
        expectedResourceTO.setCostPerHour(costPerHour);

        ResourceCollectionTO expectedResourceCollectionTO = new ResourceCollectionTO();
        expectedResourceCollectionTO.setResource(expectedResourceTO);
        expectedResourceCollectionTO.setQuantity(1);

        assertEquals(foundLoanableResources.get(0), expectedResourceCollectionTO);
    }

    @Test
    public void testGetLoanableResourcesWithAggregableResources() {
        BandingEnum banding = BandingEnum.BandOne;
        MainRoleEnum mainRole = MainRoleEnum.Developer;
        SubRoleEnum subRole = SubRoleEnum.FrontendDeveloper;
        BigDecimal costPerHour = new BigDecimal("10.0");

        List<ResourceRepository.IResourceCollection> foundResults = Collections.singletonList(
                new FakeIResourceCollection(3L, banding, mainRole, subRole, costPerHour)
        );

        when(resourceRepository.findAllByCollectionWithPredicates(any(), any(), any(), any())).thenReturn(foundResults);

        List<ResourceCollectionTO> foundLoanableResources = loanUtil.getLoanables(loanSearchTO);

        assertFalse(foundLoanableResources.isEmpty());

        ResourceTO expectedResourceTO = new ResourceTO();
        expectedResourceTO.setMainRole(mainRole);
        expectedResourceTO.setSubRole(subRole);
        expectedResourceTO.setBanding(banding);
        expectedResourceTO.setDailyLateFee(costPerHour.multiply(new BigDecimal("40")));
        expectedResourceTO.setCostPerHour(costPerHour);

        ResourceCollectionTO expectedResourceCollectionTO = new ResourceCollectionTO();
        expectedResourceCollectionTO.setResource(expectedResourceTO);
        expectedResourceCollectionTO.setQuantity(3);

        assertEquals(foundLoanableResources.get(0), expectedResourceCollectionTO);
    }

    @Test
    public void testGetLoanableResourcesWithNonAggregableResources() {
        BandingEnum firstBanding = BandingEnum.BandOne;
        BandingEnum secondBanding = BandingEnum.BandFive;
        MainRoleEnum mainRole = MainRoleEnum.Developer;
        SubRoleEnum subRole = SubRoleEnum.FrontendDeveloper;
        BigDecimal costPerHour = new BigDecimal("10.0");

        List<ResourceRepository.IResourceCollection> foundResults = Arrays.asList(
                new FakeIResourceCollection(1L, firstBanding, mainRole, subRole, costPerHour),
                new FakeIResourceCollection(1L, secondBanding, mainRole, subRole, costPerHour)
        );

        when(resourceRepository.findAllByCollectionWithPredicates(any(), any(), any(), any())).thenReturn(foundResults);

        List<ResourceCollectionTO> foundLoanableResources = loanUtil.getLoanables(loanSearchTO);

        assertFalse(foundLoanableResources.isEmpty());

        ResourceTO firstExpectedResourceTO = new ResourceTO();
        firstExpectedResourceTO.setMainRole(mainRole);
        firstExpectedResourceTO.setSubRole(subRole);
        firstExpectedResourceTO.setBanding(firstBanding);
        firstExpectedResourceTO.setDailyLateFee(costPerHour.multiply(new BigDecimal("40")));
        firstExpectedResourceTO.setCostPerHour(costPerHour);

        ResourceCollectionTO firstExpectedResourceCollectionTO = new ResourceCollectionTO();
        firstExpectedResourceCollectionTO.setResource(firstExpectedResourceTO);
        firstExpectedResourceCollectionTO.setQuantity(1);

        ResourceTO secondExpectedResourceTO = new ResourceTO();
        secondExpectedResourceTO.setMainRole(mainRole);
        secondExpectedResourceTO.setSubRole(subRole);
        secondExpectedResourceTO.setBanding(secondBanding);
        secondExpectedResourceTO.setDailyLateFee(costPerHour.multiply(new BigDecimal("40")));
        secondExpectedResourceTO.setCostPerHour(costPerHour);

        ResourceCollectionTO secondExpectedResourceCollectionTO = new ResourceCollectionTO();
        secondExpectedResourceCollectionTO.setResource(secondExpectedResourceTO);
        secondExpectedResourceCollectionTO.setQuantity(1);

        assertThat(
                foundLoanableResources,
                Matchers.containsInAnyOrder(firstExpectedResourceCollectionTO, secondExpectedResourceCollectionTO));
    }

    @Test
    public void testGetLoanableResourcesWithVaryingAggregableResources() {
        BandingEnum firstBanding = BandingEnum.BandOne;
        BandingEnum secondBanding = BandingEnum.BandFive;
        MainRoleEnum mainRole = MainRoleEnum.Developer;
        SubRoleEnum subRole = SubRoleEnum.FrontendDeveloper;
        BigDecimal costPerHour = new BigDecimal("10.0");

        List<ResourceRepository.IResourceCollection> foundResults = Arrays.asList(
                new FakeIResourceCollection(3L, firstBanding, mainRole, subRole, costPerHour),
                new FakeIResourceCollection(5L, secondBanding, mainRole, subRole, costPerHour)
        );

        when(resourceRepository.findAllByCollectionWithPredicates(any(), any(), any(), any())).thenReturn(foundResults);

        List<ResourceCollectionTO> foundLoanableResources = loanUtil.getLoanables(loanSearchTO);

        assertFalse(foundLoanableResources.isEmpty());

        ResourceTO firstExpectedResourceTO = new ResourceTO();
        firstExpectedResourceTO.setMainRole(mainRole);
        firstExpectedResourceTO.setSubRole(subRole);
        firstExpectedResourceTO.setBanding(firstBanding);
        firstExpectedResourceTO.setDailyLateFee(costPerHour.multiply(new BigDecimal("40")));
        firstExpectedResourceTO.setCostPerHour(costPerHour);

        ResourceCollectionTO firstExpectedResourceCollectionTO = new ResourceCollectionTO();
        firstExpectedResourceCollectionTO.setResource(firstExpectedResourceTO);
        firstExpectedResourceCollectionTO.setQuantity(3);

        ResourceTO secondExpectedResourceTO = new ResourceTO();
        secondExpectedResourceTO.setMainRole(mainRole);
        secondExpectedResourceTO.setSubRole(subRole);
        secondExpectedResourceTO.setBanding(secondBanding);
        secondExpectedResourceTO.setDailyLateFee(costPerHour.multiply(new BigDecimal("40")));
        secondExpectedResourceTO.setCostPerHour(costPerHour);

        ResourceCollectionTO secondExpectedResourceCollectionTO = new ResourceCollectionTO();
        secondExpectedResourceCollectionTO.setResource(secondExpectedResourceTO);
        secondExpectedResourceCollectionTO.setQuantity(5);

        assertThat(
                foundLoanableResources,
                Matchers.containsInAnyOrder(firstExpectedResourceCollectionTO, secondExpectedResourceCollectionTO));
    }

    @Data
    private static class FakeIResourceCollection implements ResourceRepository.IResourceCollection {

        private Long count;
        private BandingEntity banding;
        private MainRoleEntity mainRole;
        private Optional<SubRoleEntity> subRole;
        private BigDecimal costPerHour;

        public FakeIResourceCollection(Long count, BandingEnum banding, MainRoleEnum mainRole, SubRoleEnum subRole, BigDecimal costPerHour) {
            this.count = count;

            this.banding = new BandingEntity();
            this.banding.setName(banding.value);

            this.mainRole = new MainRoleEntity();
            this.mainRole.setName(mainRole.value);

            if (subRole != null) {
                this.subRole = Optional.of(new SubRoleEntity());
                this.subRole.get().setName(subRole.value);
            } else {
                this.subRole = Optional.empty();
            }

            this.costPerHour = costPerHour;
        }

    }
}
