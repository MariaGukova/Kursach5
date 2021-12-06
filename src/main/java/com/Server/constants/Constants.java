package com.Server.constants;

public class Constants {

    public static   final int PORT = 8888;

    public static final String  HOST_DATABASE ="jdbc:postgresql://localhost:5432/";
    public static final String  NAME_DATABASE ="postgres";
    public static final String  USER_DATABASE ="postgres";
    public static final String  PASSWORD_DATABASE ="0636";

    public static final String USERS_TABLE = "users";
    public static final String ID="id";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";


    public static final String PROJECTS_TABLE = "projects";
    public static final String PROJECT_ID="id";
    public static final String PROJECT_NAME = "name";
    public static final String CUSTOMER = "customer";
    public static final String COST = "cost";
    public static final String DEADLINE = "deadline";

    public static final String ADMIN_TABLE = "admin";
    public static final String ADMIN_ID="id";
    public static final String ADMIN_LOGIN = "login";
    public static final String ADMIN_PASSWORD = "password";



}