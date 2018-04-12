package br.com.gft.auction.auction2.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private DataSource dataSource;
//
//	@Value("${spring.queries.users-query}")
//	private String usersQuery;
//
//	@Value("${spring.queries.roles-query}")
//	private String rolesQuery;

	// @Override
	// protected void configure(AuthenticationManagerBuilder auth) throws
	// Exception {
	// auth.jdbcAuthentication().usersByUsernameQuery(usersQuery).authoritiesByUsernameQuery(rolesQuery);
	// }
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.jdbcAuthentication().dataSource(dataSource)
//				.usersByUsernameQuery("select nome, senha,situacao from usuario where nome=?")
//				.authoritiesByUsernameQuery("select nome, id_tipo from usuario where nome=?");
//
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        http.addFilterBefore(filter,CsrfFilter.class);
		http
//			.httpBasic()
//			.and()
			.authorizeRequests()
//			.anyRequest()
//			.authenticated()
			.antMatchers("/").permitAll()
			.antMatchers("/bootstrap/**").permitAll()
			.and()
			.csrf().csrfTokenRepository(csrfTokenRepository())
			.and()
			.addFilterAfter(csrfHeaderFilter(), CsrfFilter.class);

	}

	private CsrfTokenRepository csrfTokenRepository() {
		HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
		repository.setHeaderName("X-XSRF-TOKEN");
		return repository;
	}

	private Filter csrfHeaderFilter() {
		return new OncePerRequestFilter() {

			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {
				request.setCharacterEncoding("UTF-8");
				CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
				if (csrfToken != null) {
					Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
					String token = csrfToken.getToken();
					if (cookie == null || token != null && !token.equals(cookie.getValue())) {
						cookie = new Cookie("XSRF-TOKEN", token);
						cookie.setPath("/");
						response.addCookie(cookie);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

}