package com.sendtrust.best_travel.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Configuration
public class SecurityConfig {

    @Value("${app.client.id}")
    private String clientId;
    @Value("${app.client.secret}")
    private String clientSecret;
    @Value("${app.client-scope-read}")
    private String scopeRead;
    @Value("${app.client-scope-write}")
    private String scopeWrite;
    @Value("${app.client-redirect-debugger}")
    private String redirectUri1;
    @Value("${app.client-redirect-spring-doc}")
    private String redirectUri2;

    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier(value = "appUserService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configura la seguridad para el servidor de autorización OAuth2.
     * Incluye soporte para OIDC y manejo de excepciones.
     */
    @Bean
    @Order(1)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());

        http.exceptionHandling(e -> e.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(LOGIN_RESOURCE)));

        return http.build();
    }

    /**
     * Configura la seguridad para la aplicación.
     * Define reglas de acceso basadas en roles y habilita el servidor de recursos OAuth2 con JWT.
     */
    @Bean
    @Order(2)
    public SecurityFilterChain appSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_RESOURCES)
                .permitAll()
                .requestMatchers(USER_RESOURCES)
                .hasAuthority(AUTH_READ)
                .requestMatchers(ADMIN_RESOURCES)
                .hasAuthority(AUTH_WRITE)
                .and()
                .oauth2ResourceServer()
                .jwt();

        return http.build();
    }

    /**
     * Configura la seguridad para usuarios.
     * Define reglas de acceso basadas en roles específicos.
     */
    @Bean
    @Order(3)
    public SecurityFilterChain userSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(PUBLIC_RESOURCES)
                .permitAll()
                .requestMatchers(USER_RESOURCES)
                .hasRole(ROLE_USER)
                .requestMatchers(ADMIN_RESOURCES)
                .hasRole(ROLE_ADMIN);

        return http.build();
    }

    /**
     * Configura el proveedor de autenticación utilizando BCrypt para codificar contraseñas.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(BCryptPasswordEncoder encoder) {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(encoder);
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    /**
     * Configura un repositorio de clientes registrados en memoria para OAuth2.
     * Incluye detalles como ID, secreto, alcances y métodos de autenticación.
     */
    @Bean
    public RegisteredClientRepository registeredClientRepository(BCryptPasswordEncoder encoder) {
        var registeredClient = RegisteredClient
                .withId(UUID.randomUUID().toString())
                .clientId(clientId)
                .clientSecret(encoder.encode(clientSecret))
                .scope(scopeRead)
                .scope(scopeWrite)
                .redirectUri(redirectUri1)
                .redirectUri(redirectUri2)
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    /**
     * Configura los ajustes del servidor de autorización.
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    /**
     * Configura el decodificador de JWT utilizando claves JWK.
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    /**
     * Genera y configura una fuente de claves JWK para firmar tokens JWT.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        var rsaKey = generateKeys();
        var jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * Proporciona un codificador BCrypt para contraseñas.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura los ajustes de los tokens, como el tiempo de vida del token de actualización.
     */
    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .refreshTokenTimeToLive(Duration.ofHours(8))
                .build();
    }

    /**
     * Convierte las autoridades de JWT eliminando prefijos.
     */
    @Bean
    public JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter() {
        var converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthorityPrefix("");
        return converter;
    }

    /**
     * Configura el convertidor de autenticación JWT para asignar autoridades.
     */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter) {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return converter;
    }

    /**
     * Personaliza los tokens OAuth2 agregando información adicional en los claims.
     */
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> oAuth2TokenCustomizer() {
        return context -> {
            if (context.getTokenType().equals(OAuth2TokenType.ACCESS_TOKEN)) {
                context.getClaims().claims(claim -> {
                    claim.putAll(Map.of(
                            "owner", APPLICATION_OWNER,
                            "date_request", LocalDateTime.now().toString()
                    ) );
                });
            }
        };
    }

    /**
     * Genera un par de claves RSA para firmar tokens.
     */
    private static KeyPair generateRSA() {
        KeyPair keyPair = null;
        try {
            var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        return keyPair;
    }

    /**
     * Genera claves RSA y las convierte en un objeto RSAKey para JWK.
     */
    private static RSAKey generateKeys() {
        var keyPair = generateRSA();
        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey= (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString()).build();
    }

    // Constantes utilizadas en la configuración de seguridad.
    private static final String[] PUBLIC_RESOURCES = {"/fly/**","/hotel/**","/swagger-ui/**", "/.well-known/**, ", "/v3/api-docs/**", "report/**"};
    private static final String[] USER_RESOURCES = {"/tour/**","/ticket/**","/reservation/**"};
    private static final String[] ADMIN_RESOURCES = {"/users/**"};
    private static final String LOGIN_RESOURCE = "/login";
    private static final String AUTH_WRITE = "write";
    private static final String AUTH_READ = "read";
    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";
    private static final String APPLICATION_OWNER = "Debuggeando ideas";
}