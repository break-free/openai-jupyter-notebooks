
package org.apache.fineract.infrastructure.security.filter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.apache.fineract.notification.service.NotificationReadPlatformService;
import org.apache.fineract.useradministration.domain.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
@ConditionalOnProperty("fineract.security.basicauth.enabled")
public class TenantAwareBasicAuthenticationFilter extends BasicAuthenticationFilter {
    private static boolean firstRequestProcessed = false;
    private static final Logger LOG = LoggerFactory.getLogger(TenantAwareBasicAuthenticationFilter.class);
    @Autowired
    private ToApiJsonSerializer<PlatformRequestLog> toApiJsonSerializer;
    @Autowired
    private ConfigurationDomainService configurationDomainService;
    @Autowired
    private CacheWritePlatformService cacheWritePlatformService;
    @Autowired
    private NotificationReadPlatformService notificationReadPlatformService;
    @Autowired
    private BasicAuthTenantDetailsService basicAuthTenantDetailsService;
    @Autowired
    private BusinessDateReadPlatformService businessDateReadPlatformService;
    private final String tenantRequestHeader = "Fineract-Platform-TenantId";
    private final boolean exceptionIfHeaderMissing = true;
    public TenantAwareBasicAuthenticationFilter(final AuthenticationManager authenticationManager,
            final AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final StopWatch task = new StopWatch();
        task.start();
        try {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            } else {
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
                if (authToken != null && authToken.startsWith("Basic ")) {
                    ThreadLocalContextUtil.setAuthToken(authToken.replaceFirst("Basic ", ""));
                }
                if (!firstRequestProcessed) {
                    final String baseUrl = request.getRequestURL().toString().replace(request.getPathInfo(), "/");
                    System.setProperty("baseUrl", baseUrl);
                    final boolean ehcacheEnabled = this.configurationDomainService.isEhcacheEnabled();
                    if (ehcacheEnabled) {
                        this.cacheWritePlatformService.switchToCache(CacheType.SINGLE_NODE);
                    } else {
                        this.cacheWritePlatformService.switchToCache(CacheType.NO_CACHE);
                    }
                    TenantAwareBasicAuthenticationFilter.firstRequestProcessed = true;
                }
            }
            super.doFilterInternal(request, response, filterChain);
        } catch (final InvalidTenantIdentifierException e) {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.addHeader("WWW-Authenticate", "Basic realm=\"" + "Fineract Platform API" + "\"");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } finally {
            task.stop();
            final PlatformRequestLog log = PlatformRequestLog.from(task, request);
            LOG.debug("{}", this.toApiJsonSerializer.serialize(log));
        }
    }
    @Override
    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
            throws IOException {
        super.onSuccessfulAuthentication(request, response, authResult);
        AppUser user = (AppUser) authResult.getPrincipal();
        if (notificationReadPlatformService.hasUnreadNotifications(user.getId())) {
            response.addHeader("X-Notification-Refresh", "true");
        } else {
            response.addHeader("X-Notification-Refresh", "false");
        }
        String pathURL = request.getRequestURI();
        boolean isSelfServiceRequest = pathURL != null && pathURL.contains("/self/");
        boolean notAllowed = (isSelfServiceRequest && !user.isSelfServiceUser()) || (!isSelfServiceRequest && user.isSelfServiceUser());
        if (notAllowed) {
            throw new BadCredentialsException("User not authorised to use the requested resource.");
        }
    }
}
