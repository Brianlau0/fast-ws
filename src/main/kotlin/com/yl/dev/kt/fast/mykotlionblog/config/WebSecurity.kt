package com.yl.dev.kt.fast.mykotlionblog.config

import com.yl.dev.kt.fast.mykotlionblog.security.MyDisableUrlSessionFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.AuthenticationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class WebSecurity : WebSecurityConfigurerAdapter() {

    @Autowired
    private val myDisableUrlSessionFilter: MyDisableUrlSessionFilter? = null

    private val REALM = "MY_TEST_REALM"

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.authorizeRequests().antMatchers("/diary/**", "/db/**", "/site/**").authenticated().anyRequest().permitAll()
                .and().formLogin().loginPage("/login").failureUrl("/login?sr=fail").permitAll()
                .and().logout().logoutRequestMatcher(AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?sr=logout").permitAll()
                .and().rememberMe().rememberMeParameter("remember_me")
                .and().sessionManagement().maximumSessions(10).expiredUrl("/login?sr=expired").maxSessionsPreventsLogin(true)
                .and().disable().csrf().disable()

//        http.httpBasic().realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint())

        //严禁sid
//        http.addFilterBefore(myDisableUrlSessionFilter, UsernamePasswordAuthenticationFilter::class.java)
        //json登录
        //http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

    }

    @Bean
    fun getBasicAuthEntryPoint(): CustomBasicAuthenticationEntryPoint {
        return CustomBasicAuthenticationEntryPoint()
    }


    /**
     * 账号和权限
     *
     * @param auth
     * @throws Exception
     */
    @Throws(Exception::class)
    public override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.inMemoryAuthentication().passwordEncoder(MyPasswordEncoder()).withUser("brian.lau").password("P@ssw0rd|20190403").roles("USERS")
    }

    class MyPasswordEncoder : PasswordEncoder {
        override fun encode(p0: CharSequence?): String {
            return p0.toString()
        }

        override fun matches(p0: CharSequence?, p1: String?): Boolean {
            return p1.equals(p0.toString())
        }

    }

    class CustomBasicAuthenticationEntryPoint : BasicAuthenticationEntryPoint() {

        @Throws(IOException::class, ServletException::class)
        override fun commence(request: HttpServletRequest?,
                              response: HttpServletResponse,
                              authException: AuthenticationException?) {

            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.addHeader("WWW-Authenticate", "Basic realm=$realmName")

            val writer = response.writer
            writer.println("HTTP Status 401 : " + authException!!.message)
        }

        @Throws(Exception::class)
        override fun afterPropertiesSet() {
            realmName = "MY_TEST_REALM"
            super.afterPropertiesSet()
        }
    }


}


