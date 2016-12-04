package com.pactera.pds.u2.commerce.web.error;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/error")
public class ErrorController {
    //未授权访问
    @RequestMapping(value="/401",method = RequestMethod.GET)
    public String page() {
        return "error/401";
    }
}
