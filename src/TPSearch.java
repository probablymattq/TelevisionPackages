import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.Objects;

public class TPSearch extends JPanel {
    JScrollPane scrollPane;

    public TPSearch() {

        String[] elements = {"id", "name", "description", "language", "type", "quality", "price", "launch_date"};
        JComboBox<String> comboBox = new JComboBox<>(elements);
        comboBox.setBounds(70, 10, 190, 25);
        add(comboBox);

        setLayout(null);
        JTextField searchField = new JTextField();
        searchField.setBounds(270, 10, 200, 25);
        add(searchField);

        JButton searchButton = new JButton("Caută");
        searchButton.setBounds(480, 10, 100, 25);
        add(searchButton);

        searchButton.addActionListener(e -> {
            if (scrollPane != null) {
                remove(scrollPane);
            }
            String search = searchField.getText();
            if (search == null || search.equals("")) {
                JOptionPane.showMessageDialog(null, "Intorduceți un parametru de căutare");
            } else {
                try (Connection conn = TPConnection.connect()) {
                    String query;

                    if(Objects.equals(comboBox.getSelectedItem(), "launch_date")) {
                        query = "SELECT * FROM TVChannels WHERE " + comboBox.getSelectedItem() + " >= '" + search + "'";
                    } else {
                        query = "SELECT * FROM TVChannels WHERE " + comboBox.getSelectedItem() + " LIKE '" + search + "%'";
                    }

                    System.out.println(query);
                    PreparedStatement statement = conn.prepareStatement(query);
                    ResultSet result = statement.executeQuery();
                    DefaultTableModel tableModel = new DefaultTableModel(
                            new Object[]{"ID", "Nume", "Descriere", "Language", "Tip", "Calitate", "Preț", "Data lansării"}, 0);
                    while (result.next()) {
                        int id = result.getInt("id");
                        String channelName = result.getString("name");
                        String description = result.getString("description");
                        String language = result.getString("language");
                        String type = result.getString("type");
                        String quality = result.getString("quality");
                        double price = result.getDouble("price");
                        Date launchDate = result.getDate("launch_date");
                        tableModel.addRow(new Object[]{id, channelName, description, language, type, quality, price, launchDate});
                    }
                    JTable table = new JTable(tableModel);
                    scrollPane = new JScrollPane(table);
                    scrollPane.setBounds(0, 50, getWidth(), getHeight());
                    add(scrollPane);
                    revalidate();
                    repaint();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
