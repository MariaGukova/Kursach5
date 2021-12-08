package com.example.it;

import java.io.Serializable;

public class User implements Serializable {
    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;


    public User( String name,   // когда клиент присылает информацию
                    String surname,
                    String login,
                    String password){
        this.name = name;
        this.login = login;
        this.surname = surname;
        this.password = password;

    }


    public User( int id,// когда читаем БД
                    String name,
                    String surname,
                    String login,
                    String password){

        this.id = id;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public User() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
