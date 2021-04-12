package com.imooc.reader.controller.management;

import com.imooc.reader.entity.User;
import com.imooc.reader.service.UserService;
import com.imooc.reader.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/management")
public class MUserController {
    @Resource
    private UserService userService;

    @RequestMapping("/login.html")
    public ModelAndView showLogin() {
        return new ModelAndView("/management/login");
    }

    @PostMapping("/checkLogin")
    @ResponseBody
    public Map checkLogin(String username, String password, HttpSession session) {
        Map result = new HashMap();
        try {
            User user = userService.checkLogin(username, password);
            session.setAttribute("manageUser", user);
            result.put("code", 0);
            result.put("msg", "success");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            result.put("code", ex.getCode());
            result.put("msg", ex.getMsg());
        }
        return result;
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession)
    {
        httpSession.invalidate();
        return "redirect:/management/login.html";
    }
}
