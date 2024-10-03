package com.openclassrooms.chatop.configuration;

import com.openclassrooms.chatop.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SpringSecurityConfig {

    private final String jwtKey = "b3BlbnNzaC1rZXktdjEAAAAABG5vbmUAAAAEbm9uZQAAAAAAAAABAAABlwAAAAdzc2gtcnNhAAAAAwEAAQAAAYEArTzhhb5xf/pmok7lCAIHhPwRWUZly4JqqJDdg4RrwLOS8sfioy834eB8UpKiac9YpSfnpI7+fvzq3rbq8AyXEFBLkPbH+lgzVv+QIBp1P5w0+sMG9sG0LOrpKXYa8oDYRm8W66BWHqd2OzxH6KU+zjZSKrOKYEh6frmyR3M62rUDRdz8a33EFSKWDupmmK1JsXaTP4fw3sD8ODiyaGxx5t83nZMXI5IULfXhfqmNm7AqedJgGGwuVu/PSzXN9sjdWvw/wO7Bz8u+x8iXIvhCJH5uYSYl/rMOUj7ES1fCSsDtDKeiEhIuVY11Fb/BpzXRF+ezwWe5veA/ZaAWTi+sTFkiArJ1NmHJTKsif2rwMtKuec2QOcASk9CzRYzulPd0WqRCjk4boBqOvdnMvE50Nb5DpZvCAAmMu3j0s3u1qPKtpDVHbzyaOydnj4gqACOBtkMK7uZHRKQddsLUmQA5M2QKpsBqu4FVU7jDxwsVGAQ5K5kUnf59cYfmP1HaibebAAAFiF1s7/1dbO/9AAAAB3NzaC1yc2EAAAGBAK084YW+cX/6ZqJO5QgCB4T8EVlGZcuCaqiQ3YOEa8CzkvLH4qMvN+HgfFKSomnPWKUn56SO/n786t626vAMlxBQS5D2x/pYM1b/kCAadT+cNPrDBvbBtCzq6Sl2GvKA2EZvFuugVh6ndjs8R+ilPs42UiqzimBIen65skdzOtq1A0Xc/Gt9xBUilg7qZpitSbF2kz+H8N7A/Dg4smhscebfN52TFyOSFC314X6pjZuwKnnSYBhsLlbvz0s1zfbI3Vr8P8Duwc/LvsfIlyL4QiR+bmEmJf6zDlI+xEtXwkrA7QynohISLlWNdRW/wac10Rfns8Fnub3gP2WgFk4vrExZIgKydTZhyUyrIn9q8DLSrnnNkDnAEpPQs0WM7pT3dFqkQo5OG6Aajr3ZzLxOdDW+Q6WbwgAJjLt49LN7tajyraQ1R288mjsnZ4+IKgAjgbZDCu7mR0SkHXbC1JkAOTNkCqbAaruBVVO4w8cLFRgEOSuZFJ3+fXGH5j9R2om3mwAAAAMBAAEAAAGAFBlmglxDJ48HeLi17UTsVxXhHr4tMfxn9Ytr4ptq3l5h+UBkX3yApk34/qI8Amdn0y7wZZugPrQyEomfHnTHfha4KGc0HSvoi6UhCqh8hqx6n8bK4ww8Tvh+TteAxDawtmijJyQLed5XYwNZGyPjnTdDjJLCG5kCeqYpig1TfGvYp7K+UpZdOU/cSyS6UefLugbrifPIEtV0g0KzaFbUrEL6XTzjSAw7F84ETewhcuPF5IEaXHBeEVYcyI7DM75qx0iR3ffYxHWRJzZQ4hLXjHz5SamyoyKeWayabk7Nw6Pm7YkVYofg7zhKDICyEwKhvJBzpFem2F9sDDLIZJGmTEMZNb6NHLh6IgKVvfzd+Iomxi6c9EKjlhSAS5NxqhE698BEvaY7d9YFU0tWQ1fOouP8XtmjO9xzMLZDZsk/DIXbRqQtJxbaGFpEZDeLwmvOAfRiBH61KNfUdI5twtdnX+4zTggHaTE48SK+f1bqrLHxClIpSMEMqS/VPMt+QGW5AAAAwE3uRyPPqasuaHP8pXemvAL2k76klotW/yJ2ZfaCeSiMnHtKVclu87yfLoS27KkP90C/T5ay++jr2er2jox26GHcqEOXHXDoMtkWVjxaYQJkSCok6mCBeUhr5JFx7It4BuPzcO3HmXLDirdWDDdlHCr4bIDKx26fYjhkzzP93hI+d6aERnkT8+kLTBlClker5QbVcQ9f2DJ7F7hdsPsK8dTodDgR08wEymisOkDTSOI8u3CkV49YAuu6eotyYfDzyAAAAMEA3+awDg8l6gtxmUhYSQtRNOcPDG48sENWa+fiPTQotT90odg9LK+tIB8yiN70d16pDSE9KmlmLpvhphPz0oqzx8VJFqvJlauHhJ/plD3dTk5AaKmerTyozlgg1U2ekZbhJJnxM9vOvCTs9XLzmrvjr+hSU2noQzWQzBE0bkDmi8algcaGHR56zccir59+bCosDF1Di9SSXpnQSqmEhiDDU0GZfs/qdMdvFd6WffSECEYPNcPwGUU953jKVcJtO6rFAAAAwQDGEtFjprJSPZTyXYq2640QyvKyVpnPi2CIYodSs5tMHu10bXTpm1qwcigJxb8oQKtLsST8UBxdpIe71qIbPYk5zIGAtjszh4Igf8RI5f2rCr5MNvM8jew4xQTzrGOAPnq8MB8fm2TNu8BU3VoUOfZeCqtMFt5SdYsyuddJU53dkK7uaWAOaUZRbj/3G7NPwUB2AIjbxFDL4W4CCk8Ce3WVbau4IMy6kUqSQdd5aQH/Nquj9N8E9wPTqbtR/COnft8AAAAScm9vdEBkZWJpYW4tamVzc2llAQ==";

    private final CustomUserDetailsService userDetailsService;

    public SpringSecurityConfig(@Autowired CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    // Auth with HTTP Basic on /login
    public SecurityFilterChain basicSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                    auth//.requestMatchers("/api/auth/register", "/api/auth/email").permitAll()
                        // .anyRequest().authenticated()
                        .anyRequest().permitAll()
                )
                .build();
    }

    /*
    @Bean
    public JwtDecoder jwtDecoder() {
        SecretKeySpec secretKey = new SecretKeySpec(this.jwtKey.getBytes(), 0, this.jwtKey.getBytes().length,"RSA");
        return NimbusJwtDecoder.withSecretKey(secretKey).macAlgorithm(MacAlgorithm.HS256).build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtKey.getBytes()));
    }*/

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
