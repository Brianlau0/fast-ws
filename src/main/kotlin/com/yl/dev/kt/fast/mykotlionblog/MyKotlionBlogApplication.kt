package com.yl.dev.kt.fast.mykotlionblog

import cn.hutool.core.util.StrUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import java.lang.management.ManagementFactory
import java.net.InetAddress

@SpringBootApplication
class MyKotlionBlogApplication

val log: Logger = LoggerFactory.getLogger(MyKotlionBlogApplication::class.java)


fun main(args: Array<String>) {
    runApplication<MyKotlionBlogApplication>(*args)

    @Bean
    fun commandLineRunner(ctx: ApplicationContext): CommandLineRunner {
        return CommandLineRunner { args: Array<String?>? ->
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
            Runtime.getRuntime().exec("cmd /c start $url") // 可以指定自己的路径
        }
    }

}
