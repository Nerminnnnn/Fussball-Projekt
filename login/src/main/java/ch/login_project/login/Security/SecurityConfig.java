package ch.login_project.login.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
// disable csrf
        http.csrf(AbstractHttpConfigurer::disable);
// exception handling TODO
// Session management
        http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
// manage authorized endpoints
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/home", "/", "/auth/**", "/js/**", "/css/**").permitAll()
                .anyRequest().authenticated()
        );
// form login redirect
        http.formLogin(formLogin ->
                formLogin
                        .loginPage("/auth/default") // instead of the ugly default login popup, you get this
                        .loginProcessingUrl("/auth/login") // URL to submit the username and password
                        .defaultSuccessUrl("/userhome", true) // URL to redirect after successful login
                        .failureUrl("/home?error=true")
                        .permitAll()
        );
        http.sessionManagement(sess -> sess
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        );

// TODO what's this?
        http.logout(lOut -> {
            lOut
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .clearAuthentication(true)
                    .logoutSuccessUrl("/auth/logout-success")// URL to redirect after successful logout
                    .logoutUrl("/logout")
                    .permitAll(); });
// TODO
/*http.invalidateHttpSession(true)
.deleteCookies("JSESSIONID");*/
        return http.build();

    }


    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

