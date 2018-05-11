package chron;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class Wall implements Locatable {
	private int xPos, yPos;
	private Image image;
	
	public void setPos(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setX(int x) {
		xPos=x;
	}

	public void setY(int y) {
		yPos=y;
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}
	
	public void setImage(String s){
		try{
			image=ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\chron\\"+s));
		}
		catch(Exception e){
			System.err.println("Couldn't load "+s);
		}
	}
	
	public void draw(Graphics window){
		window.drawImage(image,getX(),getY(),200,200,null);
	}
}
