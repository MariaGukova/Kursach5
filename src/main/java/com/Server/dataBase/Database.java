package com.Server.dataBase;

import com.Server.constants.Constants;
import com.example.it.model.Admin;
import com.example.it.model.Project;
import com.example.it.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Connection connection;
    private static Statement statement;

    public Database() {
        connectionToDB();
    }

    public void connectionToDB() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection(Constants.HOST_DATABASE + Constants.NAME_DATABASE,
                    Constants.USER_DATABASE,
                    Constants.PASSWORD_DATABASE);
            statement = connection.createStatement();

            System.out.println("Database connection is done");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getUserAuthorization(User user) {//проверка на существование пользователя

        ResultSet resSet = null;
        String select = "SELECT " + Constants.LOGIN + "," + Constants.PASSWORD + " FROM " + Constants.USERS_TABLE +
                " WHERE " + Constants.LOGIN + " =? " + "AND " + Constants.PASSWORD + " =? ";

        try {
            PreparedStatement preparedStmt = connection.prepareStatement(select);
            preparedStmt.setString(1, user.getLogin());
            preparedStmt.setString(2, user.getPassword());

            resSet = preparedStmt.executeQuery();


            System.out.println("Such user exists !");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resSet;
    }


    public void getUserRegistration(User user) {//регистрация пользователя

        try {

            String select = "insert into " + Constants.USERS_TABLE + "(login, password, name, surname) "
                    + "values (?, ?, ?, ?)";


            PreparedStatement preparedStmt = connection.prepareStatement(select);
            preparedStmt.setString(1, user.getLogin());
            preparedStmt.setString(2, user.getPassword());
            preparedStmt.setString(3, user.getName());
            preparedStmt.setString(4, user.getSurname());

            preparedStmt.execute();
            System.out.println("User was registred !");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public ResultSet getAdminAuthorization(Admin admin) {//проверка на существование админа

        ResultSet resSet = null;
        String select = "SELECT " + Constants.ADMIN_LOGIN + "," + Constants.ADMIN_PASSWORD + " FROM " + Constants.ADMIN_TABLE +
                " WHERE " + Constants.ADMIN_LOGIN + " =? " + "AND " + Constants.ADMIN_PASSWORD + " =? ";

        try {
            PreparedStatement preparedStmt = connection.prepareStatement(select);
            preparedStmt.setString(1, admin.getLogin());
            preparedStmt.setString(2, admin.getPassword());

            resSet = preparedStmt.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return resSet;
    }

    public void addProject(Project project) {//добавление проекта
        try (Connection connection = DatabaseConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(// позволяет вставлять значения
                      " insert into "+Constants.PROJECTS_TABLE +" (name, customer, cost, deadline)"
                             + " values (?, ?, ?, ?)")) {

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getCustomer());
            preparedStatement.setString(3, project.getCost());
            preparedStatement.setString(4, project.getDeadline());

            preparedStatement.execute();//выполняем запрос

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Project> getAllProjects() {//просмотр проектов
        List<Project> projects = new ArrayList<>();
        System.out.println(" !1");
        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()

             Statement statement = connection.createStatement()) { //выполняет запрос
            System.out.println(" !2");

            ResultSet resultSet = statement.executeQuery("SELECT " + Constants.PROJECT_ID + " , " + Constants.PROJECT_NAME + " , " + Constants.CUSTOMER + " , "
                    + Constants.COST + " ," + Constants.DEADLINE + " FROM " + Constants.PROJECTS_TABLE);
            System.out.println(" !3");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String customer = resultSet.getString("customer");
                String cost = resultSet.getString("cost");
                String deadline = resultSet.getString("deadline");
                System.out.println(" !4");

                Project project = new Project(id, name, customer, cost, deadline);
                System.out.println(" !5");
                projects.add(project);
                System.out.println(" !6");
            }
        } catch (SQLException e) {
            System.out.println(" !ебууууууууууут");
            e.printStackTrace();
        }

        return projects;
    }


        public void updateProject(Project project) {//обновление проектов после редактирования
            try (Connection connection = DatabaseConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "UPDATE "+Constants.PROJECTS_TABLE +" SET name  = ?, customer = ? ,cost = ?,deadline = ?   WHERE id = ?")) {

                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getCustomer());
                preparedStatement.setString(3, project.getCost());
                preparedStatement.setString(4, project.getDeadline());


                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }




    public void deleteProjectByID(int id) {//удаление проекта
        try (Connection connection = DatabaseConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from projects where id = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void addUser (User user) {//добавление пользователя
        try (Connection connection = DatabaseConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(// позволяет вставлять значения
                     "insert into"+Constants.USERS_TABLE +"(name, surname, login, password)"
                             + " values (?, ?, ?, ?)")) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());


            preparedStatement.execute();//выполняем запрос

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {//просмотр пользователей
            List<User> users = new ArrayList<>();
        System.out.println(" !1");
        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()

             Statement statement = connection.createStatement()) { //выполняет запрос
            System.out.println(" !2");

            ResultSet resultSet = statement.executeQuery("SELECT " + Constants.ID + " , " + Constants.NAME + " , " + Constants.SURNAME + " , "
                    + Constants.LOGIN + " ," + Constants.PASSWORD + " FROM " + Constants.USERS_TABLE);
            System.out.println(" !3");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                System.out.println(" !4");

                    User user = new User(id, name, surname, login, password);
                System.out.println(" !5");
                users.add(user);
                System.out.println(" !6");
            }
        } catch (SQLException e) {
            System.out.println(" !ебууууууууууут");
            e.printStackTrace();
        }

        return users;
    }


    public void updateUser(User user) {//обновление пользователей после редактирования
        try (Connection connection = DatabaseConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update user set name = ?, surname = ?, login = ?, password = ? where id = ?")) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());


            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public void deleteUserByID(int id) {//удаление пользователя
        try (Connection connection = DatabaseConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from users where id = ?")) {

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}






