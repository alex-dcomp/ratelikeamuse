package RateLikeAMuse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    // Essa função criptografa as senhas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Cadeia de filtros de segurança do Spring Security
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Permite acesso a todas as rotas sem autenticação
            )
            .formLogin(form -> form.disable()) // Desabilita o login padrão do Spring Security, o login está sendo feito de forma manual no UserService
            .logout(logout -> logout.disable()); // Desabilita o logout padrão do Spring Security, o logout está sendo feito de forma manual no UserService

        return http.build();
    }
}
