package com.zephyr.auth;

import com.zephyr.auth.util.GrantTypes;
import com.zephyr.auth.util.Scopes;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {

    @Setter(onMethod = @__(@Autowired))
    private AuthenticationManager authenticationManager;

    @Setter(onMethod = @__(@Autowired))
    private UserDetailsService userService;

    @Setter(onMethod = @__(@Autowired))
    private Environment environment;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        // TODO: store secrets in vault
        // @formatter:off
        clients.inMemory()
                    .withClient("browser")
                    .authorizedGrantTypes(GrantTypes.REFRESH_TOKEN, GrantTypes.PASSWORD)
                    .scopes(Scopes.UI)
                .and()
                    .withClient("task-service")
                    .secret(environment.getProperty("TASK_SERVICE_PASSWORD"))
                    .authorizedGrantTypes(GrantTypes.CREDENTIALS, GrantTypes.REFRESH_TOKEN)
                    .scopes(Scopes.SERVER)
                .and()
                    .withClient("rating-service")
                    .secret(environment.getProperty("RATING_SERVICE_PASSWORD"))
                    .authorizedGrantTypes(GrantTypes.CREDENTIALS, GrantTypes.REFRESH_TOKEN)
                    .scopes(Scopes.SERVER);
        // @formatter:on
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .tokenStore(tokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userService);
    }

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        var defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());

        return defaultTokenServices;
    }
}