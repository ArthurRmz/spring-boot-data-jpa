package com.dark.hat.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.dark.hat.app.auth.handler.LoginSuccessHandler;
import com.dark.hat.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	/**
	@Autowired
	DataSource dataSource;*/
	
	@Autowired
	private JpaUserDetailsService userDetailsService;

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
		/**
		Implementacion con usuarios en memoria
		PasswordEncoder encoder = passwordEncoder;
		UserBuilder users = User.builder().passwordEncoder(password-> encoder.encode(password));
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN","USER"))
		.withUser(users.username("arthur").password("12345").roles("USER"));*/
		
		/**
		Implementacion con JDBC
		builder.jdbcAuthentication()
		.dataSource(dataSource)
		.passwordEncoder(passwordEncoder)
		.usersByUsernameQuery("SELECT username, password, enabled from users where username=?")
		.authoritiesByUsernameQuery("SELECT u.username,a.authority FROM authorities a inner join users u on (a.user_id=u.id) where u.username=?");*/
		
		/**Implementacion con JPA*/
		builder
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/","/css/**","/js/**","/listar**","/locale","/view","/api/clientes/**").permitAll()
		/**.antMatchers("/ver/*").hasAnyRole("USER")
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")*/
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.successHandler(successHandler).defaultSuccessUrl("/listar?page=0")
		.loginPage("/login").permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
	}
	
	
}
