package gr.hua.ds.freetransportation.security.config;

import gr.hua.ds.freetransportation.RoleTypes;
import gr.hua.ds.freetransportation.security.UserDetService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.accept.HeaderContentNegotiationStrategy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
@Configuration
//@EnableAutoConfiguration(exclude = { MultipartAutoConfiguration.class })
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService());
        dao.setPasswordEncoder(passwordEncoder());
        return dao;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }



@Override
    protected void configure(HttpSecurity http) throws Exception {

//    Disable session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.cors();

        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasAnyAuthority(RoleTypes.ADMIN.toString())
            .antMatchers("/api/unemployment_application/**").hasAnyAuthority(RoleTypes.DEFAULT_USER.toString())
            .antMatchers("/api/oaed_employee/**").hasAnyAuthority(RoleTypes.OAED_EMPLOYEE.toString())
            .antMatchers("/api/unemployed/**").hasAnyAuthority(RoleTypes.UNEMPLOYED.toString())
            .antMatchers("/api/transportation_employee/**").hasAnyAuthority(RoleTypes.TRANSPORTATION_EMPLOYEE.toString())
            .antMatchers("/api/register/**").permitAll()
            .antMatchers("/api/login/**").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().loginPage("/login")
            .usernameParameter("email").permitAll()
            .defaultSuccessUrl("/admin/home", true)
            .and().exceptionHandling().accessDeniedPage("/admin/forbidden")
            .and()
            .logout().logoutUrl("/logout")
            .and().httpBasic();
    }


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // Don't do this in production, use a proper list  of allowed origins
        config.setAllowedOriginPatterns(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }




//    @Bean(name = "filterMultipartResolver")
//    public CommonsMultipartResolver multiPartResolver(){
//        CommonsMultipartResolver resolver = new
//                CommonsMultipartResolver();
//        return resolver;
//    }
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring()
//                .requestMatchers(ignoreNonJsonMediaTypes())
//                .and()
//                .ignoring()
//                .antMatchers(HttpMethod.POST);
//
//        super.configure(web);
//    }
//
//    private MediaTypeRequestMatcher ignoreNonJsonMediaTypes() {
//        MediaTypeRequestMatcher
//                matcher =
//                new MediaTypeRequestMatcher(new HeaderContentNegotiationStrategy(), MediaType.APPLICATION_JSON);
//        matcher.setIgnoredMediaTypes(Collections.singleton(MediaType.ALL));
//        return matcher;
//    }

}