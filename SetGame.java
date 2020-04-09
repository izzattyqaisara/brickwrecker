package BrickWrecker;

import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SetGame extends JPanel implements KeyListener, ActionListener {


    private boolean play = false;
    private int score = 0;
    private int totalbricks = 21;
    private Timer Timer;
    private int delay = 8;
    private int playerX = 310;
    private int ballX = 120;
    private int ballY = 350;
    private int dirX= -1;
    private int dirY = -2;
    private SetBrick map;

    public SetGame() {
        map = new SetBrick(3, 7);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        Timer = new Timer(delay, this);
        Timer.start();
    }

    public void paint(Graphics g) {
        //background
        g.setColor(Color.pink);
        g.fillRect(1, 1, 692, 592);

        map.draw((Graphics2D) g);

        //borders
        g.setColor(Color.black);
        g.fillRect(0, 0, 3, 592);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(683, 0, 3, 592);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("" + score, 590, 30);


        g.setColor(Color.black);
        g.fillRect(playerX, 550, 100, 8);

        //ball
        g.setColor(Color.magenta);
        g.fillOval(ballX, ballY, 20, 20);

        if (ballY > 570) {
            play = false;
            dirX = 0;
            dirY = 0;
            g.setColor(Color.red);
            g.setFont(new Font("SansSerif", Font.BOLD, 30));
            g.drawString("    GAME OVER! Score: " + score, 140, 300);

            g.setColor(Color.white);
            g.setFont(new Font("Courier New", Font.BOLD, 30));
            g.drawString("   Press ENTER to Restart", 95, 340);
        }
        if(totalbricks == 0){
            play = false;
            dirY = -2;
            dirX = -1;
            g.setColor(Color.red);
            g.setFont(new Font("SansSerif",Font.BOLD,30));
            g.drawString("    YOU WON! Score: "+score,150,250);

            g.setFont(new Font("Courier New", Font.BOLD, 30));
            g.drawString("   Press ENTER to Restart", 100, 290);


        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer.start();

        if (play) {
            if (new Rectangle(ballX, ballY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
                dirY= -dirY;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.bricksWidth + 80;
                        int brickY = i * map.bricksHeight + 50;
                        int bricksWidth = map.bricksWidth;
                        int bricksHeight = map.bricksHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, bricksWidth, bricksHeight);
                        Rectangle ballrect = new Rectangle(ballX, ballY, 20, 20);
                        Rectangle brickrect = rect;

                        if (ballrect.intersects(brickrect)) {
                            map.setBricksValue(0, i, j);
                            totalbricks--;
                            score += 5;
                            if (ballX + 19 <= brickrect.x || ballX + 1 >= brickrect.x + bricksWidth) {
                                dirX = -dirX;
                            } else {
                                dirY = -dirY;
                            }
                            break A;
                        }
                    }


                }
            }


            ballX += dirX;
            ballY += dirY;
            if (ballX < 0) {
                dirX = -dirX;
            }
            if (ballY < 0) {
                dirY = -dirY;
            }
            if (ballX > 670) {
                dirX = -dirX;
            }
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                ballX = 120;
                ballY = 350;
                dirX = -1;
                dirY = -2;
                score = 0;
                playerX = 310;
                totalbricks = 21;
                map = new SetBrick(3, 7);

                repaint();
            }
        }


    }

    public void moveRight ()
    {
        play = true;
        playerX += 20;
    }
    public void moveLeft ()
    {
        play = true;
        playerX -= 20;
    }

}
