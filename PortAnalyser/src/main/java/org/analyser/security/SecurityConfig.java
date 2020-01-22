package org.analyser.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	/**
	 *  C'est une interface qui fournit un service. 
	 *  C'est a implementer;
	 *  cad que toutes les operations concernant l'authentification (getRoles, getUsers,...) 
	 *  se fait par ce service qui est une implementation de cette interface
	 *  
	 *  La methode "loadUserByUsername" qui est redefinie retourne un objet User qui contient username, password, authorities
	 */
	@Autowired
	private UserDetailsService userDetailsService; 
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CustomAccessDeniedHandler accessDeniedHandler;
	
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	}
	
	@Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler(){
        return new CustomSimpleUrlAuthenticationSuccessHandler();
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/**
		 * Dire a spring que l'authentification sera basee par userDetailsService 
		 * qui est une implementation de UserDetailsService
		 */
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(bCryptPasswordEncoder); 
		/**
		 *  fonction de hachage pour le mot de passe. 
		 *  Ici BCrypt au lieu de md5() qui est depreciee. 
		 *  Cela concerne tous les utilisateurs
		 *  
		 *  Spring chiffre le mot de passe quand on essait de se connecter pour comparer
		 */
		
		
		/**
		auth.inMemoryAuthentication()
        .withUser("user").password("user").roles("USER")
        .and()
        .withUser("sitrakarazafi24@gmail.com").password(bCryptPasswordEncoder.encode("admin")).roles("ADMIN");
        */
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().loginPage("/login").successHandler(customAuthenticationSuccessHandler()).failureUrl("/login?error"); // Indiquer a Spring une action (dans controleur) qui renvoie une page d'authentification
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler); // redirection que le controleur va capturer
		http.authorizeRequests().antMatchers("/login/**").permitAll();
		http.authorizeRequests().antMatchers("/static/**").permitAll();
		
		//http.authorizeRequests().antMatchers("/index").hasRole("ADMIN");
		//http.authorizeRequests().antMatchers("/index").hasRole("USER");
		http.authorizeRequests().anyRequest().authenticated(); 
	}
}
