package com.yl.dev.kt.fast.mykotlionblog

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyKotlionBlogApplication

val log: Logger = LoggerFactory.getLogger(MyKotlionBlogApplication::class.java)


fun main(args: Array<String>) {
    runApplication<MyKotlionBlogApplication>(*args)
}

