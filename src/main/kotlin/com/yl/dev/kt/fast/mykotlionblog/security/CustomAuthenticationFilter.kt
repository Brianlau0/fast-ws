package com.yl.dev.kt.fast.mykotlionblog.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.io.IOException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


class CustomAuthenticationFilter : UsernamePasswordAuthenticationFilter() {

    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse?): Authentication {

        //attempt Authentication when Content-Type is json
        if (request.contentType == MediaType.APPLICATION_JSON_UTF8_VALUE || request.contentType == MediaType.APPLICATION_JSON_VALUE) {

            //use jackson to deserialize json
            val mapper = ObjectMapper()
            var authRequest: UsernamePasswordAuthenticationToken? = null
            try {
                request.inputStream.use { `is` ->
                    val authenticationBean = mapper.readValue(`is`, AuthenticationBean::class.java)
                    authRequest = UsernamePasswordAuthenticationToken(
                            authenticationBean.username, authenticationBean.password)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                authRequest = UsernamePasswordAuthenticationToken(
                        "", "")
            } finally {
                setDetails(request, authRequest!!)
                return this.authenticationManager.authenticate(authRequest)
            }
        } else {
            return super.attemptAuthentication(request, response)
        }//transmit it to UsernamePasswordAuthenticationFilter
    }


    class AuthenticationBean {
        var username: String? = null
        var password: String? = null
    }
}