package org.television.actions;

import org.television.connection.TPConnection;
import org.television.display.TPDisplay;
import org.television.TPMain;

import javax.swing.*;
import java.sql.*;

public class TPInsert extends JPanel {
    private JTextField nameField;
    private JTextField descriptionField;
    private JTextField languageField;
    private JTextField qualityField;
    private JTextField typeField;
    private JTextField priceField;
    private JTextField launchDateField;
    private JButton insertButton;

    JFrame frame = TPMain.frame;

    public TPInsert() {
        setLayout(null);

        JLabel nameLabel = new JLabel("Nume:");
        nameLabel.setBounds(180, 50, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(280, 50, 200, 25);
        add(nameField);

        JLabel descriptionLabel = new JLabel("Descriere:");
        descriptionLabel.setBounds(180, 100, 100, 25);
        add(descriptionLabel);

        descriptionField = new JTextField();
        descriptionField.setBounds(280, 100, 200, 25);
        add(descriptionField);

        JLabel languageLabel = new JLabel("Limbă:");
        languageLabel.setBounds(180, 150, 100, 25);
        add(languageLabel);

        languageField = new JTextField();
        languageField.setBounds(280, 150, 200, 25);
        add(languageField);

        JLabel typeLabel = new JLabel("Tip:");
        typeLabel.setBounds(180, 200, 100, 25);
        add(typeLabel);

        typeField = new JTextField();
        typeField.setBounds(280, 200, 200, 25);
        add(typeField);

        JLabel qualityLabel = new JLabel("Calitate:");
        qualityLabel.setBounds(180, 250, 100, 25);
        add(qualityLabel);

        qualityField = new JTextField();
        qualityField.setBounds(280, 250, 200, 25);
        add(qualityField);

        JLabel priceLabel = new JLabel("Preț:");
        priceLabel.setBounds(180, 300, 100, 25);
        add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(280, 300, 200, 25);
        add(priceField);

        JLabel launchDateLabel = new JLabel("Data lansării:");
        launchDateLabel.setBounds(180, 350, 100, 25);
        add(launchDateLabel);

        launchDateField = new JTextField();
        launchDateField.setBounds(280, 350, 200, 25);
        add(launchDateField);

        insertButton = new JButton("Inserare");
        insertButton.setBounds(230, 420, 200, 25);
        add(insertButton);

        insertButton.addActionListener(e -> {
            if(nameField.getText().isEmpty() || descriptionField.getText().isEmpty() || languageField.getText().isEmpty() || typeField.getText().isEmpty() || qualityField.getText().isEmpty() || priceField.getText().isEmpty() || launchDateField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Toate câmpurile sunt obligatorii");
            } else {
                try (Connection conn = TPConnection.connect()) {
                    String query = "INSERT INTO TVChannels (id, name, description, language, type, quality, price, launch_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement statement = conn.prepareStatement(query);

                    String getId = "SELECT MAX(id) FROM TVChannels";
                    PreparedStatement getIdStatement = conn.prepareStatement(getId);

                    ResultSet resultSet = getIdStatement.executeQuery();
                    int id = resultSet.next() ? resultSet.getInt(1) + 1 : 1;

                    statement.setInt(1, id);
                    statement.setString(2, nameField.getText());
                    statement.setString(3, descriptionField.getText());
                    statement.setString(4, languageField.getText());
                    statement.setString(5, typeField.getText());
                    statement.setString(6, qualityField.getText());
                    statement.setDouble(7, Double.parseDouble(priceField.getText()));
                    statement.setDate(8, Date.valueOf(launchDateField.getText()));
                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Datele au fost adaugate în baza de date");
                    frame.getContentPane().removeAll();
                    frame.add(new TPDisplay());
                    frame.revalidate();
                    frame.repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
