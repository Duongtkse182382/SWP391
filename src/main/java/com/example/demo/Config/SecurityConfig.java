package com.example.demo.Config;

import com.example.demo.Security.CustomAuthenticationSuccessHandler;
import com.example.demo.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/login", "/css/**", "/js/**","/encode-password/**","/assets/**").permitAll()
                .antMatchers("/orders", "/seller/products/bill-of-sell/**","/seller/products/bill-of-buy/**", "/customer", "/cashier-profile", "/warranty").hasRole("CASHIER")
                .antMatchers("/dashboard", "/manager/products", "/staff","/counter/**", "/promotion", "/manager/products/create-product/**", "/manager/products/detail-product/**", "/staff/create-new-staff/**", "/staff/edit-staff-profile/**","/manager-profile/**").hasRole("MANAGER")
                .antMatchers("/seller/products", "/orders/listOfOrder", "/products/detail-product/**", "/orders/purchaseOrderDetail/**", "/orders/new-sell-order/**","/orders/sellOrderDetail/**", "/orders/NewPurchaseOrder/**","/seller-profile").hasRole("SELLER")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/perform_login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/login?error=true")
            .and()
            .logout()
                .logoutUrl("/perform_logout")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
            .and()
            .exceptionHandling()
                .accessDeniedPage("/403")
            .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .sessionFixation().migrateSession()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login?expired=true");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}
