
package org.apache.fineract.infrastructure.core.config;
import org.apache.fineract.infrastructure.instancemode.filter.FineractInstanceModeApiFilter;
import org.apache.fineract.infrastructure.security.filter.TenantAwareBasicAuthenticationFilter;
import org.apache.fineract.infrastructure.security.filter.TwoFactorAuthenticationFilter;
import org.apache.fineract.infrastructure.security.service.TenantAwareJpaPlatformUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
@Configuration
@ConditionalOnProperty("fineract.security.basicauth.enabled")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private TenantAwareJpaPlatformUserDetailsService userDetailsService;
    @Autowired
    private TwoFactorAuthenticationFilter twoFactorAuthenticationFilter;
    @Autowired
    private FineractInstanceModeApiFilter fineractInstanceModeApiFilter;
    @Autowired
    private FineractProperties fineractProperties;
    @Autowired
    private ServerProperties serverProperties;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http 
                .csrf().disable() 
                .antMatcher("/apiecho").permitAll() 
                .antMatchers(HttpMethod.POST, "/apiself/authentication").permitAll() 
                .antMatchers(HttpMethod.POST, "/apiself/registration/user").permitAll() 
                .antMatchers(HttpMethod.PUT, "/apitwofactor/validate").fullyAuthenticated() 
                .antMatchers("/api/*/twofactor").fullyAuthenticated() 
                .antMatchers("/api/**").access("isFullyAuthenticated() and hasAuthority('TWOFACTOR_AUTHENTICATED')").and() 
                .httpBasic() 
                .authenticationEntryPoint(basicAuthenticationEntryPoint()) 
                .and() 
                .sessionManagement() 
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) 
                .and() 
                .addFilterAfter(fineractInstanceModeApiFilter, SecurityContextPersistenceFilter.class) 
                .addFilterAfter(tenantAwareBasicAuthenticationFilter(), FineractInstanceModeApiFilter.class) 
                .addFilterAfter(twoFactorAuthenticationFilter, BasicAuthenticationFilter.class); 
        if (serverProperties.getSsl().isEnabled()) {
            http.requiresChannel(channel -> channel.antMatchers("/api/**").requiresSecure());
        }
    }
    @Bean
    public TenantAwareBasicAuthenticationFilter tenantAwareBasicAuthenticationFilter() throws Exception {
        return new TenantAwareBasicAuthenticationFilter(authenticationManagerBean(), basicAuthenticationEntryPoint());
    }
    @Bean
    public BasicAuthenticationEntryPoint basicAuthenticationEntryPoint() {
        BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName("Fineract Platform API");
        return basicAuthenticationEntryPoint;
    }
    @Bean(name = "customAuthenticationProvider")
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
        auth.eraseCredentials(false);
    }
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public FilterRegistrationBean<TenantAwareBasicAuthenticationFilter> tenantAwareBasicAuthenticationFilterRegistration()
            throws Exception {
        FilterRegistrationBean<TenantAwareBasicAuthenticationFilter> registration = new FilterRegistrationBean<TenantAwareBasicAuthenticationFilter>(
                tenantAwareBasicAuthenticationFilter());
        registration.setEnabled(false);
        return registration;
    }
    @Bean
    public FilterRegistrationBean<TwoFactorAuthenticationFilter> twoFactorAuthenticationFilterRegistration() {
        FilterRegistrationBean<TwoFactorAuthenticationFilter> registration = new FilterRegistrationBean<TwoFactorAuthenticationFilter>(
                twoFactorAuthenticationFilter);
        registration.setEnabled(false);
        return registration;
    }
}
