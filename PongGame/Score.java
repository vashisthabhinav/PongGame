package Pong;
import java.awt.*;
public class Score extends Rectangle{
    static int GAME_WIDTH=1000;
    static int GAME_HEIGHT=1000;
    int player1;
    int player2;

    Score(int gameWidth, int gameHeight){
        Score.GAME_WIDTH =GAME_WIDTH;
        Score.GAME_HEIGHT=GAME_HEIGHT;
    }
    public void draw(Graphics g){
        g.setColor(Color.white);
        g.setFont(new Font("Consolas",Font.BOLD,60));
        g.drawLine(GAME_WIDTH/2,0,GAME_WIDTH/2,GAME_HEIGHT);
        g.drawString(String.valueOf(player1),(GAME_WIDTH/2)-50,50);
        g.drawString(String.valueOf(player2),(GAME_WIDTH/2)+50,50);
    }
}
