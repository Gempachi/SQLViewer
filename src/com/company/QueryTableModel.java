package com.company;

import javax.swing.table.AbstractTableModel;
import java.sql.*;
import java.util.Vector;
public class QueryTableModel extends AbstractTableModel {

    Vector cache;

    int colCount;

    String[] headers;

    Connection conn;



    public QueryTableModel() {
        cache = new Vector();
    }

    @Override
    public String getColumnName(int i) {
        return headers[i];
    }

    @Override
    public int getRowCount() {
        return cache.size();
    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((String[]) cache.elementAt(rowIndex))[columnIndex];
    }

    public void setQuery(String q, String dbName) {

        String url ="jdbc:sqlite:"+dbName;
        Statement statement = null;

        cache = new Vector();
        try {
            conn = DriverManager.getConnection(url);

            statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(q);

            ResultSetMetaData meta = rs.getMetaData();
            colCount = meta.getColumnCount();

            headers = new String[colCount];
            for (int h = 1; h <= colCount; h++) {
                headers[h - 1] = meta.getColumnName(h);
            }

            while (rs.next()) {
                String[] record = new String[colCount];
                for (int i = 0; i < colCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                cache.addElement(record);
            }
            fireTableChanged(null);
        } catch (SQLException e) {
            cache = new Vector();
            e.printStackTrace();
        }
    }


}
