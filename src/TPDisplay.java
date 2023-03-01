import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TPDisplay extends JPanel {
    public TPDisplay() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
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
            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
            revalidate();
            repaint();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
