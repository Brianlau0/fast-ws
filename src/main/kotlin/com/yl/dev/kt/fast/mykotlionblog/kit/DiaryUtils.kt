@file:Suppress("INTEGER_OVERFLOW")

package com.yl.dev.kt.fast.mykotlionblog.kit

import java.io.File
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL
import java.nio.file.Files
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import javax.servlet.http.HttpServletRequest

/**
 *工具类
 */
object DiaryUtils {
    private const val EMPTY: String = ""
    private const val LONG_DATE_STR = "yyyyMMddHHmmss"
    private const val LONG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"

    /**
     * 字符串为空
     *
     * @param str
     * @return
     */
    private fun isEmpty(str: CharSequence?): Boolean {
        return str.isNullOrEmpty()
    }

    /**
     * 字符串不为空
     *
     * @param str
     * @return
     */
    fun isNotEmpty(str: CharSequence): Boolean {
        return !isEmpty(str)
    }

    /**
     * 创建List
     *
     * @param <E>
     * @return
    </E> */
    fun <E> newArrayList(): ArrayList<E> {
        return ArrayList()
    }

    /**
     * 创建Map
     *
     * @param <K>
     * @param <V>
     * @return
    </V></K> */
    fun <K, V> newHashMap(): HashMap<K, V> {
        return HashMap()
    }

    /**
     * 获取文件
     *
     * @param directory
     * @return
     */
    fun getFile(directory: String, name: String): File {
        return File(directory, name)
    }

    /**
     * 获取文件
     *
     * @return
     */
    fun getFile(name: String): File {
        return File(name)
    }

    /**
     * 列表文件
     *
     * @param directory
     * @return
     */
    private fun listFiles(directory: File): List<File> {
        return Arrays.asList(*directory.listFiles()!!)
    }


    /**
     * 列表文件
     *
     * @param directory
     * @return
     */
    fun listFiles(directory: String): List<File> {
        return listFiles(getFile(directory))
    }

    fun equals(str1: CharSequence, str2: CharSequence): Boolean {
        return equals(str1, str2, false)
    }

    fun equalsIgnoreCase(str1: CharSequence, str2: CharSequence): Boolean {
        return equals(str1, str2, true)
    }

    private fun equals(str1: CharSequence?, str2: CharSequence?, ignoreCase: Boolean): Boolean {
        if (null == str1) {
            // 只有两个都为null才判断相等
            return str2 == null
        }
        if (null == str2) {
            // 字符串2空，字符串1非空，直接false
            return false
        }

        return if (ignoreCase) {
            str1.toString().equals(str2.toString(), ignoreCase = true)
        } else {
            str1 == str2
        }
    }

    /**
     * 文件删除
     *
     * @param directory
     * @param file
     */
    @Throws(IOException::class)
    fun delete(directory: String, file: String) {
        Files.delete(getFile(directory, file).toPath())
    }

    /**
     * 取得访问参数
     *
     * @param request
     * @return
     */
    fun getHeadersInfo(request: HttpServletRequest): Map<String, String> {
        val map = HashMap<String, String>()
        val headerNames = request.headerNames
        while (headerNames.hasMoreElements()) {
            val key = headerNames.nextElement() as String
            val value = request.getHeader(key)
            map[key] = value
        }
        return map
    }

    /**
     * 解析域名
     *
     * @param url
     * @return
     */
    fun domain(url: String): String {
        return try {
            val url1 = URL(url)
            url1.host

        } catch (e: MalformedURLException) {
            EMPTY
        }

    }

    /**
     * 格式化日期
     *
     * @param date
     * @return
     */
    fun getLongDate(date: Long): String {
        return SimpleDateFormat(LONG_DATE_FORMAT).format(Date(date))
    }

    /**
     *
     */
    fun getYmd(): String {
        return SimpleDateFormat(LONG_DATE_STR).format(Date());
    }

    /**
     * 格式化文件大小
     *
     * @param size
     * @return
     */
    fun getFileSize(size: Long): String {
        val baseSize = 1024
        val format = DecimalFormat("####.00")
        return when {
            size < baseSize -> {
                size.toString() + "bytes"
            }
            size < baseSize * baseSize -> {
                val kbSize = size / 1024f
                format.format(kbSize.toDouble()) + "KB"
            }
            size < baseSize * baseSize * baseSize -> {
                val mbSize = size.toFloat() / 1024f / 1024f
                format.format(mbSize.toDouble()) + "MB"
            }
            size < baseSize * baseSize * baseSize * baseSize -> {
                val gbSize = size.toFloat() / 1024f / 1024f / 1024f
                format.format(gbSize.toDouble()) + "GB"
            }
            else -> {
                "size: error"
            }
        }
    }

}