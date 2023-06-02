
package org.apache.fineract.infrastructure.core.filters;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import java.util.UUID;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.fineract.infrastructure.core.config.FineractProperties;
import org.apache.fineract.infrastructure.core.service.MDCWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CorrelationHeaderFilterTest {
    private static final String CORRELATION_ID_HEADER_NAME = "X-CORR-ID";
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain filterChain;
    @Mock
    private MDCWrapper mdcWrapper;
    private FineractProperties fineractProperties;
    private CorrelationHeaderFilter underTest;
    @BeforeEach
    void setUp() {
        fineractProperties = new FineractProperties();
        FineractProperties.FineractCorrelationProperties correlationProps = new FineractProperties.FineractCorrelationProperties();
        correlationProps.setHeaderName(CORRELATION_ID_HEADER_NAME);
        correlationProps.setEnabled(true);
        fineractProperties.setCorrelation(correlationProps);
        underTest = new CorrelationHeaderFilter(fineractProperties, mdcWrapper);
    }
    @Test
    public void testDoFilterInternalShouldPutCorrelationIdIntoMDCIfHeaderIsPresentAndEnabled() throws Exception {
        String correlationId = UUID.randomUUID().toString();
        given(request.getHeader(fineractProperties.getCorrelation().getHeaderName())).willReturn(correlationId);
        underTest.doFilterInternal(request, response, filterChain);
        verify(mdcWrapper).put(CorrelationHeaderFilter.CORRELATION_ID_KEY, correlationId);
        verify(mdcWrapper).remove(CorrelationHeaderFilter.CORRELATION_ID_KEY);
        verify(filterChain).doFilter(request, response);
    }
    @Test
    public void testDoFilterInternalShouldNotPutCorrelationIdIntoMDCIfHeaderIsNotPresentAndEnabled() throws Exception {
        given(request.getHeader(fineractProperties.getCorrelation().getHeaderName())).willReturn(null);
        underTest.doFilterInternal(request, response, filterChain);
        verify(mdcWrapper, never()).put(anyString(), anyString());
        verify(mdcWrapper).remove(CorrelationHeaderFilter.CORRELATION_ID_KEY);
        verify(filterChain).doFilter(request, response);
    }
    @Test
    public void testDoFilterInternalShouldNotPutCorrelationIdIntoMDCIfHeaderIsPresentButWhitespacesAndEnabled() throws Exception {
        given(request.getHeader(fineractProperties.getCorrelation().getHeaderName())).willReturn("    ");
        underTest.doFilterInternal(request, response, filterChain);
        verify(mdcWrapper, never()).put(anyString(), anyString());
        verify(mdcWrapper).remove(CorrelationHeaderFilter.CORRELATION_ID_KEY);
        verify(filterChain).doFilter(request, response);
    }
    @Test
    public void testDoFilterInternalShouldNotPutCorrelationIdIntoMDCIfHeaderIsPresentAndDisabled() throws Exception {
        fineractProperties.getCorrelation().setEnabled(false);
        String correlationId = UUID.randomUUID().toString();
        given(request.getHeader(fineractProperties.getCorrelation().getHeaderName())).willReturn(correlationId);
        underTest.doFilterInternal(request, response, filterChain);
        verifyNoInteractions(mdcWrapper);
        verify(filterChain).doFilter(request, response);
    }
}
