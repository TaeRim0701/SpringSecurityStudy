package study.TestSecuriry.comfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "login").permitAll() // 모든 사용자에게 접근 허용
                        .requestMatchers("/admine").hasRole("ADMIN") // 특정 사용자에게 권한
                        .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN") // 여러가지 사용자
                        .anyRequest().authenticated()
                );
        
        return http.build();
    }
}
