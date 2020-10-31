package com.yl.dev.kt.fast.mykotlionblog.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@EnableCaching
@EnableAsync
@Configuration
class SystemConfig