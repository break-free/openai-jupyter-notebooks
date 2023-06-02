
package org.apache.fineract.infrastructure.security.filter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.fineract.infrastructure.businessdate.domain.BusinessDateType;
import org.apache.fineract.infrastructure.businessdate.service.BusinessDateReadPlatformService;
import org.apache.fineract.infrastructure.cache.domain.CacheType;
import org.apache.fineract.infrastructure.cache.service.CacheWritePlatformService;
import org.apache.fineract.infrastructure.configuration.domain.ConfigurationDomainService;
import org.apache.fineract.infrastructure.core.domain.FineractPlatformTenant;
import org.apache.fineract.infrastructure.core.serialization.ToApiJsonSerializer;
import org.apache.fineract.infrastructure.core.service.ThreadLocalContextUtil;
import org.apache.fineract.infrastructure.security.data.PlatformRequestLog;
import org.apache.fineract.infrastructure.security.exception.InvalidTenantIdentifierException;
import org.apache.fineract.infrastructure.security.service.BasicAuthTenantDetailsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;
@Service
@ConditionalOnProperty("fineract.security.oauth.enabled")
@RequiredArgsConstructor
@Slf4j
public class TenantAwareTenantIdentifierFilter extends GenericFilterBean {
    private static AtomicBoolean firstRequestProcessed = new AtomicBoolean();
    private final BasicAuthTenantDetailsService basicAuthTenantDetailsService;
    private final ToApiJsonSerializer<PlatformRequestLog> toApiJsonSerializer;
    private final ConfigurationDomainService configurationDomainService;
    private final CacheWritePlatformService cacheWritePlatformService;
    private final BusinessDateReadPlatformService businessDateReadPlatformService;
    private final String tenantRequestHeader = "Fineract-Platform-TenantId";
    private final boolean exceptionIfHeaderMissing = true;
    private final String apiUri = "/api/v1/";
    @Override
    public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) req;
        final HttpServletResponse response = (HttpServletResponse) res;
        final StopWatch task = new StopWatch();
        task.start();
        try {
            response.setHeader("Access-Control-Allow-Origin", "*"); 
            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            final String reqHead = request.getHeader("Access-Control-Request-Headers");
            if (null != reqHead && !reqHead.isEmpty()) {
                response.setHeader("Access-Control-Allow-Headers", reqHead);
            }
            if (!"OPTIONS".equalsIgnoreCase(request.getMethod())) {
                String tenantIdentifier = request.getHeader(this.tenantRequestHeader);
                if (org.apache.commons.lang3.StringUtils.isBlank(tenantIdentifier)) {
                    tenantIdentifier = request.getParameter("tenantIdentifier");
                }
                if (tenantIdentifier == null && this.exceptionIfHeaderMissing) {
                    throw new InvalidTenantIdentifierException("No tenant identifier found: Add request header of '"
                            + this.tenantRequestHeader + "' or add the parameter 'tenantIdentifier' to query string of request URL.");
                }
                String pathInfo = request.getRequestURI();
                boolean isReportRequest = false;
                if (pathInfo != null && pathInfo.contains("report")) {
                    isReportRequest = true;
                }
                final FineractPlatformTenant tenant = this.basicAuthTenantDetailsService.loadTenantById(tenantIdentifier, isReportRequest);
                ThreadLocalContextUtil.setTenant(tenant);
                HashMap<BusinessDateType, LocalDate> businessDates = this.businessDateReadPlatformService.getBusinessDates();
                ThreadLocalContextUtil.setBusinessDates(businessDates);
                String authToken = request.getHeader("Authorization");
                if (authToken != null && authToken.startsWith("bearer ")) {
                    ThreadLocalContextUtil.setAuthToken(authToken.replaceFirst("bearer ", ""));
                }
                if (!firstRequestProcessed.get()) {
                    final String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(),
                            request.getContextPath() + apiUri);
                    System.setProperty("baseUrl", baseUrl);
                    final boolean ehcacheEnabled = this.configurationDomainService.isEhcacheEnabled();
                    if (ehcacheEnabled) {
                        this.cacheWritePlatformService.switchToCache(CacheType.SINGLE_NODE);
                    } else {
                        this.cacheWritePlatformService.switchToCache(CacheType.NO_CACHE);
                    }
                    firstRequestProcessed.set(true);
                }
                chain.doFilter(request, response);
            }
        } catch (final InvalidTenantIdentifierException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.addHeader("WWW-Authenticate", "Basic realm=\"" + "Fineract Platform API" + "\"");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } finally {
            task.stop();
            final PlatformRequestLog logRequest = PlatformRequestLog.from(task, request);
            log.info("{}", this.toApiJsonSerializer.serialize(logRequest));
        }
    }
}
