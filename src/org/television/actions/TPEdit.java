package org.television.actions;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import org.television.TPMain;
import org.television.connection.TPConnection;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.*;

public class TPEdit extends JPanel {
    JScrollPane scrollPane;
    DefaultTableModel tableModel =
            new DefaultTableModel(
                    new Object[]{"Nume", "Descriere", "Language", "Tip", "Calitate", "Preț", "Data lansării"}, 0);

    public TPEdit() {
        TPMain.setTitle("Television (Editare)");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        if (scrollPane != null) remove(scrollPane);
        updateTable();


        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                String columnName = model.getColumnName(column);
                String firstRaw = (String) model.getValueAt(row, 0);
                Object data = model.getValueAt(row, column);

                if(validation(data) == null) {
                    JOptionPane.showMessageDialog(null, "Nu ați introdus nimic!");
                    System.out.println(data);
                    return;
                }

                try (Connection conn = TPConnection.connect()) {
                    String query = "UPDATE TVChannels SET " + getDatabaseColumn(columnName) + " = '" + data + "' WHERE name = '" + firstRaw + "'";
                    PreparedStatement statement = conn.prepareStatement(query);
                    statement.executeUpdate();
                } catch (SQLServerException ex) {
                    JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
                } catch (NumberFormatException | SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
                }
                }
            }
        });
    }

    private void updateTable() {
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
            JTable table = new JTable(tableModel);
            scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
            revalidate();
            repaint();
        } catch (SQLServerException ex) {
            JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
        } catch (NumberFormatException | SQLException ex) {
            JOptionPane.showMessageDialog(null, "Eroare: " + ex.getMessage());
        }
    }

    private Object validation(Object data) {
        if (data == null || data.equals("")) {
            return null;
        } else {
            return data;
        }
    }

    private String getDatabaseColumn(String name) {
        return switch (name) {
            case "Nume" -> "name";
            case "Descriere" -> "description";
            case "Language" -> "language";
            case "Tip" -> "type";
            case "Calitate" -> "quality";
            case "Preț" -> "price";
            case "Data lansării" -> "launch_date";
            default -> null;
        };
    }
}