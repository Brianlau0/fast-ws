package com.yl.dev.kt.fast.mykotlionblog.config

import cn.hutool.core.util.StrUtil
import com.yl.dev.kt.fast.mykotlionblog.log
import org.springframework.boot.CommandLineRunner
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import java.lang.management.ManagementFactory
import java.net.InetAddress

@EnableScheduling
@EnableCaching
@EnableAsync
@Configuration
class SystemConfig {
    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
            println("startup")
            val env = ctx.environment
            val ip = InetAddress.getLocalHost().hostName
            val port = StrUtil.emptyToDefault(env.getProperty("server.port"), "8080")
            val path = StrUtil.nullToEmpty(env.getProperty("server.servlet.context-path"))

            val jvmName = ManagementFactory.getRuntimeMXBean().name
            val url = "http://$ip:$port$path"


            log.info(
                "自动加载指定的页面 ={}, jvm流程名称 = {}",
                url,
                jvmName.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
            )
//            Runtime.getRuntime().exec("cmd /c start $url") // 可以指定自己的路径

        }
    }
}