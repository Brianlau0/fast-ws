package com.yl.dev.kt.fast.mykotlionblog.bean

import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import java.io.Serializable
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size


/**
 * 日志类
 */
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "km_diary")
class Diary : Serializable {

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    //标题
    @get:NotEmpty(message = "标题不能为空")
    @get:Size(message = "标题的长度应该在6与255之间", min = 6, max = 255)
    var title: String? = ""

    //内容
    @get:NotEmpty(message = "内容不能为空")
    @Column(columnDefinition = "longtext")
    var content: String? = ""


    //URL
    var url: String? = ""

    //域名
    var domain: String? = ""

    //审计
    var createTime: Date? = Date()
    var createBy: String? = ""

    var updateTime: Date? = Date()
    var updateBy: String? = ""

    var favoriteFlag: Int = 0


    override fun toString(): String {
        return "Diary(id=$id, title='$title', url='$url', domain='$domain', createTime=$createTime)"
    }
}

