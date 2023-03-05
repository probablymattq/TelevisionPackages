package org.television.actions;

import org.television.TPMain;
import org.television.connection.TPConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Objects;

public class TPSearch extends JPanel {
    JComboBox<String> comboBox;
    JTextField searchField;
    JTable table;
    JScrollPane scrollPane;

    DefaultTableModel tableModel =
            new DefaultTableModel(
                    new Object[]{"Nume", "Descriere", "Language", "Tip", "Calitate", "Preț", "Data lansării"}, 0);

    public TPSearch() {
        TPMain.setTitle("Television (Căutare)");
        String[] elements = {"name", "description", "language", "type", "quality", "price", "launch_date"};
        comboBox = new JComboBox<>(elements);
        comboBox.setBounds(70, 10, 190, 25);
        add(comboBox);

        setLayout(null);
        searchField = new JTextField();
        searchField.setBounds(270, 10, 200, 25);
        add(searchField);

        JButton searchButton = new JButton("Caută");
        searchButton.setBounds(480, 10, 100, 25);
        add(searchButton);

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 65, 714, 500);
        add(scrollPane);
        addTableData();

        searchButton.addActionListener(e -> {
            String search = searchField.getText();
            if (search == null || search.equals("")) {
                addTableData();
            } else {
                try (Connection conn = TPConnection.connect()) {
                    String query;

                    if (Objects.equals(comboBox.getSelectedItem(), "launch_date")) {
                        query = "SELECT * FROM TVChannels WHERE " + comboBox.getSelectedItem() + " >= '" + search + "'";
                    } else if (Objects.equals(comboBox.getSelectedItem(), "language")) {
                        query = "SELECT * FROM TVChannels WHERE " + comboBox.getSelectedItem() + " LIKE '%" + search + "%'";
                    } else {
                        query = "SELECT * FROM TVChannels WHERE " + comboBox.getSelectedItem() + " LIKE '" + search + "%'";
                    }

                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet result = statement.executeQuery();
                    tableModel.setRowCount(0);
                    while (result.next()) {
                        String channelName = result.getString("name");
                        String description = result.getString("description");
                        String language = result.getString("language");
                        String type = result.getString("type");
                        String quality = result.getString("quality");
                        double price = result.getDouble("price");
                        Date launchDate = result.getDate("launch_date");
                        tableModel.addRow(new Object[]{channelName, description, language, type, quality, price, launchDate});
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void addTableData() {
        tableModel.setRowCount(0);
        try (Connection conn = TPConnection.connect()) {
            String query = "SELECT * FROM TVChannels";

            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String channelName = result.getString("name");
                String description = result.getString("description");
                String language = result.getString("language");
                String type = result.getString("type");
                String quality = result.getString("quality");
                double price = result.getDouble("price");
                Date launchDate = result.getDate("launch_date");
                tableModel.addRow(new Object[]{channelName, description, language, type, quality, price, launchDate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
