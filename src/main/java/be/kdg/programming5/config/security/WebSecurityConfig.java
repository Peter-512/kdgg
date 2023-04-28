package be.kdg.programming5.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	private HttpStatusEntryPoint httpStatusEntryPoint() {
		return new HttpStatusEntryPoint(HttpStatus.FORBIDDEN);
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http)
			throws Exception {
		http.
				httpBasic().authenticationEntryPoint(httpStatusEntryPoint())
				.and()
				.csrf()
				.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
				.and()
				.cors()
				.and()
				.authorizeHttpRequests(auths -> auths
						.antMatchers(HttpMethod.GET, "/js/**", "/css/**", "/webjars/**", "/fonts/**", "/images/**", "/api/**")
						.permitAll()
						.antMatchers("/", "/channels", "/users", "/signup").permitAll()
						.anyRequest().authenticated())
				.formLogin().loginPage("/login").permitAll()
				.and()
				.logout().permitAll();
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins("http://localhost:9000");
			}
		};
	}
}
