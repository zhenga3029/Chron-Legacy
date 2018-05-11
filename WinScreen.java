package chron;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class WinScreen {
	private int winner;
	public void setWinner(int x){
		winner=x;
	}
	private Font myFont = new Font("Serif", Font.ITALIC, 30);
	public void display(Graphics gg){
		gg.setFont(myFont);
		gg.setColor(Color.white);
		gg.fillRect(0,0,1600,1000);
		gg.setColor(Color.RED);
		gg.drawString("Chron Legacy", 700, 100 );
		gg.setColor(Color.BLUE);
		gg.drawString("Adam Zheng P4", 700, 200);
		gg.setColor(Color.BLACK);
		gg.drawString("PLAYER "+winner+" WINS!!!!!!!!!",700, 300);
		gg.drawString("Returning to the Start Menu...", 700, 600);
	}
}
