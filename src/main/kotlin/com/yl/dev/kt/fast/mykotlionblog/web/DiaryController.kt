package com.yl.dev.kt.fast.mykotlionblog.web

import cn.hutool.core.util.StrUtil
import com.yl.dev.kt.fast.mykotlionblog.bean.Diary
import com.yl.dev.kt.fast.mykotlionblog.kit.Constants.ACT_TYPE
import com.yl.dev.kt.fast.mykotlionblog.kit.Constants.CREATE
import com.yl.dev.kt.fast.mykotlionblog.kit.Constants.INDEX
import com.yl.dev.kt.fast.mykotlionblog.kit.Constants.UPDATE
import com.yl.dev.kt.fast.mykotlionblog.kit.Constants.VIEW
import com.yl.dev.kt.fast.mykotlionblog.service.DiaryService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import javax.validation.Valid
import kotlin.math.abs

@RequestMapping("/diary")
@Controller
class DiaryController(private val diaryService: DiaryService) {
    /**
     * 常量定义
     */
    companion object {
        //
    }

    val str = "An :grinning:awesome :smiley:string &#128516;with a few :wink:emojis!"

    /**
     * 最大
     */
    @ResponseBody
    @PostMapping("/max")
    fun max(): Long = diaryService.findMax()

    /**
     * 同步
     */
    @ResponseBody
    @PostMapping("/by-max/{id}")
    fun byMax(@PathVariable id: Long): Iterable<Diary> = diaryService.findByMax(id)

    /**
     *首页
     */
    @GetMapping("/index", "", "/")
    fun index(model: Model, @RequestParam(value = "kw", required = false) kw: String?): String = index(model, 0, kw)

    /**
     *状态
     */
    @ModelAttribute("prefix")
    fun prefix() = "diary"


    /**
     * 分页
     */
    @GetMapping("/index/{page}")
    fun index(model: Model, @PathVariable("page") @Valid page: Int, @RequestParam(value = "kw", required = false) kw: String?): String {
        model.addAttribute(ACT_TYPE, INDEX)
        model.addAttribute("baseUrl", "/diary/index/")
        val sort = Sort.by(Sort.Direction.DESC, "createTime")
        val request = PageRequest.of(page, 20, sort)
        //关键字
        if (StrUtil.equals(kw, "fw")) {
            val pageVo = diaryService.fwPage(request)
            model.addAttribute("kw", "fw")
            model.addAttribute("page", pageVo.page)
            model.addAttribute("content", pageVo.content)
        } else {
            val pageVo = diaryService.page(request)
            model.addAttribute("page", pageVo.page)
            model.addAttribute("content", pageVo.content)
        }
        return "diary"
    }

    /**
     * 查询
     */
    @GetMapping("/search")
    fun search(model: Model, @RequestParam(value = "k", required = false, defaultValue = "") k: String): String {
        if (StrUtil.isEmpty(k)) return "redirect:/diary/index/1"
        model.addAttribute("op", "search")
        model.addAttribute("kw", k)
        model.addAttribute("content", diaryService.search(k))
        return "diary"
    }

    /**
     * 查看
     */
    @GetMapping("/{id}")
    fun view(@PathVariable("id") id: Long, model: Model): String {
        model.addAttribute(ACT_TYPE, VIEW)
        model.addAttribute("e", str)
        model.addAttribute("diary", diaryService.view(id))
        return "diary"
    }


    /**
     * 更新
     */
    @ResponseBody
    @GetMapping("/fav/{id}")
    fun fav(@PathVariable("id") id: Long): String {
        val diary = diaryService.findOn(id)
        diary.favoriteFlag = abs(diary.favoriteFlag - 1)
        diaryService.saveNative(diary)
        return "ok"
    }

    /**
     *保存表单
     */
    @GetMapping("/create")
    fun createForm(model: Model): String {
        model.addAttribute(ACT_TYPE, CREATE)
        model.addAttribute("prefix", "diary_create")
        model.addAttribute("diary", Diary())
        return "diary"
    }

    /**
     * 执行保存
     */
    @PostMapping("/create")
    fun create(model: Model, @Valid @ModelAttribute diary: Diary, result: BindingResult, redirectAttributes: RedirectAttributes): String {
        if (result.hasErrors()) {
            model.addAttribute(ACT_TYPE, CREATE)
            model.addAttribute("diary", diary)
            return "diary"
        }
        diaryService.save(diary)
        redirectAttributes.addFlashAttribute("msg", "日志保存成功")
        return "redirect:/diary"
    }

    /**
     * 更新
     */
    @GetMapping("/{id}/update")
    fun updateForm(@PathVariable("id") diary: Diary, model: Model): String {
        model.addAttribute(ACT_TYPE, UPDATE)
        model.addAttribute("prefix", "diary_update")
        model.addAttribute("diary", diary)
        return "diary"
    }

    /**
     * 修改
     */
    @PostMapping("/{id}/update")
    fun update(model: Model, @Valid @ModelAttribute("diary") diary: Diary, result: BindingResult, redirectAttributes: RedirectAttributes): String {
        if (result.hasErrors()) {
            model.addAttribute(ACT_TYPE, UPDATE)
            model.addAttribute("diary", diary)
            return "diary"
        }
        diaryService.update(diary)
        redirectAttributes.addFlashAttribute("msg", "日志更新成功")
        return "redirect:/diary"
    }


}