package Pong;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
public class GamePanel extends JPanel implements Runnable{
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH,GAME_HEIGHT);
    static final int BALL_DIAMETER=20;
    static final int PADDLE_WIDTH =21;
    static final int PADDLE_HEIGHT=102;
    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel(){
        newPaddle();
        newBall();
        score=new Score(GAME_WIDTH,GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new ActionListner());
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void newBall(){
        random = new Random();
        ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2),random.nextInt(GAME_HEIGHT-BALL_DIAMETER),BALL_DIAMETER,BALL_DIAMETER);
    }
    public void newPaddle(){
        paddle1 = new Paddle(0,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,1);
        paddle2 = new Paddle(GAME_WIDTH-PADDLE_WIDTH,(GAME_HEIGHT/2)-(PADDLE_HEIGHT/2),PADDLE_WIDTH,PADDLE_HEIGHT,2);

    }
    public void paint(Graphics g){
        image = createImage(getWidth(),getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw( Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }
    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }
    public void checkCollision(){
        //Stops paddle at window edges
        if (paddle1.y<=0){
            paddle1.y=0;
        }
        if (paddle1.y>=(GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle1.y=GAME_HEIGHT-PADDLE_HEIGHT;
        }
        if (paddle2.y<=0){
            paddle2.y=0;
        }
        if (paddle2.y>=(GAME_HEIGHT-PADDLE_HEIGHT)){
            paddle2.y=GAME_HEIGHT-PADDLE_HEIGHT;
        }
        //Bouncing the ball off the top and bottom edges
        if (ball.y<=0){
            ball.setYDirection(-ball.yVelocity);
        }

        if (ball.y>=GAME_HEIGHT-BALL_DIAMETER){
            ball.setYDirection(-ball.yVelocity);
        }

        //Bounces the ball off the paddle
        if (ball.intersects(paddle1)){
            ball.xVelocity = -ball.xVelocity;
            ball.xVelocity++;//Optional for more difficulty
        }
        if (ball.intersects(paddle2)){
            ball.xVelocity = -ball.xVelocity;
            ball.xVelocity--;//Optional for more difficulty
        }
        //Give a player 1 point and creates new paddle and ball

        if (ball.x<=0){
            score.player2++;
            newPaddle();
            newBall();
            System.out.println("Score of Player 2: "+score.player2);
        }
        if (ball.x>=GAME_WIDTH-BALL_DIAMETER){
            score.player1++;
            newPaddle();
            newBall();
            System.out.println("Score of Player 1: "+score.player1);
        }

    }
    public void run(){
        //Game Loop
        /*
        A game loop runs continuously during gameplay.
        Each turn of the loop, it processes user input without blocking, updates the game state, and renders the game.
        It tracks the passage of time to control the rate of gameplay.
        This pattern decouples progression of game time from user input and processor speed.
         */
        long lastTime =  System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000/amountOfTicks;
        double delta = 0;
        while (true){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            if (delta>=1){
                move();
                checkCollision();
                repaint();
                delta--;
//                System.out.println("TEST");

            }
        }
    }
    public class ActionListner extends KeyAdapter{
        public void keyPressed(KeyEvent e){
            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e){
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
