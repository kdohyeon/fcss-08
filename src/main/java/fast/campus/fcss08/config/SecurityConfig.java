package fast.campus.fcss08.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import java.util.Collection;
import java.util.function.Supplier;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        UserDetails danny = User.withUsername("danny.kim")
                .password("12345")
//                .authorities("READ")
                .roles("ADMIN")
//                .authorities("ROLE_ADMIN")
                .build();
        UserDetails steve = User.withUsername("steve.kim")
                .password("23456")
//                .authorities("WRITE")
                .roles("MANAGER")
//                .authorities("ROLE_MANAGER")
                .build();

        manager.createUser(danny);
        manager.createUser(steve);

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(Customizer.withDefaults());
//        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().permitAll()); // 모든 요청 허용
//        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().hasAuthority("WRITE")); // WRITE 권한이 있는 사용자만 허용
//        httpSecurity.authorizeHttpRequests(c -> c.anyRequest()
//                .hasAnyAuthority("READ", "WRITE")); // READ, WRITE 권한이 있는 사용자만 허용
//        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().access(customAuthManager())); // WRITE 권한이 있는 사용자만 허용
//        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().hasRole("MANAGER")); // WRITE 권한이 있는 사용자만 허용
        httpSecurity.authorizeHttpRequests(c -> c.anyRequest().denyAll()); // 모든 요청 거부

        return httpSecurity.build();
    }

//    private AuthorizationManager<RequestAuthorizationContext> customAuthManager() {
//        return new AuthorizationManager<RequestAuthorizationContext>() {
//            @Override
//            public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
//                Authentication auth = authentication.get();
//                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
//                boolean granted = authorities.stream().anyMatch(each -> each.getAuthority().equals("READ"));
//                return new AuthorizationDecision(granted);
//            }
//        };
//    }
}
