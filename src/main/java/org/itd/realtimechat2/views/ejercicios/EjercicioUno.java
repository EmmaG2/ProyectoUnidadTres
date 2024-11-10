package org.itd.realtimechat2.views.ejercicios;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Semaphore;

public class EjercicioUno extends JPanel {
    private JTextArea textArea;

    public EjercicioUno() {
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton startButton = new JButton("Iniciar Ejecución");
        startButton.addActionListener(e -> iniciarSubprocesos());
        add(startButton, BorderLayout.SOUTH);
    }

    private void iniciarSubprocesos() {
        // Semáforo para sincronizar el acceso a textArea
        Semaphore semaphore = new Semaphore(1);

        // Crear e iniciar los hilos
        Thread subProceso1 = new Thread(new SubProceso("SubProceso1", "1", "10", semaphore));
        Thread subProceso2 = new Thread(new SubProceso("SubProceso2", "1", "10", semaphore));
        Thread subProceso3 = new Thread(new SubProceso("SubProceso3", "a", "j", semaphore));
        Thread subProceso4 = new Thread(new SubProceso("SubProceso4", "a", "j", semaphore));

        subProceso1.start();
        subProceso2.start();
        subProceso3.start();
        subProceso4.start();
    }

    // Clase interna para cada SubProceso
    private class SubProceso implements Runnable {
        private String nombre;
        private String inicio;
        private String fin;
        private Semaphore semaphore;

        public SubProceso(String nombre, String inicio, String fin, Semaphore semaphore) {
            this.nombre = nombre;
            this.inicio = inicio;
            this.fin = fin;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                // Adquirir semáforo
                semaphore.acquire();
                mostrarTexto("Inició: " + nombre);

                // Ejecutar el ciclo según el tipo (números o letras)
                if (Character.isDigit(inicio.charAt(0))) {
                    for (int i = Integer.parseInt(inicio); i <= Integer.parseInt(fin); i++) {
                        mostrarTexto("Hilo: " + i);
                    }
                } else {
                    for (char c = inicio.charAt(0); c <= fin.charAt(0); c++) {
                        mostrarTexto("Hilo: " + c);
                    }
                }

                mostrarTexto("Terminó: " + nombre);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // Liberar semáforo
                semaphore.release();
            }
        }

        // Método para agregar texto al JTextArea
        private void mostrarTexto(String texto) {
            SwingUtilities.invokeLater(() -> textArea.append(texto + "\n"));
        }
    }
}

