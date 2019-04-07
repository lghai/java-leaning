package com.springboot.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

;

@Controller
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public Map<String,String> test(){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("key","value");
        return map;
    }

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home() {
        return "test";
    }
}
