package com.example.service.impl;

import com.example.bean.Test;
import com.example.dao.TestDao;
import com.example.service.TestService;
import org.hibernate.dialect.OracleTypesHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class TestServiceImpl implements TestService{

    @Autowired
    private TestDao testDao;


    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public List<Test> list() {

        StringBuffer sql = new StringBuffer();
        sql.append(" {call sp_patch_server_list_load(?)} ");

        List<Map<String, Object>> list = (List<Map<String, Object>>) jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call sp_patch_server_list_load(?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setString(1, "1");
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
        System.out.println(11);
        return null;
    }




    @Override
    public Page<Test> listPage(String name, Pageable pageRequest) {


        //查询本单位所有记录
        List<Test> testList = testDao.list(name);
        Integer count = 0;
        if (testList != null && !testList.isEmpty()) {
            count = testList.size();
        }
        //分页查询
        List<Test> testRepertorys = testDao.list(name,pageRequest);
        return new PageImpl<Test>(testRepertorys, pageRequest, count);
    }
}
