package com.yl.dev.kt.fast.mykotlionblog.service

import com.yl.dev.kt.fast.mykotlionblog.bean.Diary
import com.yl.dev.kt.fast.mykotlionblog.dao.DiaryRepository
import com.yl.dev.kt.fast.mykotlionblog.kit.DiaryUtils
import com.yl.dev.kt.fast.mykotlionblog.vo.PageVo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.*
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 *日记操作服务
 */
@CacheConfig(cacheNames = ["customers"])
@Service
class DiaryService {
    val log: Logger = LoggerFactory.getLogger(DiaryService::class.java)


    @Autowired
    private
    lateinit var diaryRepository: DiaryRepository


    /**
     *全部保存
     */
    fun saveAll(diaris: List<Diary>) {
        diaryRepository.saveAll(diaris)
    }

    /**
     *最新
     */
    fun findByMax(max: Long): Iterable<Diary> = diaryRepository.findByIdGreaterThan(max)

    /**
     *最大
     */
    fun findMax(): Long {
        return try {
            diaryRepository.findFirstOrderById()
        } catch (ex: Exception) {
            0
        }
    }


    /**
     *字符
     */
    fun uniDiary(diary: Diary): Diary {
        return diary
    }

    /**
     * 表情转义
     */
    fun emDiary(diary: Diary) {

    }


    /**
     *查看
     */
    @Cacheable(key = "'by_'+#id")
    fun view(id: Long): Diary = uniDiary(diaryRepository.findById(id).get())


    /**
     * 保存
     */
    private fun saveBy(diary: Diary): Diary {
        emDiary(diary)
        parseDomain(diary)
        diaryRepository.save(diary)
        return diary
    }

    /**
     * 获取
     */
    fun findOn(id: Long): Diary = diaryRepository.findById(id).orElse(Diary())

    /**
     *
     */
    @Caching(evict = [(CacheEvict(value = ["pages"], allEntries = true))], put = [(CachePut(key = "'by_'+#diary.id"))])
    fun saveNative(diary: Diary) = diaryRepository.save(diary)

    /**
     *保存
     */
    @Caching(evict = [(CacheEvict(value = ["pages"], allEntries = true))], put = [(CachePut(key = "'by_'+#diary.id"))])
    fun save(diary: Diary): Diary = saveBy(diary)


    /**
     * 更新
     */
    @Caching(evict = [(CacheEvict(value = ["pages"], allEntries = true))], put = [(CachePut(key = "'by_'+#diary.id"))])
    fun update(diary: Diary): Diary = saveBy(diary)


    /**
     * 优化分页
     */
    @Cacheable(value = ["pages"], key = "'findByPage_'+#request.pageNumber+'_'+#request.pageSize", condition = "#request.pageNumber lt 3")
    fun page(request: Pageable): PageVo {
        val page = diaryRepository.findPageIds(request)
        val content = diaryRepository.findAll(page.content)
        return PageVo(page, content)
    }

    fun fwPage(pageable: Pageable): PageVo {
        val page = diaryRepository.findPageIds(1, pageable)
        val content = diaryRepository.findAll(page.content)
        return PageVo(page, content)
    }

    /**
     * 按照关键字分页
     */
    fun page(kw: String, pageable: PageRequest): PageVo {
        val page = diaryRepository.findPageIds(kw, pageable)
        val idc: List<Long> = if (page.content.isEmpty()) listOf(0L) else page.content
        val content = diaryRepository.findAll(idc)
        return PageVo(page, content)
    }

    /**
     *解析域名
     */
    private fun parseDomain(diary: Diary) {
        diary.domain = DiaryUtils.domain(diary.url!!)
    }


    /**
     * 搜索引擎
     */
    fun index() {
    }

    fun flush() {
    }

    fun search(k: String): List<Diary> {
        return ArrayList()
    }
}