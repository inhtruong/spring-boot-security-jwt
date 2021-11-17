package com.ait.demospringsecurity.securities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ait.demospringsecurity.securities.securities.jwt.AuthEntryPointJwt;
import com.ait.demospringsecurity.securities.securities.jwt.AuthTokenFilter;
import com.ait.demospringsecurity.securities.securities.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
//		 securedEnabled = true,
//		 jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable()
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//CSRF ( Cross Site Request Forgery) lÃ  kÄ© thuáº­t táº¥n cÃ´ng báº±ng cÃ¡ch sá»­ dá»¥ng quyá»�n chá»©ng thá»±c cá»§a ngÆ°á»�i sá»­ dá»¥ng Ä‘á»‘i vá»›i 1 website khÃ¡c

		// CÃ¡c trang khÃ´ng yÃªu cáº§u login
		http.authorizeRequests().antMatchers("/api/auth/**", "/api/test/**", "/", "/login", "/logout").permitAll();

		// Khi ngÆ°á»�i dÃ¹ng Ä‘Ã£ login, vá»›i vai trÃ² XX.
		// NhÆ°ng truy cáº­p vÃ o trang yÃªu cáº§u vai trÃ² YY,
		// Ngoáº¡i lá»‡ AccessDeniedException sáº½ nÃ©m ra.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Cáº¥u hÃ¬nh cho Login Form.
		http.authorizeRequests().and().formLogin()//
				// Submit URL cá»§a trang login
				.loginProcessingUrl("/login") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/home", true)	//Ä‘Ã¢y Khi Ä‘Äƒng nháº­p thÃ nh cÃ´ng thÃ¬ vÃ o trang nÃ y. userAccountInfo sáº½ Ä‘Æ°á»£c khai bÃ¡o trong controller Ä‘á»ƒ hiá»ƒn thá»‹ trang view tÆ°Æ¡ng á»©ng
				.failureUrl("/login?error=true")		// Khi Ä‘Äƒng nháº­p sai username vÃ  password thÃ¬ nháº­p láº¡i
				.usernameParameter("username")			// tham sá»‘ nÃ y nháº­n tá»« form login á»Ÿ bÆ°á»›c 3 cÃ³ input  name='username'
				.passwordParameter("password")			// tham sá»‘ nÃ y nháº­n tá»« form login á»Ÿ bÆ°á»›c 3 cÃ³ input  name='password'
				// Cáº¥u hÃ¬nh cho Logout Page.
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
				.deleteCookies("JWT")
                .invalidateHttpSession(true)
                .and()
                .csrf().disable();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		http.cors();
	}

}
