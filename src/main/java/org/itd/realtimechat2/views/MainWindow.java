package org.itd.realtimechat2.views;

import org.itd.realtimechat2.views.ejercicios.CarAnimation;

import javax.swing.*;
import java.awt.*;

public class MainWindow  extends JFrame {
    public MainWindow() {
        super("Proyecto");
        setSize(new Dimension(1280, 800));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Ejercicio 1", null);
        tabbedPane.addTab("Ejercicio 2", null);
        tabbedPane.addTab("Carrito", new CarAnimation());
        tabbedPane.addTab("Servidor concurrente", null);

        add(tabbedPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
