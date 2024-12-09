package com.linktic.app.config;


import com.linktic.app.security.UserDetailsImpl;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class PersistenceConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {

        @NotNull
        public Optional<String> getCurrentAuditor() {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()
                    || authentication.getPrincipal().toString().equals("anonymousUser")) {
                return Optional.empty();
            }
            UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
            return Optional.ofNullable(userDetails.getUsername());
        }
    }
}