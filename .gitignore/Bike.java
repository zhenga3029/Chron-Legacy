package chron;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

public class Bike extends MobileObject
{
	private int speed;
	private Image image;

	public Bike()
	{
		this(0,0,0);
	}

	public Bike(int x, int y)
	{
		this(x,y,0);
	}

	public Bike(int x, int y, int s)
	{
		super(x, y);
		speed=s;
	}
	
	public void setImage(String s){
		try{
			image=ImageIO.read(new File(System.getProperty("user.dir")+"\\src\\chron\\"+s));
		}
		catch(Exception e){
			System.err.println("Couldn't load "+s);
		}
	}

	public void setSpeed(int s)
	{
		speed=s;
	}

	public int getSpeed()
	{
	   return speed;
	}
	
	public boolean intersectsWall(Wall w){
		if(getX()+30>=w.getX() && w.getX()+200>=getX()){
			if(getY()+30>=w.getY() && w.getY()+200>=getY()){
				return true;
			}
		}
		return false;
	}

	public void draw( Graphics window )
	{
		window.drawImage(image,getX(),getY(),30,30,null);
	}

	public String toString()
	{
		return super.toString() + " " + getSpeed();
	}
}