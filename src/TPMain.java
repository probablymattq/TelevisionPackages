import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TPMain {
    static JFrame frame;
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem mainItem, insertItem, removeItem, searchItem;

    public TPMain() {
        frame = new JFrame("Television Packages");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(730, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new TPDisplay());
        frame.setResizable(false);

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        menu = new JMenu("Meniu");
        menuBar.add(menu);

        mainItem = new JMenuItem("Principal");
        insertItem = new JMenuItem("Inserare");
        removeItem = new JMenuItem("Ștergere");
        searchItem = new JMenuItem("Căutare");

        menu.add(mainItem);
        menu.add(insertItem);
        menu.add(removeItem);
        menu.add(searchItem);

        ActionListener listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                switch (command) {
                    case "Principal" -> {
                        frame.getContentPane().removeAll();
                        frame.add(new TPDisplay());
                    }
                    case "Inserare" -> {
                        frame.getContentPane().removeAll();
                        frame.add(new TPInsert());
                    }
                    case "Ștergere" -> {
                        frame.getContentPane().removeAll();
                    }
                    case "Căutare" -> {
                        frame.getContentPane().removeAll();
                        frame.add(new TPSearch());
                    }
                }
                frame.revalidate();
                frame.repaint();
            }
        };

        mainItem.addActionListener(listener);
        insertItem.addActionListener(listener);
        removeItem.addActionListener(listener);
        searchItem.addActionListener(listener);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TPMain();
    }
}

