package gr.hua.ds.freetransportation.security.config;

import gr.hua.ds.freetransportation.security.UserDetService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
            .antMatchers("/api/unemployment_application/**").hasAnyAuthority("DEFAULT_USER")
            .antMatchers("/api/oaed_employee/**").hasAnyAuthority("OAED_EMPLOYEE")
            .antMatchers("/api/unemployed/**").hasAnyAuthority("UNEMPLOYED")
            .antMatchers("/api/transportation_employee/**").hasAnyAuthority("TRANSPORTATION_EMPLOYEE")
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

}