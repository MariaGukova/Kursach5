package com.Server.dataBase;

import com.Server.constants.Constants;

import java.sql.*;

public class Tables {
    private final Connection connection;
    private final Statement statement;

    public Tables(Connection connection, Statement statement) {
        this.connection = connection;
        this.statement = statement;

    }

    public void createTablesInDataBase(){
        addTableUserInDateBase();

    }

    private void addTableUserInDateBase(){
        if(tableExists(Constants.USERS_TABLE)) {
            try {
                String SQL = "CREATE TABLE "+Constants.USERS_TABLE +
                        "( " +
                        " id  SERIAL PRIMARY KEY," +
                        " login VARCHAR (50), " +
                        " name VARCHAR (50), " +
                        " surname VARCHAR (50), " +
                        " password VARCHAR (50), " +
                        ")";
                System.out.println("Tables created !");
                statement.executeUpdate(SQL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private boolean tableExists(String nameTable){

        try{
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, nameTable, null);
            rs.last();
            return rs.getRow() <= 0;
        }catch(SQLException ignored){

        }
        return true;
    }

}
