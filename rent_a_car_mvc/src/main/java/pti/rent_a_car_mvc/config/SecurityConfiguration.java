package pti.rent_a_car_mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(req -> req
					.requestMatchers("/admin", "/admin/**").hasAnyAuthority("ROLE_ADMIN")
					.anyRequest().permitAll()
				)
			.csrf(token -> token
					.disable())
			.formLogin(login -> login
					.permitAll())
			.logout(logout -> logout
					.permitAll());
			
		return http.build();
	}
	
	@Bean
	public UserDetailsService service() {
		
		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
		
		manager.createUser(
					User.builder()
						.username("admin")
						.password("admin")
						.roles("ADMIN")
						.build()
		);
		
		return manager;
	}
	
	/* The NoOpPasswordEncoder is marked deprecated because it is not considered
	 * safe. According to the official documentation it won't be removed anytime
	 * soon and in this case it fits the bill.
	 */
	
	@Bean
	@SuppressWarnings("deprecation")
	public PasswordEncoder encoder() {
		
		return NoOpPasswordEncoder.getInstance();
	}
}
