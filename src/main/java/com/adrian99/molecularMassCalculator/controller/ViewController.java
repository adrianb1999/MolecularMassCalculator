package com.adrian99.molecularMassCalculator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {
    @RequestMapping({"/","/index","/index.html"})
    public String mainPage(){
        return "index";
    }
}
