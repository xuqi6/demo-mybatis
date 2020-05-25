package com.example.controller;

import com.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("demo")
    public String test(){
        return "demo";
    }

//    @RequestMapping("list")
//    public List<Test> list(){
//        return testService.list();
//    }


//    @RequestMapping(value = "/listPage",method = RequestMethod.POST)
//    public Page<Test> listPage(@RequestBody(required = false) String name,
//                          Pageable pageRequest) {
//        Page<Test> all = testService.listPage(name,pageRequest);
//        return new PageImpl<Test>(all.getContent(), pageRequest, all.getTotalElements());
//    }
}
