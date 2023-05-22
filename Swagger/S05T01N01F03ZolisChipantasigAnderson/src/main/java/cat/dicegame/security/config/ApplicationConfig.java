package cat.dicegame.security.config;

import cat.dicegame.security.model.Entity.Player;
import cat.dicegame.security.model.Repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final PlayerRepository playerRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> playerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("USER NOT FOUND FROM AUTH"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {  //  Data Access Object
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
        /* I will call it authentication manager and within  this mean I want to inject an object  of type authentication configuration
            1:39:55, and I will call it config this authentication  configuration hold already the information about 1:40:04 the authentication manager so I will just return  config.getAuthenticationManager() all right
         */
    }

    @Bean
    public  PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
