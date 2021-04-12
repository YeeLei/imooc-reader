package com.imooc.reader.controller.management;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.imooc.reader.entity.Book;
import com.imooc.reader.entity.Evaluation;
import com.imooc.reader.service.EvaluationService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台短评管理
 */
@Controller
@RequestMapping("/management/evaluation")
public class MEvaluationController {
    @Resource
    private EvaluationService evaluationService;

    @RequestMapping("/index.html")
    public ModelAndView showEvaluation(HttpSession session){
        Object manageUser = session.getAttribute("manageUser");
        if (manageUser==null) {
            return new ModelAndView("/management/login");
        }
        return new ModelAndView("/management/evaluation");
    }

    /**
     * 分页查询所有评论
     * @param page 第几页
     * @param limit 每页显示条数
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public Map list(Integer page,Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        IPage<Evaluation> pageObject = evaluationService.paging(page, limit);
        Map result = new HashMap();
        result.put("code", "0");
        result.put("msg", "success");
        //当前页数据
        result.put("data", pageObject.getRecords());
        //未分页时记录总数
        result.put("count", pageObject.getTotal());
        return result;
    }

    @PostMapping("/disable")
    @ResponseBody
    public Map disable(Long evaluationId,String reason) {
        Map result = new HashMap();
        try {
            evaluationService.disableEvaluation(evaluationId,reason);
            result.put("code","0");
            result.put("msg", "success");
        } catch (BussinessException ex) {
            result.put("code", ex.getCode());
            result.put("msg", ex.getMessage());
        }
        return result;
    }
}
