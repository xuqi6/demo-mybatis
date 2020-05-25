package com.example.service;

import com.example.bean.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TestService {
     List<Test> list();

     Page<Test> listPage(String name, Pageable pageRequest);

}
