package com.lukasz.wolski.DatingAppBackend

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import kotlin.Throws
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import com.lukasz.wolski.DatingAppBackend.JWTAuthorizationFilter
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsUtils
import java.lang.Exception

@EnableWebSecurity
@Configuration
class WebSecurityConfig : WebSecurityConfigurerAdapter() {
    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.cors().and().csrf().disable()
            .addFilterAfter(JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
            .authorizeRequests()
            .antMatchers("/auth/token/refresh").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/register").permitAll()
            .antMatchers(HttpMethod.GET, "/dictionary/getAllGenderDictionary").permitAll()
            .antMatchers(HttpMethod.GET, "/dictionary/getAllOrientationDictionary").permitAll()
            .antMatchers(HttpMethod.POST, "/auth/registerDetails").permitAll()
            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
            .anyRequest().authenticated()




    }

}