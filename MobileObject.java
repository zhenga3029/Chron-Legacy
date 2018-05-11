package chron;
import java.awt.Graphics;

public abstract class MobileObject implements Locatable {
	private int xPos;
	private int yPos;
	private static final int LEFT=0,RIGHT=1,DOWN=2,UP=3;

	public MobileObject()
	{
		setPos(0,0);
	}

	public MobileObject(int x, int y)
	{
		setPos(x,y);
	}

	public void setPos(int x, int y)
	{
		setX(x);
		setY(y);
	}


	public void setX(int x)
	{
		xPos=x;
	}


	public void setY(int y)
	{
		yPos=y;
	}

	public int getX()
	{
		return xPos;
	}


	public int getY()
	{
		return yPos;
	}

	public abstract void setSpeed( int s );
	public abstract int getSpeed();
	public abstract void draw(Graphics window);

	public void move(int dir)
	{
		if(dir==LEFT)
	      setX(getX()-getSpeed());
		else if(dir==RIGHT)
			setX(getX()+getSpeed());
		else if(dir==UP)
			setY(getY()-getSpeed());
		else if(dir==DOWN)
			setY(getY()+getSpeed());
	}
}