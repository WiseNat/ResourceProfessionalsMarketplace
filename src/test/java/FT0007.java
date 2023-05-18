import com.wise.resource.professionals.marketplace.constant.BandingEnum;
import com.wise.resource.professionals.marketplace.constant.MainRoleEnum;
import com.wise.resource.professionals.marketplace.entity.ResourceEntity;
import com.wise.resource.professionals.marketplace.to.InvalidFieldsAndDataTO;
import com.wise.resource.professionals.marketplace.to.RawResourceTO;
import com.wise.resource.professionals.marketplace.to.ResourceTO;
import com.wise.resource.professionals.marketplace.util.ResourceUtil;
import com.wise.resource.professionals.marketplace.util.ValidatorUtil;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static com.wise.resource.professionals.marketplace.constant.RoleMapping.ROLE_MAPPING;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FT0007 {

    @InjectMocks
    private ResourceUtil resourceUtil;

    @Spy
    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Spy
    private ValidatorUtil validatorUtil;

    @BeforeEach
    public void init() {
    }

    @Test
    public void testCalculateDailyLateFee() {
        BigDecimal costPerHour = new BigDecimal("10");

        BigDecimal dailyLateFee = resourceUtil.calculateDailyLateFee(costPerHour);
        BigDecimal expectedDailyLateFee = new BigDecimal("400");

        assertEquals(dailyLateFee, expectedDailyLateFee);
    }

    @Nested
    class CreateResourceTO {

        private RawResourceTO rawResourceTO;

        private ResourceEntity resourceEntity;

        private BigDecimal expectedDailyLateFee;

        @BeforeEach
        public void init() {
            resourceEntity = new ResourceEntity();
            resourceEntity.setDailyLateFee(new BigDecimal("600"));
            resourceEntity.setCostPerHour(new BigDecimal("15"));
            resourceEntity.setLoanedClient(null);
            resourceEntity.setAvailabilityDate(null);

            MainRoleEnum mainRoleEnum = MainRoleEnum.Developer;
            String mainRole = mainRoleEnum.value;
            String subRole = ROLE_MAPPING.get(mainRoleEnum)[0].value;

            String banding = BandingEnum.BandTwo.value;
            String costPerHour = "10.5";

            rawResourceTO = new RawResourceTO(resourceEntity, mainRole, subRole, banding, costPerHour);

            expectedDailyLateFee = new BigDecimal("40.0").multiply(new BigDecimal(rawResourceTO.getCostPerHour()));
        }

        @Test
        public void testCreateResourceToWithValidUpdateResourceTO() {
            InvalidFieldsAndDataTO<ResourceTO> invalidFieldsAndDataTO = resourceUtil.createResourceTo(rawResourceTO);

            ResourceTO resourceTO = invalidFieldsAndDataTO.getData();

            assertNotNull(resourceTO);
            assertEquals(resourceTO.getBanding().value, rawResourceTO.getBanding());
            assertEquals(resourceTO.getSubRole().value, rawResourceTO.getSubRole());
            assertEquals(resourceTO.getMainRole().value, rawResourceTO.getMainRole());
            assertEquals(resourceTO.getLoanedClient(), resourceEntity.getLoanedClient());
            assertThat(resourceTO.getDailyLateFee(), Matchers.comparesEqualTo(expectedDailyLateFee));
            assertEquals(resourceTO.getCostPerHour().toPlainString(), rawResourceTO.getCostPerHour());
            assertEquals(resourceTO.getAvailabilityDate(), resourceEntity.getAvailabilityDate());
        }

        @Test
        public void testCreateResourceToWithEmptyUpdateResourceTO() {
            rawResourceTO.setMainRole("");
            rawResourceTO.setSubRole("");
            rawResourceTO.setBanding("");
            rawResourceTO.setCostPerHour("");

            InvalidFieldsAndDataTO<ResourceTO> invalidFieldsAndDataTO = resourceUtil.createResourceTo(rawResourceTO);

            ResourceTO resourceTO = invalidFieldsAndDataTO.getData();
            String[] invalidFields = invalidFieldsAndDataTO.getInvalidFields();

            assertNull(resourceTO);
            assertThat(Arrays.asList("mainRole", "banding", "costPerHour"), Matchers.containsInAnyOrder(invalidFields));
        }

        @Test
        public void testCreateResourceToWithEmptyMainRoleInUpdateResourceTO() {
            rawResourceTO.setMainRole("");

            InvalidFieldsAndDataTO<ResourceTO> invalidFieldsAndDataTO = resourceUtil.createResourceTo(rawResourceTO);

            ResourceTO resourceTO = invalidFieldsAndDataTO.getData();
            String[] invalidFields = invalidFieldsAndDataTO.getInvalidFields();

            assertNull(resourceTO);
            assertThat(Collections.singletonList("mainRole"), Matchers.containsInAnyOrder(invalidFields));
        }

        @Test
        public void testCreateResourceToWithEmptyButRequiredSubRoleInUpdateResourceTO() {
            rawResourceTO.setSubRole("");

            InvalidFieldsAndDataTO<ResourceTO> invalidFieldsAndDataTO = resourceUtil.createResourceTo(rawResourceTO);

            ResourceTO resourceTO = invalidFieldsAndDataTO.getData();
            String[] invalidFields = invalidFieldsAndDataTO.getInvalidFields();

            assertNull(resourceTO);
            assertThat(Arrays.asList("mainRole", "subRole"), Matchers.containsInAnyOrder(invalidFields));
        }

        @Test
        public void testCreateResourceToWithEmptyButUnwantedSubRoleInUpdateResourceTO() {
            rawResourceTO.setMainRole(MainRoleEnum.UXDesigner.value);
            rawResourceTO.setSubRole("");

            InvalidFieldsAndDataTO<ResourceTO> invalidFieldsAndDataTO = resourceUtil.createResourceTo(rawResourceTO);

            ResourceTO resourceTO = invalidFieldsAndDataTO.getData();

            assertNotNull(resourceTO);
            assertEquals(resourceTO.getBanding().value, rawResourceTO.getBanding());
            assertNull(resourceTO.getSubRole());
            assertEquals(resourceTO.getMainRole().value, rawResourceTO.getMainRole());
            assertEquals(resourceTO.getLoanedClient(), resourceEntity.getLoanedClient());
            assertThat(resourceTO.getDailyLateFee(), Matchers.comparesEqualTo(expectedDailyLateFee));
            assertEquals(resourceTO.getCostPerHour().toPlainString(), rawResourceTO.getCostPerHour());
            assertEquals(resourceTO.getAvailabilityDate(), resourceEntity.getAvailabilityDate());
        }
    }
}
