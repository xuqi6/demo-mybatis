package com.example.utils;

import com.example.bean.User;
import java.sql.SQLException;

public class Demo {


    public static void main(String[] args) throws SQLException {

        User user = new User();
        user.setUsername("11");
        User user2 = new User();
        user2.setUsername("21");
        System.out.println(user.equals(user2));

//        Connection connection = DBUtils.getConnection("jdbc:sqlserver://106.53.0.192:1433;DatabaseName=crm", "sa", "Cityray83917111");
//
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from  crm_user ");
//        ResultSet resultSet = preparedStatement.executeQuery();
//
//        boolean next = resultSet.next();
//        String string = resultSet.getString(1);
//        System.out.println(string);
//
//        DBUtils.closeAll(connection,preparedStatement,resultSet);

    }

}
