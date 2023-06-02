
package org.apache.fineract.infrastructure.businessdate.api;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.ws.rs.core.UriInfo;
import org.apache.fineract.commands.service.PortfolioCommandSourceWritePlatformService;
import org.apache.fineract.infrastructure.businessdate.data.BusinessDateData;
import org.apache.fineract.infrastructure.businessdate.service.BusinessDateReadPlatformService;
import org.apache.fineract.infrastructure.core.api.ApiRequestParameterHelper;
import org.apache.fineract.infrastructure.core.data.CommandProcessingResult;
import org.apache.fineract.infrastructure.core.serialization.ApiRequestJsonSerializationSettings;
import org.apache.fineract.infrastructure.core.serialization.DefaultToApiJsonSerializer;
import org.apache.fineract.infrastructure.security.exception.NoAuthorizationException;
import org.apache.fineract.infrastructure.security.service.PlatformSecurityContext;
import org.apache.fineract.useradministration.domain.AppUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class BusinessDateApiTest {
    @Mock
    private ApiRequestParameterHelper parameterHelper;
    @Mock
    private BusinessDateReadPlatformService readPlatformService;
    @Mock
    private UriInfo uriInfo;
    @Mock
    private PlatformSecurityContext securityContext;
    @Mock
    private DefaultToApiJsonSerializer<BusinessDateData> jsonSerializer;
    @Mock
    private PortfolioCommandSourceWritePlatformService commandWritePlatformService;
    @InjectMocks
    private BusinessDateApiResource underTest;
    private ApiRequestJsonSerializationSettings apiRequestJsonSerializationSettings;
    @BeforeEach
    void setUp() throws IOException {
        apiRequestJsonSerializationSettings = new ApiRequestJsonSerializationSettings(false, null, false, false, false);
        given(parameterHelper.process(Mockito.any())).willReturn(apiRequestJsonSerializationSettings);
    }
    @Test
    void getBusinessDatesAPIHasPermission() throws ServletException, IOException {
        AppUser appUser = Mockito.mock(AppUser.class);
        List<BusinessDateData> response = Mockito.mock(List.class);
        given(readPlatformService.findAll()).willReturn(response);
        Mockito.doNothing().when(appUser).validateHasReadPermission("BUSINESS_DATE");
        given(securityContext.authenticatedUser()).willReturn(appUser);
        underTest.getBusinessDates(uriInfo);
        verify(readPlatformService, Mockito.times(1)).findAll();
        verify(jsonSerializer, Mockito.times(1)).serialize(apiRequestJsonSerializationSettings, response);
    }
    @Test
    void getBusinessDatesAPIHasNoPermission() throws ServletException, IOException {
        AppUser appUser = Mockito.mock(AppUser.class);
        Mockito.doThrow(NoAuthorizationException.class).when(appUser).validateHasReadPermission("BUSINESS_DATE");
        given(securityContext.authenticatedUser()).willReturn(appUser);
        assertThatThrownBy(() -> underTest.getBusinessDates(uriInfo)).isInstanceOf(NoAuthorizationException.class);
        verifyNoInteractions(readPlatformService);
    }
    @Test
    void getBusinessDateByTypeAPIHasPermission() throws ServletException, IOException {
        AppUser appUser = Mockito.mock(AppUser.class);
        BusinessDateData response = Mockito.mock(BusinessDateData.class);
        given(readPlatformService.findByType("type")).willReturn(response);
        Mockito.doNothing().when(appUser).validateHasReadPermission("BUSINESS_DATE");
        given(securityContext.authenticatedUser()).willReturn(appUser);
        underTest.getBusinessDate("type", uriInfo);
        verify(readPlatformService, Mockito.times(1)).findByType("type");
        verify(jsonSerializer, Mockito.times(1)).serialize(apiRequestJsonSerializationSettings, response);
    }
    @Test
    void getBusinessDateByTypeAPIHasNoPermission() throws ServletException, IOException {
        AppUser appUser = Mockito.mock(AppUser.class);
        Mockito.doThrow(NoAuthorizationException.class).when(appUser).validateHasReadPermission("BUSINESS_DATE");
        given(securityContext.authenticatedUser()).willReturn(appUser);
        assertThatThrownBy(() -> underTest.getBusinessDate("type", uriInfo)).isInstanceOf(NoAuthorizationException.class);
        verifyNoInteractions(readPlatformService);
    }
    @Test
    void postBusinessDateAPIHasPermission() throws ServletException, IOException {
        AppUser appUser = Mockito.mock(AppUser.class);
        CommandProcessingResult response = Mockito.mock(CommandProcessingResult.class);
        Mockito.doNothing().when(appUser).validateHasUpdatePermission("BUSINESS_DATE");
        given(securityContext.authenticatedUser()).willReturn(appUser);
        given(commandWritePlatformService.logCommandSource(Mockito.any())).willReturn(response);
        underTest.updateBusinessDate("{}", uriInfo);
        verify(commandWritePlatformService, Mockito.times(1)).logCommandSource(Mockito.any());
        verify(jsonSerializer, Mockito.times(1)).serialize(response);
    }
    @Test
    void postBusinessDateAPIHasNoPermission() throws ServletException, IOException {
        AppUser appUser = Mockito.mock(AppUser.class);
        Mockito.doThrow(NoAuthorizationException.class).when(appUser).validateHasUpdatePermission("BUSINESS_DATE");
        given(securityContext.authenticatedUser()).willReturn(appUser);
        assertThatThrownBy(() -> underTest.updateBusinessDate("{}", uriInfo)).isInstanceOf(NoAuthorizationException.class);
        verifyNoInteractions(readPlatformService);
    }
}
