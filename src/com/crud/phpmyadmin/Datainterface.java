package com.crud.phpmyadmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Datainterface {
    public Datainterface() {
        addbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ProductJenis, ProductNama, ProductHarga;
                ProductJenis = jTextField_Jenis_Produk.getText();
                ProductNama = jTextField_Nama_Produk.getText();
                ProductHarga = jTextField_Harga.getText();

                try {
                    preparedStatement = Connector.connectDB().prepareStatement("INSERT INTO products (product_id, product_name, product_price) values (?, ?, ?)");
                    preparedStatement.setString(1, ProductJenis);
                    preparedStatement.setString(2, ProductNama);
                    preparedStatement.setString(3, ProductHarga);
                    showData();
                    JOptionPane.showMessageDialog(null,"Data Sucsessfully inserted");
                } catch (SQLException err) {
                    Logger.getLogger(Datainterface.class.getName()).log(Level.SEVERE, null, err);
                }
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public JPanel getMainPanel(){
        showData();
        return MainPanel;
    }
    private DefaultTableModel tableModel;
    private ResultSet resultSet;
    private PreparedStatement preparedStatement;
    private void showData(){
        try{
            Object[] columnTitle = {"Products ID", "Name", "Price"};
            tableModel = new DefaultTableModel(null, columnTitle);
            table1.setModel(tableModel);

            //retrieve connection from DB
            Connection connection = Connector.connectDB();
            Statement statement = connection.createStatement();
            tableModel.getDataVector().removeAllElements();

            //initiate result with SQL Query
            resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()){
                Object[] data = {
                        resultSet.getString("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_price")
                };
                tableModel.addRow(data);
            }
        } catch (SQLException err){
            throw new RuntimeException(err);

        }
    }
    private JPanel MainPanel;
    private JTextField jTextField_Jenis_Produk;
    private JTextField jTextField_Nama_Produk;
    private JTextField jTextField_Harga;
    private JTable table1;
    private JButton addbutton;
    private JButton updateButton;
    private JButton deleteButton;
    private JPanel JFirstPanel;
    private JPanel JSecondPanel;
    private JPanel JThridPanel;
    private JLabel jLabelJenis_Produk;
    private JLabel jLabelNama_Produk;
    private JLabel jLabelHarga;
}
