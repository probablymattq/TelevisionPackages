package org.television;

import org.television.actions.TPEdit;
import org.television.actions.TPInsert;
import org.television.actions.TPRemove;
import org.television.actions.TPSearch;
import org.television.display.TPDisplay;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TPMain {
    public static JFrame frame;
    JMenuBar menuBar;
    JButton main, insert, remove, search, edit;

    public TPMain() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(730, 600);
        frame.setLocationRelativeTo(null);
        frame.add(new TPDisplay());
        frame.setResizable(false);

        menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        main = new JButton("Principal");
        insert = new JButton("Inserare");
        remove = new JButton("Ștergere");
        search = new JButton("Căutare");
        edit = new JButton("Editare");

        configureButton(main);
        configureButton(insert);
        configureButton(remove);
        configureButton(search);
        configureButton(edit);

        menuBar.add(main);
        menuBar.add(insert);
        menuBar.add(remove);
        menuBar.add(search);
        menuBar.add(edit);

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
                        frame.add(new TPRemove());
                    }
                    case "Căutare" -> {
                        frame.getContentPane().removeAll();
                        frame.add(new TPSearch());
                    }
                    case "Editare" -> {
                        frame.getContentPane().removeAll();
                        frame.add(new TPEdit());
                    }
                }
                frame.revalidate();
                frame.repaint();
            }
        };

        main.addActionListener(listener);
        insert.addActionListener(listener);
        remove.addActionListener(listener);
        search.addActionListener(listener);
        edit.addActionListener(listener);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new TPMain();
    }

    public static void setTitle(String title) {
        frame.setTitle(title);
    }

    private void configureButton(Object o) {
        JButton button = (JButton) o;
        button.setOpaque(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
}

