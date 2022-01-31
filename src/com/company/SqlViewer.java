package com.company;

import javax.swing.*;
import java.util.Vector;

public class SqlViewer extends JFrame {
    JButton openFileButton = new JButton("Open");
    JTextField fileNameTextField = new JTextField();
    JComboBox<String> tablesComboBox =new JComboBox<>();
    JTextArea queryTextArea = new JTextArea();
    JButton executeButton = new JButton("Execute");



    QueryTableModel qtm = new QueryTableModel();
    JTable table = new JTable(qtm);
    JScrollPane scrollPane = new JScrollPane(table);

    Vector<String> tables = new Vector<>();

    public SqlViewer() {
        super("SQLite Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 600);
        this.setLayout(null);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        initComponents();





    }

    private void executeButton(){
        //Execute Button
        executeButton.setBounds(570,130,120,50);
        executeButton.setName("ExecuteQueryButton");
        add(executeButton);
    }



    private void addComboBox() {
        tablesComboBox.removeAllItems();
        for (String item : tables) {
            tablesComboBox.addItem(item);
        }
    }

    private void addOpenFileButton(){
        //Adatbázis megnyitása gomb
        DataBase db = new DataBase();

        openFileButton.setBounds(585,20,100,30);
        openFileButton.setName("OpenFileButton");
        openFileButton.addActionListener(e -> {
            if(fileNameTextField.getText().equals("")){
                JOptionPane.showMessageDialog(null, "You must add database file name");
            }
            db.open(fileNameTextField.getText());
            tables = db.queryTables();

            addComboBox();
            tables.clear();


        });
        add(openFileButton);

        addJTable();



    }

    private void addJTable(){


        executeButton.addActionListener(e ->{
            String sql = queryTextArea.getText();
            String url = fileNameTextField.getText();

            if(sql.equals("")){
                JOptionPane.showMessageDialog(null, "You must give a query");
            }
            qtm.setQuery(sql, url);

            table.setName("Table");

            scrollPane.setBounds(10,300,650,130);

            scrollPane.setName("Table");
            add(scrollPane);
        });



    }

    private void addTablesComboBox(){
        //Combobox
        tablesComboBox.setBounds(10,80, 680,30);
        tablesComboBox.setName("TablesComboBox");
        tablesComboBox.addActionListener(e -> {
            String query = "SELECT * FROM "+ tablesComboBox.getSelectedItem()+";";
            queryTextArea.setText(query);
        });
        add(tablesComboBox);
    }

    private void addFileNameTextField(){
        //Adatbázis neve textfield
        fileNameTextField.setBounds(10,20, 570,30);
        fileNameTextField.setName("FileNameTextField");
        add(fileNameTextField);
    }

    private void addQueryTextArea(){
        //TextArea
        queryTextArea.setBounds(10,130,550,130);
        queryTextArea.setName("QueryTextArea");
        add(queryTextArea);
    }


    private void initComponents(){
        addOpenFileButton();
        addTablesComboBox();
        addFileNameTextField();
        addQueryTextArea();
        executeButton();
    }

}
