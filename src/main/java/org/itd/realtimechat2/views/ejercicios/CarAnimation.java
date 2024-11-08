package org.itd.realtimechat2.views.ejercicios;

import javax.swing.*;
import java.awt.*;

public class CarAnimation extends JPanel {
    private JPanel panel;
    private JLabel carLabel;
    private JButton startButton, stopButton, resumeButton, resetButton;
    private Thread carThread;
    private boolean running = false;
    private boolean suspended = false;
    private int carPositionX = 0;

    public CarAnimation() {
        setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Iniciar Hilo 1");
        stopButton = new JButton("Suspender Hilo 1");
        resumeButton = new JButton("Re-Iniciar Hilo 1");
        resetButton = new JButton("Resetear Hilo 1");

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(resumeButton);
        buttonPanel.add(resetButton);

        add(buttonPanel, BorderLayout.NORTH);

        // Panel para el carrito (usamos null layout para mover el JLabel libremente)
        panel = new JPanel(null);
        panel.setBackground(Color.RED);
        add(panel, BorderLayout.CENTER);

        // Crear el JLabel con la imagen del carrito
        ImageIcon carIcon = new ImageIcon("/Users/emmanuelgranados/Desktop/Programacion/java/RealTimeChat2/car.jpg"); // Cambia "car.jpg" a la ruta correcta de tu imagen
        carLabel = new JLabel();
        carLabel.setIcon(carIcon);
        carLabel.setBounds(carPositionX, 100, carIcon.getIconWidth(), carIcon.getIconHeight());
        panel.add(carLabel);

        startButton.addActionListener(e -> startCar());
        stopButton.addActionListener(e -> suspendCar());
        resumeButton.addActionListener(e -> resumeCar());
        resetButton.addActionListener(e -> resetCar());
        setVisible(true);
    }

    private void startCar() {
        if (carThread == null || !running) {
            carThread = new Thread(() -> {
                running = true;
                while (running) {
                    if (!suspended) {
                        carPositionX += 5;
                        if (carPositionX > panel.getWidth()) {
                            carPositionX = 0;
                        }
                        carLabel.setLocation(carPositionX, 100);
                    }
                    try {
                        Thread.sleep(50); // Velocidad del movimiento
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            carThread.start();
        } else {
            JOptionPane.showConfirmDialog(null, "El hilo ya fue iniciado previamente");
        }
    }

    private void suspendCar() {
        suspended = true;
    }

    private void resumeCar() {
        suspended = false;
    }

    private void resetCar() {
        carPositionX = 0;
        carLabel.setLocation(carPositionX, 100);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CarAnimation::new);
    }
}
