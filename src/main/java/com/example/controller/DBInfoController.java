package com.example.controller;

import com.example.bean.DBInfo;
import com.example.bean.User;
import com.example.service.DBInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/dbInfo")
@RestController
public class DBInfoController {

    @Autowired
    private DBInfoService dBService;

    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public List<DBInfo> list(@RequestBody DBInfo dbInfo){
        return dBService.list();
    }


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public Map<String, String> save(@RequestBody DBInfo dbInfo){
        return dBService.save(dbInfo);
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public Map<String, String> delete(@RequestBody DBInfo dbInfo){
        return dBService.delete(dbInfo.getId());
    }

}
