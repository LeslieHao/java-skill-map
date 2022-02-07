package com.hh.skillspring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author HaoHao
 * @date 2022/1/12 11:05 下午
 */
@Controller
@RequestMapping("vvv")
public class ThisController {

    @GetMapping("doSomeThing")
    public void doSomeThing() {
        System.out.println("doSomeThing1111");
    }
}
