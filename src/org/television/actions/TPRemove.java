package org.television.actions;

import org.television.connection.TPConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TPRemove extends JPanel {
    JScrollPane scrollPane;

    public TPRemove() {
        JPanel nestedPanel = new JPanel(null);
        nestedPanel.setPreferredSize(new Dimension(getWidth(), 20));
        add(nestedPanel, BorderLayout.NORTH);

        String[] elements = {"name", "description", "language", "type", "quality", "price", "launch_date"};
        JComboBox<String> comboBox = new JComboBox<>(elements);
        comboBox.setBounds(70, 10, 190, 25);
        nestedPanel.add(comboBox);

        JTextField searchField = new JTextField();
        searchField.setBounds(270, 10, 200, 25);
        nestedPanel.add(searchField);

        JButton removeButton = new JButton("Șterge");
        removeButton.setBounds(480, 10, 100, 25);
        nestedPanel.add(removeButton);

        removeButton.addActionListener(e -> {
            if (scrollPane != null) {
                remove(scrollPane);
            }
            String search = searchField.getText();
            if (search == null || search.equals("")) {
                JOptionPane.showMessageDialog(null, "Introduceți un parametru de căutare");
            } else {
                try (Connection conn = TPConnection.connect()) {
                    String query = "DELETE FROM TVChannels WHERE " + comboBox.getSelectedItem() + " = '" + search + "'";
                    JOptionPane.showMessageDialog(null, "Ștergerea a fost efectuată cu succes");
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.executeUpdate();
                    updateTable();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        updateTable();
    }

    private void updateTable() {
        try (Connection conn = TPConnection.connect()) {
            String query = "SELECT * FROM TVChannels";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"Nume", "Descriere", "Language", "Tip", "Calitate", "Preț", "Data lansării"}, 0);
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
            JTable table = new JTable(tableModel);
            scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
            revalidate();
            repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}