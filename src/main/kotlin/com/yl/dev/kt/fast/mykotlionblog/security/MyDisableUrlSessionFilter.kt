package com.yl.dev.kt.fast.mykotlionblog.security

import org.springframework.stereotype.Component

import javax.servlet.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponseWrapper
import java.io.IOException

/**
 * 用于客户端第一次访问时去掉URL中的jsessionid
 */
@Component
class MyDisableUrlSessionFilter : Filter {

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {

        // System.out.println("3.过滤器MyDisableUrlSessionFilter");
        // skip non-http requests
        if (request !is HttpServletRequest) {
            chain.doFilter(request, response)
            return
        }

        val httpResponse = response as HttpServletResponse

        // clear session if session id in URL
        if (request.isRequestedSessionIdFromURL) {
            val session = request.session
            session?.invalidate()
        }

        // wrap response to remove URL encoding
        val wrappedResponse = object : HttpServletResponseWrapper(httpResponse) {

            override fun encodeRedirectURL(url: String): String {
                return url
            }


            override fun encodeURL(url: String): String {
                return url
            }
        }

        // process next request in chain
        chain.doFilter(request, wrappedResponse)

    }

    override fun destroy() {

    }

}
