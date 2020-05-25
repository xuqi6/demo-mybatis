package com.example.service.impl;

import com.example.bean.User;
import com.example.dao.LoginDao;
import com.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public User login(User user){
        Map<String, Object> map = (Map<String, Object>) jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call sp_crm_login_user_info(?,?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setString(1, user.getUsername());
                String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
                cs.setString(2, md5Password);
                //cs.setString(1, "supervisor");
                //cs.setString(2, "E10ADC3949BA59ABBE56E057F20F883E");
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException {
                ResultSet rs = cs.executeQuery();
                ResultSetMetaData rmd = rs.getMetaData();
                int columnCount = rmd.getColumnCount();
                System.out.println("columnCount: " + columnCount);
                Map<String, Object> rowMap = new HashMap<>(columnCount);
                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        rowMap.put(rmd.getColumnName(i), rs.getObject(i));
                        System.out.println(rmd.getColumnName(i));
                        System.out.println(rs.getObject(i));
                    }
                }
                rs.close();
                return rowMap;

            }
        });
        if(null != map && map.size()>0){
            //登录成功
            User result = new User();
            result.setUsername(map.get("crm_uname").toString());
            result.setId(Long.valueOf(map.get("crm_uid").toString()));
            return result;
        }
        return null;
    }


}
