package com.Server.dataBase;

import com.Server.constants.Constants;
import com.Server.server.DatabaseConnectionProvider;
import com.example.it.Admin;
import com.example.it.Project;
import com.example.it.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {

   private static Connection connection;
    private static Statement statement;

    public Database() {
        connectionToDB();
        Tables tables = new Tables(connection, statement);
        tables.createTablesInDataBase();
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



        public  ResultSet getUserAuthorization (User user){

        ResultSet resSet = null;
        String select = "SELECT " + Constants.LOGIN + "," + Constants.PASSWORD +  " FROM " + Constants.USERS_TABLE +
                " WHERE " + Constants.LOGIN + " =? " + "AND " + Constants.PASSWORD + " =? ";

        try{
            PreparedStatement preparedStmt = connection.prepareStatement(select);
            preparedStmt.setString(1, user.getLogin());
            preparedStmt.setString(2, user.getPassword());

            resSet = preparedStmt.executeQuery();


            System.out.println("Such user exists !");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  resSet;
        }

        public  void  getUserRegistration (User user) {

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


        public  ResultSet getAdminAuthorization (Admin admin){

        ResultSet resSet = null;
        String select = "SELECT " + Constants.ADMIN_LOGIN + "," + Constants.ADMIN_PASSWORD +  " FROM " + Constants.ADMIN_TABLE +
                " WHERE " + Constants.ADMIN_LOGIN + " =? " + "AND " + Constants.ADMIN_PASSWORD + " =? ";

        try{
            PreparedStatement preparedStmt = connection.prepareStatement(select);
            preparedStmt.setString(1, admin.getLogin());
            preparedStmt.setString(2, admin.getPassword());

            resSet = preparedStmt.executeQuery();
            System.out.println("Such admin exists !");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return  resSet;
        }

       /* public List<Project> showAllProjects() throws ClassNotFoundException {

            List<Project> projects = new ArrayList<>();

            ResultSet resSet = null;
            Class.forName("org.postgresql.Driver");
            String query = "SELECT " + Constants.PROJECT_ID + " , " + Constants.PROJECT_NAME + " , " + Constants.CUSTOMER + " , "
                    + Constants.COST + " ," + Constants.DEADLINE + " FROM " + Constants.PROJECTS_TABLE;
            ResultSet rs;
            try {
                rs = statement.executeQuery(query);

                while (rs.next()) {

                    int id = resSet.getInt("id");
                    String name = resSet.getString("name");
                    String customer = resSet.getString("customer");
                    String cost = resSet.getString("cost");
                    String deadline = resSet.getString("deadline");

                    Project project = new Project(id, name, customer, cost, deadline);
                    projects.add(project);
                }

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return projects;
            }


            public void addProjectInDataBase(String name, String customer, String cost, String deadline){

                 try {
                        String query = " insert into "+Constants.PROJECTS_TABLE +" (name, customer,cost,deadline)"
                        + " values ( ?, ?,?,?)";

                    PreparedStatement preparedStmt = connection.prepareStatement(query);
                    preparedStmt.setString (1, name);
                    preparedStmt.setString (2, customer);
                    preparedStmt.setString (3, cost);
                    preparedStmt.setString (4, deadline);


                    preparedStmt.executeUpdate();
                    System.out.println("New project was added ");
                 } catch (SQLException throwables) {
                throwables.printStackTrace();
                }
            }

            public void deleteProjectInDataBase(int id)  {
                System.out.println(id);
                String selectSQL = "DELETE FROM "+Constants.PROJECTS_TABLE +  " WHERE id = ?";
                try {
                connection.prepareStatement(selectSQL);
                PreparedStatement preparedStmt = connection.prepareStatement(selectSQL);
                preparedStmt.setInt(1, id);
                preparedStmt.executeUpdate();

                } catch (SQLException throwables) {
                throwables.printStackTrace();
                }

            }

            public  void redactionProjectInDataBase(int id, String name, String customer, String cost, String deadline )  {

            String query = "UPDATE "+Constants.PROJECTS_TABLE +" SET name  = ?, customer = ? ,cost = ?,deadline = ?   WHERE id = ?";
            PreparedStatement preparedStmt = null;
            try {
            preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString   (1, name);
            preparedStmt.setString(2, customer);
            preparedStmt.setString(3, cost);
            preparedStmt.setString(4, deadline);
            preparedStmt.setInt(5, id);

            preparedStmt.executeUpdate();
            } catch (SQLException throwables) {
            throwables.printStackTrace();
            }

            }*/
    public void addProject(Project project) {
        try (Connection connection = DatabaseConnectionProvider.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(// позволяет вставлять значения
                     "insert into projects( name,customer,cost,deadline) " +
                             "value(?, ?, ?, ?)")) {

            preparedStatement.setString(1, project.getName());
            preparedStatement.setString(2, project.getCustomer());
            preparedStatement.setString(3, project.getCost());
            preparedStatement.setString(4, project.getDeadline());

            preparedStatement.execute();//выполняем запрос

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();

        try (Connection connection = DatabaseConnectionProvider.getConnection();// если в скобках что-то указывается, то потом вызовется метод close()
             Statement statement = connection.createStatement()) { //выполняет запрос

            ResultSet resultSet = statement.executeQuery("select * from projects");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String customer = resultSet.getString("customer");
                String cost = resultSet.getString("cost");
                String deadline = resultSet.getString("deadline");


                Project project = new Project(id, name,customer,cost,deadline);

                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return projects;
    }
    /* public void addProject(Project project) {
            try (Connection connection = DatabaseConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(// позволяет вставлять значения
                         "insert into projects( name,customer,cost,deadline) " +
                                 "value(?, ?, ?, ?)")) {

                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getCustomer());
                preparedStatement.setString(3, project.getCost());
                preparedStatement.setString(4, project.getDeadline());

                preparedStatement.execute();//выполняем запрос

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void updateProject(Project project) {
            try (Connection connection = DatabaseConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "update projects set name = ?, customer = ?, cost = ?, deadline = ? where id = ?")) {

                preparedStatement.setString(1, project.getName());
                preparedStatement.setString(2, project.getCustomer());
                preparedStatement.setString(3, project.getCost());
                preparedStatement.setString(4, project.getDeadline());


                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void deleteProjectByID(int id) {
            try (Connection connection = DatabaseConnectionProvider.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(
                         "delete from projects where id = ?")) {

                preparedStatement.setInt(1, id);

                preparedStatement.execute();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/


}







