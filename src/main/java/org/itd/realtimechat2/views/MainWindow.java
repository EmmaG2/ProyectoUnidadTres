package org.itd.realtimechat2.views;

import org.itd.realtimechat2.views.ejercicios.CarAnimation;
import org.itd.realtimechat2.views.ejercicios.EjercicioTres;
import org.itd.realtimechat2.views.ejercicios.EjercicioUno;

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

        tabbedPane.addTab("Ejercicio 1", new EjercicioTres());
        tabbedPane.addTab("Ejercicio 2", new EjercicioUno()); // aquí va la instancia
        tabbedPane.addTab("Ejercicio 3", new CarAnimation());

        add(tabbedPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainWindow::new);
    }
}
