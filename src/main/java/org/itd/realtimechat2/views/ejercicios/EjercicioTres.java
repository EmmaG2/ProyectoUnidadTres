package org.itd.realtimechat2.views.ejercicios;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EjercicioTres extends JPanel {

    private JTextArea textArea;
    private JList<String> numberList;
    private JLabel vocalLabel;

    public EjercicioTres() {
        setLayout(new BorderLayout());

        // Área de texto para mostrar el contenido del archivo
        textArea = new JTextArea(5, 20);
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.NORTH);

        // Lista para mostrar los números del 1 al 10
        DefaultListModel<String> listModel = new DefaultListModel<>();
        numberList = new JList<>(listModel);
        add(new JScrollPane(numberList), BorderLayout.CENTER);

        // Etiqueta para mostrar las vocales
        vocalLabel = new JLabel("A");
        vocalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(vocalLabel, BorderLayout.SOUTH);

        // Iniciar hilos
        new Thread(new Hilo_UNO()).start();
        new Thread(new Hilo_DOS()).start();
        new Thread(new Hilo_TRES(listModel)).start();

        // Iniciar ciclo de vocales en el hilo principal
        startVocalCycle();
    }

    private void startVocalCycle() {
        String[] vocales = {"A", "E", "I", "O", "U"};
        new Thread(() -> {
            int i = 0;
            while (true) {
                vocalLabel.setText(vocales[i % vocales.length]);
                i++;
                try {
                    Thread.sleep(500); // Pausa de medio segundo entre vocales
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    // Hilo para leer el archivo
    private class Hilo_UNO implements Runnable {
        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(new FileReader("archivo_texto.txt"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                textArea.setText("Error al leer el archivo.");
            }
        }
    }

    // Hilo para crear un nuevo frame
    private static class Hilo_DOS implements Runnable {
        @Override
        public void run() {
            JFrame frame = new JFrame("Nuevo Frame");
            frame.setSize(300, 200);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);
        }
    }

    // Hilo para mostrar los números del 1 al 10 en la lista
    private static class Hilo_TRES implements Runnable {
        private DefaultListModel<String> model;

        public Hilo_TRES(DefaultListModel<String> model) {
            this.model = model;
        }

        @Override
        public void run() {
            for (int i = 1; i <= 10; i++) {
                model.addElement("Número: " + i);
                try {
                    Thread.sleep(300); // Pausa de 300ms entre cada número
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
