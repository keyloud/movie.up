package com.kosavpa.first.boot.example.config;


import com.kosavpa.first.boot.example.dao.entity.users.UserEntity;
import com.kosavpa.first.boot.example.dao.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Bean
    public SecurityFilterChain webSecurity(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf()
                    .disable()
                .authorizeRequests()
                    .antMatchers("/blog/*/delete", "/add", "/blog/*/redact/").hasRole("ADMIN")
                    .antMatchers("/registration").not().fullyAuthenticated()
                    .antMatchers("/blog/*").authenticated()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                .and()
                .build();
    }

    @Bean
    public ApplicationRunner dataLoader(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByUsername("ADMIN") == null){
                userRepo.save(new UserEntity(
                        "ADMIN",
                        encoder.encode("pwd"),
                        "ROLE_ADMIN"));}
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
