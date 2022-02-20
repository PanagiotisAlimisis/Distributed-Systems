package gr.hua.ds.freetransportation.security.config;

import gr.hua.ds.freetransportation.RoleTypes;
import gr.hua.ds.freetransportation.security.UserDetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Configuration
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

        http.cors();

        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasAnyAuthority(RoleTypes.ADMIN.toString())
            .antMatchers(HttpMethod.GET, "/api/getAll").hasAnyAuthority(RoleTypes.OAED_EMPLOYEE.toString(), RoleTypes.TRANSPORTATION_EMPLOYEE.toString())
            .antMatchers(HttpMethod.PUT, "/api").hasAnyAuthority(RoleTypes.OAED_EMPLOYEE.toString(), RoleTypes.TRANSPORTATION_EMPLOYEE.toString())
            .antMatchers(HttpMethod.GET, "/api").hasAnyAuthority(RoleTypes.UNEMPLOYED.toString())
            .antMatchers(HttpMethod.POST, "/api").hasAnyAuthority(RoleTypes.DEFAULT_USER.toString(), RoleTypes.UNEMPLOYED.toString())
            .antMatchers("/api/register/**").permitAll()
            .antMatchers("/api/login/**").permitAll()
            .antMatchers("/css/**").permitAll()
            .anyRequest().authenticated()
            .and().formLogin().loginPage("/login")
            .usernameParameter("email").permitAll()
            .defaultSuccessUrl("/admin/home", true)
            .and().exceptionHandling().accessDeniedPage("/admin/forbidden")
            .and()
            .logout().logoutUrl("/logout")
            .and().httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
           web
                   .ignoring()
                   .antMatchers("/resources/**", "/static/**");

    }
}