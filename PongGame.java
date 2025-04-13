/**
 * PongGame.java
 * 
 * Este programa implementa una versión básica del clásico juego Pong usando Java Swing.
 * Incluye dos paletas controladas por el teclado y una pelota que rebota entre ellas.
 * 
 * Controles:
 * - Jugador 1 (izquierda): W (arriba) y S (abajo)
 * - Jugador 2 (derecha): Flechas ↑ y ↓
 * 
 * Autor: [Tu Nombre]
 * Fecha: [Fecha Actual]
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PongGame extends JPanel implements KeyListener, ActionListener {

    // Posiciones de las paletas y la pelota
    int paddle1Y = 100, paddle2Y = 100;
    int ballX = 250, ballY = 150;

    // Velocidad de la pelota en X e Y
    int ballXSpeed = 2, ballYSpeed = 2;

    // Temporizador que actualiza el juego cada 5 ms
    Timer timer = new Timer(5, this);

    // Variables de control de teclas presionadas
    boolean wPressed = false, sPressed = false;
    boolean upPressed = false, downPressed = false;

    /**
     * Constructor principal: crea la ventana del juego y configura el panel.
     */
    public PongGame() {
        JFrame frame = new JFrame("Pong Game");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        frame.addKeyListener(this); // Detecta eventos de teclado
        timer.start(); // Inicia el temporizador (bucle del juego)
    }

    /**
     * Dibuja los elementos del juego: paletas y pelota.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(20, paddle1Y, 10, 60);   // Paleta izquierda
        g.fillRect(470, paddle2Y, 10, 60);  // Paleta derecha
        g.fillOval(ballX, ballY, 10, 10);   // Pelota
    }

    /**
     * Método llamado por el temporizador cada 5 ms. Actualiza el estado del juego.
     */
    public void actionPerformed(ActionEvent e) {
        // Movimiento de paleta izquierda
        if (wPressed && paddle1Y > 0) paddle1Y -= 3;
        if (sPressed && paddle1Y < getHeight() - 60) paddle1Y += 3;

        // Movimiento de paleta derecha
        if (upPressed && paddle2Y > 0) paddle2Y -= 3;
        if (downPressed && paddle2Y < getHeight() - 60) paddle2Y += 3;

        // Movimiento de la pelota
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Rebote en el borde superior/inferior
        if (ballY <= 0 || ballY >= getHeight() - 10) {
            ballYSpeed *= -1;
        }

        // Rebote con las paletas
        if ((ballX <= 30 && ballY + 10 >= paddle1Y && ballY <= paddle1Y + 60) ||
            (ballX >= 460 && ballY + 10 >= paddle2Y && ballY <= paddle2Y + 60)) {
            ballXSpeed *= -1;
        }

        // Reiniciar la pelota si se sale por los lados
        if (ballX < 0 || ballX > getWidth()) {
            ballX = 250;
            ballY = 150;
            ballXSpeed = -ballXSpeed;
        }

        // Redibuja el juego
        repaint();
    }

    /**
     * Detecta cuando una tecla es presionada.
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = true;
            case KeyEvent.VK_S -> sPressed = true;
            case KeyEvent.VK_UP -> upPressed = true;
            case KeyEvent.VK_DOWN -> downPressed = true;
        }
    }

    /**
     * Detecta cuando una tecla es liberada.
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> wPressed = false;
            case KeyEvent.VK_S -> sPressed = false;
            case KeyEvent.VK_UP -> upPressed = false;
            case KeyEvent.VK_DOWN -> downPressed = false;
        }
    }

    /**
     * Método requerido por la interfaz KeyListener (no se usa en este caso).
     */
    public void keyTyped(KeyEvent e) {}

    /**
     * Método principal: inicia el juego creando una instancia de PongGame.
     */
    public static void main(String[] args) {
        new PongGame();
    }
}
