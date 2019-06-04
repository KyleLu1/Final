/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BadmintonFiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

/**
 *
 * @author luk8724
 */
public class Badminton extends Canvas implements KeyListener, Runnable {

    private Birdie birdie;
    private Block net;
    private Player leftPlayer;
    private Player rightPlayer;
    private boolean[] keysLeft;
    private boolean[] keysRight;
    private BufferedImage back;
    private Block bottom;
    private Block leftWall;
    private Block rightWall;
    private Block top;
    private int leftScore;
    private int rightScore;
    private FileWriter writer;
    private int playerOneWins;
    private int playerTwoWins;

    public Badminton() {

        try {
            File record = new File("Record.txt");
            writer = new FileWriter("record.txt");
            writer.write(playerOneWins + " wins for player 1" + "                   " + playerTwoWins + "wins for player 2" );
            FileReader fr = new FileReader("C:\\Users\\luk8724\\Documents\\NetBeansProjects\\Final-master\\record.txt");
            boolean flag = false;
                int i;
                while ((i = fr.read()) != -1) {
                    if(i >= 48 && i <= 57)
                    {
                        playerOneWins = i - 48;
                        flag = true;
                        if(flag == true)
                        {
                            playerTwoWins = i -48;
                        }
                    }
                }
            
            
            
            if (record.length() != 0) {
                BufferedReader a = new BufferedReader(new FileReader(record));
                Scanner b = new Scanner(record);
                b.useDelimiter("\\Z");
                String c = b.next();
                System.out.println(c);
            }
        } catch (Exception ex) {
            System.out.println("Error");
        }
        //instantiate objects
        birdie = new Birdie(350, 200, 15, 25, 2, 2);
        leftPlayer = new Player(60, 420, 40, 80, 3);
        rightPlayer = new Player(720, 420, 40, 80, 3);
        net = new Block(400, 380, 10, 120);
        bottom = new Block(20, 500, 740, 20);
        leftWall = new Block(0, 0, 20, 520);
        rightWall = new Block(760, 0, 20, 520);
        top = new Block(0, 0, 780, 20);
        leftScore = 0;
        rightScore = 0;
        playerOneWins = 0;
        playerTwoWins = 0;

        keysLeft = new boolean[4];
        keysLeft = new boolean[]{false, false, false, false};

        keysRight = new boolean[4];
        keysRight = new boolean[]{false, false, false, false};

        setBackground(Color.WHITE);
        setVisible(true);

        new Thread(this).start();
        addKeyListener(this);
    }

    public void update(Graphics window) {
        paint(window);
    }

    public void paint(Graphics window) {
        Graphics2D twoDGraph = (Graphics2D) window;

        if (back == null) {
            back = (BufferedImage) (createImage(getWidth(), getHeight()));
        }

        Graphics graphToBack = back.createGraphics();

        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(440, 520, 80, 80);

        birdie.moveAndDraw(graphToBack);
        leftPlayer.draw(graphToBack);
        rightPlayer.draw(graphToBack);
        net.draw(graphToBack);
        bottom.draw(graphToBack);
        leftWall.draw(graphToBack);
        rightWall.draw(graphToBack);
        top.draw(graphToBack);

        graphToBack.setColor(Color.red);

        if (!(birdie.getY() >= 20 && birdie.getY() <= 450)) {
            if (birdie.getX() < 400 && birdie.getY() > 20) {
                rightScore = rightScore + 1;
                
                window.setColor(Color.WHITE);
                
                birdie.setX(leftPlayer.getX());
                birdie.setY(leftPlayer.getY() - 40);
                birdie.moveAndDraw(graphToBack);

            }
            if (birdie.getX() > 400 && birdie.getY() > 20) {
                leftScore = leftScore + 1;
                
                window.setColor(Color.WHITE);
                
                birdie.setX(rightPlayer.getX());
                birdie.setY(rightPlayer.getY() - 40);
                birdie.moveAndDraw(graphToBack);
            }
            birdie.setySpeed(-birdie.getySpeed());
        }

        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(390, 520, 20, 20);
        graphToBack.setColor(Color.WHITE);
        graphToBack.fillRect(550, 520, 20, 20);
        graphToBack.setFont(new Font("Arial", Font.ROMAN_BASELINE, 18));
        graphToBack.setColor(Color.BLACK);
        graphToBack.drawString("rightScore = " + rightScore, 450, 540);
        graphToBack.drawString("leftScore = " + leftScore, 300, 540);

        if (!(birdie.getX() >= 20 && birdie.getX() <= 740)) {
            birdie.setxSpeed(-birdie.getxSpeed());
        }

        if (birdie.getX() + birdie.getWidth() == net.getX() && birdie.getY() < net.getY() + net.getHeight() && birdie.getY() > net.getY()) {
            birdie.setxSpeed(-birdie.getxSpeed());
        }
        if (birdie.getX() == net.getX() + net.getWidth() && birdie.getY() < net.getY() + net.getHeight() && birdie.getY() > net.getY()) {
            birdie.setxSpeed(-birdie.getxSpeed());
        }
        if (birdie.getY() == net.getY() && birdie.getX() > net.getX() && birdie.getX() < net.getX() + net.getWidth()) {
            birdie.setySpeed(-birdie.getySpeed());
        }

        /*
         just ask me bout this tmrw too tired
        
         if ( birdie.getX() + birdie.getHeight() == net.getX() + net.getWidth() && birdie.getY() + birdie.getHeight() >= net.getY() && 
         birdie.getY()  + birdie.getHeight() <= net.getY() + net.getHeight())
         birdie.setxSpeed(-birdie.getxSpeed());
         if ( birdie.getX()+ birdie.getWidth() == net.getX() &&
         birdie.getY() + birdie.getHeight() > net.getY() && birdie.getY() + birdie.getHeight() < net.getY() + net.getHeight() )
         birdie.setxSpeed(-birdie.getxSpeed());
         */
        //if(birdie.didCollideTop(bottom) && birdie.getX()>400)
        //{
        //    System.out.println("lEFT BAKSJLDHNSAK");
        //    leftScore += 1;   
        //    birdie.setX(rightPlayer.getX());
        //    birdie.setY(rightPlayer.getY() - 40);
        //}
        //if(birdie.didCollideTop(bottom) && birdie.getX()<400)
        //{
        //   System.out.println("right BANJKSLDBSA");
        //    rightScore += 1;   
        //    birdie.setX(leftPlayer.getX());
        //    birdie.setY(leftPlayer.getY() - 40);
        //}
        if (leftScore == 7) {
            graphToBack.drawString("Player One Wins!", 375, 200);

            playerOneWins += 1;

            int a = birdie.getX();
            int b = birdie.getY();

            birdie.setX(50);
            birdie.setY(50);

            graphToBack.setColor(Color.WHITE);
            graphToBack.fillRect(a, b, 30, 50);

            birdie.setxSpeed(0);
            birdie.setySpeed(0);

            try {
                writer = new FileWriter("record.txt");
                String hold = playerOneWins + " wins for player 1";
                String hold1 = playerTwoWins + " wins for player 2";
                writer.write(hold);
                writer.write(hold1);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (rightScore == 7) {
            graphToBack.drawString("Player Two Wins!", 375, 200);

            playerTwoWins += 1;
            
            
            int a = birdie.getX();
            int b = birdie.getY();

            birdie.setX(50);
            birdie.setY(50);

            graphToBack.setColor(Color.WHITE);
            graphToBack.fillRect(a, b, 30, 50);
            birdie.setX(50);
            birdie.setY(50);
            birdie.setxSpeed(0);
            birdie.setySpeed(0);

            try {
                writer = new FileWriter("record.txt");
                String hold = playerTwoWins + " wins for player 2";
                String hold1 = playerOneWins + " wins for player 1";
                writer.write(hold1);
                writer.write(hold);
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (birdie.didCollideLeft(leftPlayer) && (birdie.didCollideTop(leftPlayer) || birdie.didCollideBottom(leftPlayer))
                && birdie.getX() + birdie.getWidth() > leftPlayer.getX() && birdie.getX() < leftPlayer.getX() + leftPlayer.getWidth()) {

            if (birdie.getX() <= leftPlayer.getX() + leftPlayer.getWidth() - Math.abs(birdie.getxSpeed())) {
                birdie.setySpeed(-birdie.getySpeed());
            } else {
                birdie.setxSpeed(-birdie.getxSpeed());

            }
        }

        if (birdie.didCollideRight(rightPlayer)
                && (birdie.didCollideTop(rightPlayer) || birdie.didCollideBottom(rightPlayer))
                && birdie.getX() + birdie.getWidth() > rightPlayer.getX() && birdie.getX() < rightPlayer.getX() + rightPlayer.getWidth()) {
            if (birdie.getX() + birdie.getWidth() >= rightPlayer.getX() + Math.abs(birdie.getxSpeed())) {
                birdie.setySpeed(-birdie.getySpeed());
            } else {
                birdie.setxSpeed(-birdie.getxSpeed());
            }
        }

        if (keysLeft[0] == true) {
            //leftPlayer.moveUpAndDraw(graphToBack);
        }
        if (keysLeft[1] == true) {
            //leftPlayer.moveDownAndDraw(graphToBack);
        }
        if (keysLeft[2] == true) { //left

            if (leftPlayer.getX() > 20) {
                leftPlayer.moveRightAndDraw(graphToBack);
            }
        }
        if (keysLeft[3] == true) { //right
            if (leftPlayer.getX() + leftPlayer.getWidth() < net.getX()) {
                leftPlayer.moveLeftAndDraw(graphToBack);
            }
        }

        if (keysRight[0] == true) {
            //rightPlayer.moveUpAndDraw(graphToBack);
        }
        if (keysRight[1] == true) {
            //rightPlayer.moveDownAndDraw(graphToBack);
        }
        if (keysRight[2] == true) {
            if (rightPlayer.getX() > net.getX() + net.getWidth()) {
                rightPlayer.moveRightAndDraw(graphToBack);
            }
        }
        if (keysRight[3] == true) {
            if (rightPlayer.getX() + rightPlayer.getWidth() < 800 - 50) {
                rightPlayer.moveLeftAndDraw(graphToBack);
            }
        }

        twoDGraph.drawImage(back, null, 0, 0);

    }

    public void keyPressed(KeyEvent e) {
        //leftplayer
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keysRight[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keysRight[1] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keysRight[2] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keysRight[3] = true;
        }

        //rightplayer
        if (e.getKeyCode() == KeyEvent.VK_S) {
            keysLeft[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            keysLeft[1] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            keysLeft[2] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            keysLeft[3] = true;
        }

        repaint();
    }

    public void keyReleased(KeyEvent e) {
        //leftplayer
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keysRight[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keysRight[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keysRight[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keysRight[3] = false;
        }

        //rightplayer
        if (e.getKeyCode() == KeyEvent.VK_S) {
            keysLeft[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            keysLeft[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            keysLeft[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            keysLeft[3] = false;
        }

        repaint();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void run() {
        try {
            while (true) {
                Thread.currentThread().sleep(5);
                repaint();
            }
        } catch (Exception e) {
        }
    }

}
