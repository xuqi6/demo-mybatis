package com.example.bean;

import lombok.Data;

@Data
public class DBInfo {

    private Integer id;

    private String serviceName;

    private String url;

    private String port;

    private String dbName;

    private String dbUser;

    private String dbPassword;

    private String patchId;

    private String patchName;


}

