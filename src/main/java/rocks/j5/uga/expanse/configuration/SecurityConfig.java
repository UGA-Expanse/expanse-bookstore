package rocks.j5.uga.expanse.configuration;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.transaction.interceptor.TransactionInterceptor;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
////import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.access.AccessDeniedHandler;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig {//extends WebSecurityConfigurerAdapter {


//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.httpBasic().disable();
//            .csrf()
//            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .and()
//                .authorizeRequests()
//                .antMatchers("/api/**").authenticated()
//                .antMatchers("/**").permitAll();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }


//    @Override
//    protected void configure() throws Exception {
//        http.cors().and()
//                .authorizeRequests()
//                .antMatchers("/api/users").authenticated()
//                .antMatchers("/**").permitAll();

}


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .cors().and()
//                .csrf().ignoringAntMatchers("/login")
//                .ignoringAntMatchers("/perform_login")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/**").permitAll();
//    }
//}
