package com.yl.dev.kt.fast.mykotlionblog.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfigure : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/login").setViewName("login")
        registry.addRedirectViewController("/", "/diary/index")
        registry.addRedirectViewController("", "/diary/index")
        super.addViewControllers(registry)
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        super.addInterceptors(registry)
    }
}