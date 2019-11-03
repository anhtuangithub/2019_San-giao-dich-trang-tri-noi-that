package com.luanvan.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
    private UserDetailsService userDetailsService;
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/noithat246.vn").permitAll()
                .antMatchers("/noithat246.vn/tai-khoan/*").authenticated()
                .antMatchers("/noithat246.vn/form-send-mail").hasRole("STORE")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/store/**").hasRole("STORE")
                .and()
            .formLogin()
                .loginProcessingUrl("/noithat246.vn/dang-nhap")
                .loginPage("/noithat246.vn/dang-nhap")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/noithat246.vn")
                .failureUrl("/noithat246.vn/dang-nhap?error")
                .permitAll()
                .and()
            .logout()                                                                
                .logoutUrl("/logout")                                                 
                .logoutSuccessUrl("/noithat246.vn")                                                                                
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403");
        http.csrf().disable();
    }
}
