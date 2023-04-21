package com.superphantomman.cook_with_me.sections.home;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping( "/")
public class HomeController   {

    @GetMapping
    public String homePage(HttpSession httpSession){
        return "/home/home";

    }
}