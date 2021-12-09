package com.Server.dataBase;

import com.Server.constants.Constants;
import com.example.it.model.Otchet;
import com.example.it.model.Project;
import com.example.it.model.User;
import com.example.it.model.Admin;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
                      " insert into "+Constants.PROJECTS_TABLE +" (id, name, customer, cost, deadline, level )"
                             + " values (?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setInt(1, project.getId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getCustomer());
            preparedStatement.setString(4, project.getCost());
            preparedStatement.setString(5, project.getDeadline());
            preparedStatement.setString(6, project.getLevel());

            preparedStatement.execute();//выполняем запрос

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Project> getAllProjects() {//просмотр проектов
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()

             Statement statement = connection.createStatement()) { //выполняет запрос

            ResultSet resultSet = statement.executeQuery("SELECT " + Constants.PROJECT_ID + " , " + Constants.PROJECT_NAME + " , " + Constants.CUSTOMER + " , "
                    + Constants.COST + " , " + Constants.DEADLINE + " , " + Constants.LEVEL + " FROM " + Constants.PROJECTS_TABLE);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String customer = resultSet.getString("customer");
                String cost = resultSet.getString("cost");
                String deadline = resultSet.getString("deadline");
                String level = resultSet.getString("level");


                Project project = new Project(id, name, customer, cost, deadline, level);

                projects.add(project);

            }
        } catch (SQLException e) {

            e.printStackTrace();
        }

        return projects;
    }


        public void updateProject(Project project) {//обновление проектов после редактирования
            try (Connection connection = DatabaseConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "UPDATE "+Constants.PROJECTS_TABLE +" SET name  = ?, customer = ? , cost = ?, deadline = ?, level = ? WHERE id = ?")) {

                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getCustomer());
                preparedStatement.setString(3, project.getCost());
                preparedStatement.setString(4, project.getDeadline());
                preparedStatement.setString(5, project.getLevel());
                preparedStatement.setInt(6, project.getId());

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
                     "insert into "+Constants.USERS_TABLE +"( name, surname, login, password)"
                             + " values ( ?, ?, ?, ?)")) {


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

        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос
            ResultSet resultSet = statement.executeQuery("SELECT " + Constants.ID + " , " + Constants.NAME + " , " + Constants.SURNAME + " , "
                    + Constants.LOGIN + " ," + Constants.PASSWORD + " FROM " + Constants.USERS_TABLE);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");

                User user = new User(id, name, surname, login, password);
                users.add(user);
            }
        } catch (SQLException e) {
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
    public List<Otchet> getOtchet() {//просмотр пользователей
        List<Otchet> otchets = new ArrayList<>();

        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос
            ResultSet resultSet = statement.executeQuery("SELECT " + Constants.OTHET_NAME + " , " + Constants.RISKS + " , "
                    + Constants.PARTICIPANTS + " ," + Constants.OTCHET_DEADLINE + " FROM " + Constants.OTCHET);
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String risks = resultSet.getString("risks");
                String participants = resultSet.getString("participants");
                String deadline = resultSet.getString("deadline");

                Otchet user = new Otchet(name, risks, participants, deadline);
                otchets.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return otchets;
    }


    public static void filewriter() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        ResultSet resultSet = null;
        List<String> data = new ArrayList<String>();
        List<String> head = new ArrayList<String>();
        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()
        ) {
            String sqlQuery = "select * from " + Constants.PROJECTS_TABLE;
            try {
                resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {
                    String id = String.valueOf(resultSet.getInt(1));
                    String name = resultSet.getString(2);
                    String customer =resultSet.getString(3);
                    String cost = resultSet.getString(4);
                    String deadline = resultSet.getString(5);
                    String level = resultSet.getString(6);
                    data.add(id + "\t\t" + name + "\t\t\t\t" +customer + "\t\t" + cost + "\t\t\t\t" + deadline + "\t\t\t\t" + level);
                }
                head.add("\n");
                head.add("ID\t\tName\t\t\t\tCost\t\t\t\tCustomer\t\t\tDeadline\t\t\tReadiness level\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
                writeToFile(head, "AdminOtchet.txt");
                writeToFile(data, "AdminOtchet.txt");
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Запрос " + sqlQuery + " был обработан!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void filewriter1() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        ResultSet resultSet = null;
        List<String> data = new ArrayList<String>();
        List<String> head = new ArrayList<String>();
        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()
        ) {
            String sqlQuery = "select * from " + Constants.OTCHET;
            try {
                resultSet = statement.executeQuery(sqlQuery);
                while (resultSet.next()) {

                    String name = resultSet.getString(1);
                    String risks =resultSet.getString(2);
                    String participants = resultSet.getString(4);
                    String deadline = resultSet.getString(4);

                    data.add(name + "\t\t\t" +risks + "\t\t\t" + participants + "\t\t\t\t" + deadline);
                }
                head.add("\n");
                head.add("Name\t\t\t\tRisks\t\t\t\tParticipants\t\t\tDeadline\n--------------------------------------------------------------------------------------------------------------------------------------------------");
                writeToFile(head, "UserOtchet.txt");
                writeToFile(data, "UserOtchet.txt");
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Запрос " + sqlQuery + " был обработан!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void writeToFile(java.util.List<String> list, String path) {
        BufferedWriter out = null;
        try {
            File file = new File(path);
            out = new BufferedWriter(new FileWriter(file, true));
            for (String s : list) {
                out.write(s);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
        }
    }


}






