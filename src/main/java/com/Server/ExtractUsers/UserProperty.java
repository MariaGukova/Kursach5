package com.Server.ExtractUsers;

import com.example.it.User;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserProperty {
    private IntegerProperty id;
    private StringProperty name;
    private StringProperty surname;
    private StringProperty login;
    private StringProperty password;


    public UserProperty(User user) {
        id = new SimpleIntegerProperty(user.getId());
        name = new SimpleStringProperty(user.getName());
        surname = new SimpleStringProperty(user.getSurname());
        login = new SimpleStringProperty(user.getLogin());
        password = new SimpleStringProperty(user.getPassword());

    }

    public User toUser() {
        return new User(id.intValue(),
                name.getValue(),
                surname.getValue(),
                login.getValue(),
                password.getValue());
    }

    public int getId() {
        return id.get();
    }
    // они используются неявно
    public IntegerProperty idProperty() {
        return id;
    }


    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }


    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }


    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }



    public void setName(String name) {
        this.name.set(name);
    }
    public void setSurname(String surname) {
        this.surname.set(surname);
    }
    public void setLogin(String login) {
        this.login.set(login);
    }
    public void setPassword(String password) {
        this.password.set(password);
    }


}
