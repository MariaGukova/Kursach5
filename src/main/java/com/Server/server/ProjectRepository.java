package com.Server.server;


import com.example.it.Project;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectRepository {
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
        }
    }


