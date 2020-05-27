package com.example.controller;

import com.example.bean.User;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    private LoginService loginService;



    //respon

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public User login(@RequestBody User user){
        System.out.println(user);
        return loginService.login(user);
    }


}
