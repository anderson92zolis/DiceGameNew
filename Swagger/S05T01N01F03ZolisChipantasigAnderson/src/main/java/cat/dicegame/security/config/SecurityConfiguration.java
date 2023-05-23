package cat.dicegame.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    // To confing swagger:  : https://www.bezkoder.com/spring-boot-jwt-auth-mongodb/#With_WebSecurityConfigurerAdapter
    // to check later: https://www.toptal.com/spring/spring-security-tutorial

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .authorizeHttpRequests() // if  some endpoint need Authorize

                .requestMatchers("/api/v1/auth/**", "/swagger-ui/index.html**", "/v3/api-docs/**", "/swagger-ui/**")
                .permitAll()
                .requestMatchers("/swagger-ui.html/**")
                .permitAll()
                .anyRequest()
                .authenticated()

                .and() //new configuration
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return httpSecurity.build();
    }




}
