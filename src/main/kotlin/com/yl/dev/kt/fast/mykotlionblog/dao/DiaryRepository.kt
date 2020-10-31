package com.yl.dev.kt.fast.mykotlionblog.dao

import com.yl.dev.kt.fast.mykotlionblog.bean.Diary
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * 日志的数据库操作
 */
interface DiaryRepository : PagingAndSortingRepository<Diary, Long> {

    /**
     *查询
     */
    @Query("SELECT d FROM Diary d")
    override fun findAll(pageable: Pageable): Page<Diary>

    /**
     *查询编号
     */
    @Query("SELECT d.id FROM Diary d")
    fun findPageIds(pageable: Pageable): Page<Long>

    /**
     *查询编号
     */
    @Query("SELECT d.id FROM Diary d WHERE d.title LIKE  %?1%")
    fun findPageIds(kw: String, pageable: Pageable): Page<Long>

    /**
     * 收藏类
     */
    @Query("select d.id from Diary d where d.favoriteFlag=?1")
    fun findPageIds(flag: Int, pageable: Pageable): Page<Long>

    /**
     *最终查询
     */
    @Query("SELECT d FROM Diary d WHERE id IN ?1 ORDER BY d.createTime DESC")
    fun findAll(ids: List<Long>): Iterable<Diary>

    /**
     *大于，数据同步
     */
    fun findByIdGreaterThan(max: Long): Iterable<Diary>


    /**
     * 最大的编码
     */
    @Query("SELECT MAX(id) FROM  Diary ")
    fun findFirstOrderById(): Long
}