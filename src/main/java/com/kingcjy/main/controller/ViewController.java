package com.kingcjy.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @GetMapping("/sign/signIn")
    public ModelAndView signIn() {
        return new ModelAndView("sign/signIn");
    }

    @GetMapping("/sign/signUp")
    public ModelAndView signUp() {
        return new ModelAndView("sign/signUp");
    }

    @RequestMapping("/")
    public ModelAndView ping() {
        return new ModelAndView("main/main");
    }

    @GetMapping("/{dir1}/{dir2}")
    public ModelAndView modelAndView(@PathVariable String dir1,
                                     @PathVariable String dir2) {
        return new ModelAndView(dir1 + '/' + dir2);
    }
}
