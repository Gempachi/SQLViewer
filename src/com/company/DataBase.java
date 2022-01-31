package com.company;

import java.sql.*;
import java.util.Vector;

public class DataBase {
    private Connection conn;
   
    public void open(String dbname) {
        String url = "jdbc:sqlite:"+dbname;
        try{
            conn = DriverManager.getConnection(url);
        } catch(SQLException e){
            System.out.println(e);
        }
    }
    public void close(){
        try{
            if(conn != null){
                conn.close();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
    private Vector<String> tableList = new Vector<>();

    public Vector<String> queryTables(){
        Statement statement = null;
        ResultSet results = null;

        try{
            DatabaseMetaData metaData = conn.getMetaData();
            String[] types = {"TABLE"};

            ResultSet tables = metaData.getTables(null, null, "%", types);

            while (tables.next()) {
                //System.out.println(tables.getString("TABLE_NAME"));
                tableList.add(tables.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(results != null){
                    results.close();
                }
            } catch (SQLException e){
                System.out.println(e.getMessage());
            }
            try{
                if(statement != null){
                    statement.close();
                }
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return tableList;
    }
}
