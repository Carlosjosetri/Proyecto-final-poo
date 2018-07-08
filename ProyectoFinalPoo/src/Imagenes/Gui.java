/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Imagenes;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import GameObjects.Monitos;
import GameObjects.Personaje;
import GameObjects.ClickListener;
import GameObjects.Escenario;
import GameObjects.Jefe;
import GameObjects.Moneda;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class Gui extends JFrame {

    private JLabel[] labels;
    public ArrayList<Monitos> monos;
    public ArrayList<Moneda> monedas;
    public ArrayList<Escenario> escenario;
    public Personaje Character = new Personaje(this);
    public Jefe jefe;
    public boolean jefevivo = false;
    private Random random;
    public int cont = 1;
    public int cont2 = 1;
    public int conttiros = 24;
    public int contexplo = 46;
    public int y;
    public int vida = 8;
    public int moneditas = 0;
    public int Damage = 1;
    public int vidarelativa = 100;
    public boolean rellenar = false;
    public Container container = getContentPane();

    public Gui() {
        super();                    // usamos el contructor de la clase padre JFrame
        configurarVentana();        // configuramos la ventana
        inicializarComponentes();

    }

    private void configurarVentana() {
        this.setTitle("EnWo");

        this.setSize(1000, 600);                                 // colocamos tamanio a la ventana (ancho, alto)
        this.setLocationRelativeTo(null);
        this.setResizable(false);// centramos la ventana en la pantalla
        this.setLayout(null);

        labels = new JLabel[70];

        labels[24] = new JLabel();
        labels[24].setIcon(new ImageIcon(getClass().getResource("explo" + ".gif")));
        container.add(labels[24]);

        labels[47] = new JLabel();
        labels[47].setIcon(new ImageIcon(getClass().getResource("gorilla" + ".gif")));

        container.add(labels[47]);

        labels[14] = new JLabel();

        labels[14].setIcon(new ImageIcon(getClass().getResource("megar" + ".gif")));
        labels[14].setBounds(10, 400, 200, 200);
        container.add(labels[14]);

        labels[0] = new JLabel();

        labels[0].setIcon(new ImageIcon(getClass().getResource("mega" + ".gif")));
        labels[0].setBounds(10, 400, 200, 200);
        container.add(labels[0]);
        Character.setlabel(labels[0]);
        Character.setLabel2(labels[14]);
        for (int i = 1; i < 10; i++) {
            labels[i] = new JLabel();
            labels[i].setIcon(new ImageIcon(getClass().getResource("mon" + ".gif")));

            container.add(labels[i]);
        }
        for (int i = 15; i < 24; i++) {
            labels[i] = new JLabel();
            labels[i].setIcon(new ImageIcon(getClass().getResource("vidap" + ".gif")));

            container.add(labels[i]);
        }
        for (int i = 25; i < 36; i++) {
            labels[i] = new JLabel();
            labels[i].setIcon(new ImageIcon(getClass().getResource("shoot" + ".png")));
            labels[i].setBounds(100, 2000, 400, 400);
            container.add(labels[i]);
        }
        for (int i = 36; i < 36 + vida; i++) {
            labels[i] = new JLabel();
            labels[i].setIcon(new ImageIcon(getClass().getResource("cora" + ".gif")));
            labels[i].setBounds(10 + (i - 36) * 20, -150, 400, 400);
            container.add(labels[i]);
        }
        for (int i = 48; i < 58; i++) {
            labels[i] = new JLabel();
            labels[i].setIcon(new ImageIcon(getClass().getResource("Coin" + ".gif")));

            container.add(labels[i]);
        }
        for (int i = 67; i < 70; i++) {
            labels[i] = new JLabel();
            labels[i].setIcon(new ImageIcon(getClass().getResource("jungle" + ".jpg")));
            labels[i].setBounds(0, 0, 3000, 600);
            container.add(labels[i]);
        }

        // no usamos ningun layout, solo asi podremos dar posiciones a los componentes
        // hacemos que la ventana no sea redimiensionable
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // hacemos que cuando se cierre la ventana termina todo proceso
    }

    private void inicializarComponentes() {

        random = new Random();
        monos = new ArrayList<Monitos>();
        monedas = new ArrayList<Moneda>();
        escenario = new ArrayList<Escenario>();
        labels[24].setBounds(1010, -70, 400, 400);
        labels[47].setBounds(1010, -70, 400, 400);
        jefe = new Jefe(this, labels[47], 960, labels[24]);

    }

    public void move() {
        if (System.nanoTime() - Monitos.lastDuckTime >= Monitos.timeBetweenDucks * 4) {

            // Here we create new duck and add it to the array list.
            y = random.nextInt(600);
            labels[cont].setBounds(3432, y, 100, 100);
            labels[cont + 14].setBounds(2345, y, 200, 200);
            monos.add(new Monitos(this, y, labels[cont], labels[cont + 14]));
            y = random.nextInt(500);
            labels[cont2 + 47].setBounds(2345, y, 200, 200);
            labels[cont2 + 47].setVisible(true);
            monedas.add(new Moneda(this, y, labels[cont2 + 47]));
            System.out.println(cont2 + 47);
            cont += 1;
            cont2 += 1;

            Monitos.lastDuckTime = System.nanoTime();
        }

        if (cont == 7 && jefevivo == false) {
            jefevivo = true;

        }
        if (cont == 9) {

            cont = 1;
        }
        if (cont2 == 10) {

            cont2 = 1;
        }
        if (jefevivo == true) {
            jefe.move();
        }

        if (rellenar == false) {
            escenario.add(new Escenario(this, 0, labels[67]));
            escenario.add(new Escenario(this, 600, labels[68]));
            escenario.add(new Escenario(this, 1200, labels[69]));
            rellenar = true;
        }
        for (int i = 0; i < escenario.size(); i++) {
            escenario.get(i).move();
        }
        // Update all of the ducks.
        for (int i = 0; i < monos.size(); i++) {
            // Move the duck.
            monos.get(i).move();

            // Checks if the duck leaves the screen and remove it if it does.
            if (monos.get(i).x < 0) {
                monos.get(i).label.setLocation(i, 8000);
                monos.get(i).golpe.setLocation(i, 8000);
                monos.remove(i);
            }

        }
        for (int i = 0; i < monedas.size(); i++) {
            // Move the duck.
            monedas.get(i).move();

            // Checks if the duck leaves the screen and remove it if it does.
            if (monedas.get(i).x < 0) {
                monedas.get(i).label.setLocation(i, 8000);

                monedas.remove(i);
            }
            if (monedas.get(i).label==null) {
             

                monedas.remove(i);
            }
            

        }

        Character.move();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        if (jefevivo) {
            if (jefe.vida > 700) {
                g2d.fillRect(300, 30, 700, 15);
                g2d.fillRect(300, 50, jefe.vida - 700, 15);
            } else {
                g2d.fillRect(300, 30, jefe.vida, 15);
            }

        }

        /// POR SI SE QUIERE REVISAR HIT BOX DESCOMENTAR ESTE FRAGMENTO
//        for (int i = 0; i < monos.size(); i++) {
//            monos.get(i).paint(g2d);
////        }
//        for (int i = 0; i < monedas.size(); i++) {
//            monedas.get(i).paint(g2d);
//        }
//        Character.paint(g2d);
//        if (jefevivo == true) {
//            jefe.paint(g2d);
//        }
//        for (int i = 0; i < Character.getDisparos().size(); i++) {
//            Character.getDisparos().get(i).paint(g2d);
//        }
    }

    public JLabel CrearDisparos() {
        conttiros += 1;
        labels[conttiros].setBounds(-234, 4000, 200, 200);
        if (conttiros >= 35) {
            conttiros = 25;
        }
        return labels[conttiros];
    }

    public JLabel[] getLabels() {
        return labels;
    }

    public void setLabels(JLabel[] labels) {
        this.labels = labels;
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(this, "Game Over", "Game Over", JOptionPane.YES_NO_OPTION);
        this.dispose();
    }

    public void Victory() {
        JOptionPane.showMessageDialog(this, "VICTORY", "YOU WIN", JOptionPane.YES_NO_OPTION);
        this.dispose();
    }

    public static void jugar() throws InterruptedException {

        Gui game = new Gui();
        game.getContentPane().addMouseListener(new ClickListener(game.Character));
        game.setVisible(true);

        while (true) {
            game.move();
            game.repaint();
            Thread.sleep(6);
        }
    }

}
