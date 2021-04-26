package com.example.Spring.boot.Advance

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.NoOpPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import javax.sql.DataSource

@EnableWebSecurity

class SecurityConfiguration( @Autowired var dataSource: DataSource):WebSecurityConfigurerAdapter() {



    /*
    constructor(_dataSource: DataSource){
        this.super()
        this.dataSource = _dataSource
    }*/

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.jdbcAuthentication()
                ?.dataSource(dataSource)
                ?.usersByUsernameQuery("select username,password,enabled "
                        + "from users "
                        + "where username = ?")
                ?.authoritiesByUsernameQuery("select username,authority  "
                        + "from authorities "
                        + "where username = ?"
                )
                /*
                ?.withDefaultSchema()
                ?.withUser(
                        User.withUsername("user")
                                .password("user")
                                .roles("user")
                )
                ?.withUser(
                        User.withUsername("admin")
                                .password("admin")
                                .roles("admin")
                )
            */

    }
    override fun configure(http: HttpSecurity?) {
        http?.authorizeRequests()
                ?.antMatchers("/admin")?.hasRole("ADMIN")
                ?.antMatchers("/user")?.hasAnyRole("USER","ADMIN")
                ?.antMatchers("/")?.permitAll()
                ?.and()?.formLogin()
    }

    @Bean
    fun getPasswordEncodeer():PasswordEncoder{
        return  NoOpPasswordEncoder.getInstance()
    }
}