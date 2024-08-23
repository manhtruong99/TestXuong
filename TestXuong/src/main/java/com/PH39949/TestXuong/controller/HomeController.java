package com.PH39949.TestXuong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {
    @GetMapping("staff")
    public String showStaff(){
        return "/staff";
    }

    @GetMapping("staff/{id}")
    public String details(){
        return "/details";
    }

    @GetMapping("staff/change/{id}")
    public String change(){
        return "/change";
    }
}
