package chron;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class StartMenu {
	private boolean started=false;
	private Font myFont = new Font("Serif", Font.ITALIC, 30);
	public void START(){
		started=true;
	}
	public boolean isOn(){
		return !started;
	}
	public void display(Graphics gg){
		gg.setFont(myFont);
		gg.setColor(Color.white);
		gg.fillRect(0,0,1600,1000);
		gg.setColor(Color.RED);
		gg.drawString("Chron Legacy", 700, 100 );
		gg.setColor(Color.BLUE);
		gg.drawString("Adam Zheng P4", 700, 200);
		gg.setColor(Color.BLACK);
		gg.drawString("Instructions:\n Player 1 uses WASD to move, while player 2 uses arrow keys.",10,300);
		gg.drawString("The players control clocks, while leave a trail behind in its path.\n",10,350);
		gg.drawString("If a player hits other's path, stays in own path for too long, or hits wall/edge, then other player gains a point, and the game resets.", 10, 400);
		gg.drawString("If a players hit each other, then neither player gains a point, and the game resets.", 10, 450);
		gg.drawString("First player to get 6 points wins.", 10, 500);
		gg.drawString("Press SPACE to begin!", 700, 600);
	}
}
