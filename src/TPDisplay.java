import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class TPDisplay extends JPanel {
    public TPDisplay() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        try (Connection conn = TPConnection.connect()) {
            String query = "SELECT * FROM TVChannels";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet result = statement.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel(
                    new Object[]{"ID", "Nume", "Descriere", "Tip", "Preț", "Data lansării"}, 0);
            while (result.next()) {
                int id = result.getInt("id");
                String channelName = result.getString("name");
                String description = result.getString("description");
                String type = result.getString("type");
                double price = result.getDouble("price");
                Date launchDate = result.getDate("launch_date");
                tableModel.addRow(new Object[]{id, channelName, description, type, price, launchDate});
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
