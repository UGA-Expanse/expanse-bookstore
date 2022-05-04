package rocks.j5.uga.expanse.configuration;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import rocks.j5.uga.expanse.filter.CustomAuthenticationFilter;
import rocks.j5.uga.expanse.filter.CustomAuthorizationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;



@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(final HttpSecurity http) throws Exception
    {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean(), bCryptPasswordEncoder);
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");
        http.cors();
        http.csrf().disable();
        http.logout(logout -> logout.deleteCookies("JSESSIONID"));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        http.authorizeRequests().antMatchers(
                "/api/logout",
                "/api/price/**",
                "/api/catalog/**",
                "/api/book/**",
                "/api/category/**",
                "/api/login/**",
                "/api/cart/**",
                "/api/token/refresh").permitAll();
//        http.authorizeRequests().antMatchers(GET, "/api/cart/**").hasAnyAuthority("ROLE_USER", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/user/**").hasAnyAuthority("ROLE_USER", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/admin/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/inventory/**").hasAnyAuthority("ROLE_MERCHANT", "ROLE_VENDOR", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/inventory/**").hasAnyAuthority("ROLE_MERCHANT", "ROLE_VENDOR", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/reports/**").hasAnyAuthority("ROLE_MERCHANT", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers(POST, "/api/checkout/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().antMatchers("/api/affiliates/**").hasAnyAuthority("ROLE_VENDOR", "ROLE_SUPER_ADMIN");
//        http.authorizeRequests().antMatchers(GET, "/api/affiliates/**").hasAnyAuthority("ROLE_VENDOR", "ROLE_SUPER_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

    
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }
}