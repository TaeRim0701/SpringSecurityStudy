package study.TestSecuriry.comfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        return hierarchy;
    }

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "login", "/loginProc", "/join", "/joinProc").permitAll() // 모든 사용자에게 접근 허용
                        .requestMatchers("/admine").hasRole("ADMIN") // 특정 사용자에게 권한
                        .requestMatchers("/my/**").hasAnyRole("USER", "ADMIN") // 여러가지 사용자
                        .anyRequest().authenticated()
                );

//        http
//                .formLogin((auth) -> auth.loginPage("/login")
//                        .loginProcessingUrl("/loginProc")
//                        .permitAll()
//                );
//        http basic 방식으로 대체

        http
                .httpBasic(Customizer.withDefaults()); //http basic 방식으로 인증


//        http
//                .csrf((auth) -> auth.disable()); //개발 환경에서만 csrf 비홠성화

        http
                .sessionManagement((auth) -> auth
                        .maximumSessions(1) // 하나의 아이디에 대한 다중 로그인 허용 개수
                        .maxSessionsPreventsLogin(true));  // 다중 로그인 개수를 초과하였을 경우 처리 방법
                                                           // true : 초과시 새로운 로그인 차단, false : 초과시 기존 세션 하나 삭제

        http
                .sessionManagement((auth) -> auth
                        .sessionFixation().changeSessionId());
        // 세션 고정 공격을 보호하기 위한 로그인 성공시 세션 설정 방법은 sessionManagement() 메소드의 sessionFixation() 메소드를 통해서 설정
        //sessionManagement().sessionFixation().none() : 로그인 시 세션 정보 변경 안함
        //sessionManagement().sessionFixation().newSession() : 로그인 시 세션 새로 생성
        //sessionManagement().sessionFixation().changeSessionId() : 로그인 시 동일한 세션에 대한 id 변경






//        inmemory 방식 유저 정보 저장
//        public UserDetailsService userDetailsService() {
//
//            UserDetails user1 = User.builder()
//                    .username("user1")
//                    .password(bCryptPasswordEncoder().encode("1234"))
//                    .roles("ADMIN")
//                    .build();
//
//            UserDetails user2 = User.builder()
//                    .username("user2")
//                    .password(bCryptPasswordEncoder().encode("1234"))
//                    .roles("USER")
//                    .build();
//
//            return new InMemoryUserDetailsManager(user1, user2);
//        }
        return http.build();
    }
}
