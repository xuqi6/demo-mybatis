package com.example.service;

import com.example.bean.DBInfo;

import java.util.List;
import java.util.Map;

public interface DBInfoService {

    List<DBInfo> list();

    Map<String, String> save(DBInfo dbInfo);

    Map<String, String> delete(Integer dbInfo);
}
