package com.yl.dev.kt.fast.mykotlionblog.vo

import com.yl.dev.kt.fast.mykotlionblog.bean.Diary


data class PageVo(
        //    分页
        val page: org.springframework.data.domain.Page<Long>? = null,
        //    内容
        val content: Iterable<Diary>? = null
)