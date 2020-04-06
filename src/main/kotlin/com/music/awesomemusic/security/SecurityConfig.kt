package com.music.awesomemusic.security

import com.music.awesomemusic.services.AwesomeUserDetailsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var userDetailsService: AwesomeUserDetailsService

    @Autowired
    lateinit var jwtTokenProvider: JwtTokenProvider

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }


    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.userDetailsService(userDetailsService)?.passwordEncoder(passwordEncoder())
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder(13)
    }


    override fun configure(http: HttpSecurity) {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/api/user/register", "/").permitAll()
                .antMatchers("/api/user/sign-in").permitAll()
                .anyRequest().authenticated()
//                .and()
//                .formLogin()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .apply(JwtConfigurer(jwtTokenProvider))
    }

}