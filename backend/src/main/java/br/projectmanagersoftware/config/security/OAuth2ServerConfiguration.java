package br.projectmanagersoftware.config.security;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 *
 * @author Wellington Felipe Fucks
 *
 * @version 1.0
 * @since 1.0, 30/10/2015
 */
@Configuration
public class OAuth2ServerConfiguration {

    private static String REALM = "MY_OAUTH_REALM";

    @Autowired
    private AuthenticationService authenticationService;

    @Configuration
    @EnableResourceServer
    protected class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId("proejctmanagersecurity").stateless(false);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .addFilterBefore(new CORSFilter(), HeaderWriterFilter.class)
                .authorizeRequests()
                    .antMatchers("/api").authenticated()
                    .anyRequest().permitAll()
                .and()
                    .exceptionHandling()
                        .accessDeniedHandler(new OAuth2AccessDeniedHandler())
                .and()
                    .csrf()
//                        .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
//                        .requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/token"))
                    .disable();
        }
        
//        @Bean
//        public CorsConfigurationSource corsConfigurationSource() {
//             CorsConfiguration configuration = new CorsConfiguration();
//             configuration.addAllowedHeader("*");
//             configuration.setAllowedOrigins(Arrays.asList("*"));
//             configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
//
//             UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//             source.registerCorsConfiguration("/**", configuration);
//
//             return source;
//         }
        
    }

    @Configuration
    @EnableAuthorizationServer
    protected class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        protected TokenStore tokenStore;

        @Autowired
        protected UserApprovalHandler approvalHandler;

        @Autowired
        protected PasswordEncoder passwordEncoder;

        @Autowired
        @Qualifier("authenticationManagerBean")
        protected AuthenticationManager authenticationManager;
        
        

        @Override
        public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
            security
                    .realm(REALM + "/CLIENT")
                    .addTokenEndpointAuthenticationFilter(new CORSFilter());
            
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                    .withClient("my-trusted-client")
                    .authorizedGrantTypes("password", "authorization_code", "refresh_token", "implicit")
                    .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
                    .scopes("read", "write", "trust")
                    .secret("{bcrypt}$2a$10$1tmyeLQqZtRU1WeY/1m5jemeb8E6huG/OTtiJboGAOyWmL1APX02a")
                    .accessTokenValiditySeconds(600)//Access token is only valid for 2 minutes.
                    .refreshTokenValiditySeconds(1200);//Refresh token is only valid for 10 minutes.
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(this.tokenStore)
                    .userApprovalHandler(this.approvalHandler)
                    .authenticationManager(this.authenticationManager)
                    .userDetailsService(authenticationService);
        }

    }

}
