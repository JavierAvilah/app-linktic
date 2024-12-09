package com.linktic.app.security;

import com.linktic.app.model.Profile;
import com.linktic.app.model.enumeration.Role;
import com.linktic.app.repository.ProfileRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitProfile {
    @Autowired
    private ProfileRepository profileRepository;
    @Value("${password.admin}")
    private String passwordAdmin;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init (){
        if (!profileRepository.findByUserName("admin").isPresent()){
             Profile profile=Profile.builder()
                     .userName("admin")
                     .password(passwordEncoder.encode(passwordAdmin))
                     .email("admin@test.com")
                     .role(Role.ADMIN)
                     .build();

             profileRepository.save(profile);
        }

    }
}
