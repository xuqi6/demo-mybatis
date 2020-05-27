package com.example.service.impl;

import com.example.bean.DBInfo;
import com.example.service.DBInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DBInfoServiceImpl implements DBInfoService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<DBInfo> list() {
        List<Map<String, Object>> list = (List<Map<String, Object>>) jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call sp_patch_server_list_load(?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, 1);
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException {
                List<Map<String, Object>> list = new ArrayList<>();
                ResultSet rs = cs.executeQuery();
                ResultSetMetaData rmd = rs.getMetaData();
                int columnCount = rmd.getColumnCount();
                System.out.println("columnCount: " + columnCount);
                while (rs.next()) {
                    Map<String, Object> rowMap = new HashMap<>(columnCount);
                    for (int i = 1; i <= columnCount; i++) {
                        rowMap.put(rmd.getColumnName(i), rs.getObject(i));
                    }
                    list.add(rowMap);
                }
                rs.close();
                return list;

            }
        });
        List<DBInfo> dbInfos = new ArrayList<>();
        if(null!= list & list.size()>0){
            for (Map<String, Object> map : list) {
                if(null != map & map.size()>0){
                    //登录成功
                    DBInfo dbInfo = new DBInfo();
                    if(null!=map.get("server_id")){
                        dbInfo.setId(Integer.valueOf(map.get("server_id").toString()));
                    }
                    if(null!=map.get("serv_name")){
                        dbInfo.setServiceName(map.get("serv_name").toString());
                    }
                    if(null!=map.get("db_url")){
                        dbInfo.setUrl(map.get("db_url").toString());
                    }
                    if(null!=map.get("db_port")){
                        dbInfo.setPort(map.get("db_port").toString());
                    }
                    if(null!=map.get("db_name")){
                        dbInfo.setDbName(map.get("db_name").toString());
                    }
                    if(null!=map.get("db_user")){
                        dbInfo.setDbUser(map.get("db_user").toString());
                    }
                    if(null!=map.get("db_password")){
                        dbInfo.setDbPassword(map.get("db_password").toString());
                    }
                    if(null!=map.get("patch_id")){
                        dbInfo.setPatchId(map.get("patch_id").toString());
                    }
                    if(null!=map.get("patch_name")){
                        dbInfo.setPatchName(map.get("patch_name").toString());
                    }
                    dbInfos.add(dbInfo);
                }
            }
        }

        return dbInfos;
    }

    @Override
    public Map<String, String> save(DBInfo dbInfo) {
        return (Map<String, String>) jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call sp_patch_server_save(?,?,?,?,?,?,?,?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                if(dbInfo.getId()!=null){
                    //修改
                    cs.setInt("@user_id", 1);
                    cs.setInt("@server_id", dbInfo.getId());
                }else{
                    //新增
                    cs.setInt("@user_id", 0);
                    cs.setInt("@server_id", 0);
                }
                cs.setString("@server_name", dbInfo.getServiceName());
                cs.setString("@db_url", dbInfo.getUrl());
                cs.setString("@db_name", dbInfo.getDbName());
                cs.setString("@db_port", dbInfo.getPort());
                cs.setString("@db_user", dbInfo.getDbUser());
                cs.setString("@db_password", dbInfo.getDbPassword());
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException {
                Map<String, String> maps = new HashMap<>();
                ResultSet rs = cs.executeQuery();
                ResultSetMetaData rmd = rs.getMetaData();
                int columnCount = rmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        maps.put(rmd.getColumnName(i), String.valueOf(rs.getObject(i)));
                    }
                }
                rs.close();
                return maps;
            }
        });
    }

    @Override
    public Map<String, String> delete(Integer id) {
        return (Map<String, String>) jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call sp_patch_server_delete(?,?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt("@user_id", 0);
                cs.setInt("@server_id", id);
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException {
                Map<String, String> maps = new HashMap<>();
                ResultSet rs = cs.executeQuery();
                ResultSetMetaData rmd = rs.getMetaData();
                int columnCount = rmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        maps.put(rmd.getColumnName(i), String.valueOf(rs.getObject(i)));
                    }
                }
                rs.close();
                return maps;
            }
        });
    }


}
