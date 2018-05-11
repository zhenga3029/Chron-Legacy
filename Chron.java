package chron;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;


public class Chron extends Canvas implements KeyListener, Runnable {
	
	private static final int[] dx={-1,1,0,0};
	private static final int[] dy={0,0,1,-1};
	private static final int LEFT=0,RIGHT=1,DOWN=2,UP=3;
	private static final int MAXTICKS=60;
	private static final int DELAYTIME=200;
	private Player p1,p2;
	private int delay;
	private int paused;
	private Font myFont = new Font("Arial", Font.BOLD, 24);
	private boolean started;
	private Wall w;
	private BufferedImage back;
	private StartMenu stmenu;
	private WinScreen winscr;
	
	public Chron() {
		stmenu=new StartMenu();
		winscr=new WinScreen();
		started=false;
		paused=0;
		setBackground(Color.white);
		p1=new Player();
		p2=new Player();
		p1.score=p2.score=p1.tick=p2.tick=0;
		delay=10;
		p1.dir=RIGHT;
		p2.dir=LEFT;
		p1.bike=new Bike(200, 470, 2);
		p1.bike.setImage("standard.png");
		p2.bike=new Bike(1300, 470, 2);
		p2.bike.setImage("alarm.png");
		p1.trail=new Trail(Color.magenta);
		p2.trail=new Trail(Color.ORANGE);
		w=new Wall();
		w.setPos(700, 400);
		w.setImage("wall.png");
		this.addKeyListener(this);
		new Thread(this).start();

		setVisible(true);
	}
	
	public void update(Graphics window){
		paint(window);
	}
	
	private void hardReset(){
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			System.err.println("sleep broken");
		}
		stmenu=new StartMenu();
		winscr=new WinScreen();
		started=false;
		paused=0;
		setBackground(Color.white);
		p1.score=p2.score=p1.tick=p2.tick=0;
		delay=10;
		p1.dir=RIGHT;
		p2.dir=LEFT;
		p1.bike=new Bike(200, 470, 2);
		p1.bike.setImage("standard.png");
		p2.bike=new Bike(1300, 470, 2);
		p2.bike.setImage("alarm.png");
		p1.trail=new Trail(Color.magenta);
		p2.trail=new Trail(Color.ORANGE);
		w=new Wall();
		w.setPos(700, 400);
		w.setImage("wall.png");;
	}
	
	private void reset(){
		p1.dir=RIGHT;
		p2.dir=LEFT;
		delay=DELAYTIME;
		p1.tick=p2.tick=0;
		p1.bike=new Bike(200, 470, 2);
		p1.bike.setImage("standard.png");
		p2.bike=new Bike(1300, 470, 2);
		p2.bike.setImage("alarm.png");
		p1.trail=new Trail(Color.magenta);
		p2.trail=new Trail(Color.ORANGE);
	}
	
	public void paint(Graphics window){
		
		
		Graphics2D twoDGraph = (Graphics2D)window;
		if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));
		Graphics gg = back.createGraphics();
		
		if(stmenu.isOn()) {
			stmenu.display(gg);
			twoDGraph.drawImage(back,null,0,0);
			return;
		}
		
		if(paused==1){
			gg.setFont(myFont);
			gg.setColor(Color.GREEN);
			gg.drawString("PAUSED...", 500, 25);
			twoDGraph.drawImage(back,null,0,0);
			return;
		}
		
		if(delay>0){
			delay--;
			return;
		}
		
		if(p1.score==6){
			winscr.setWinner(1);
			winscr.display(gg);
			twoDGraph.drawImage(back,null,0,0);
			hardReset();
			return;
		}
		if(p2.score==6){
			winscr.setWinner(2);
			winscr.display(gg);
			twoDGraph.drawImage(back,null,0,0);
			hardReset();
			return;
		}
		
		gg.setFont(myFont);
		gg.setColor(Color.white);
		gg.fillRect(0,0,1600,1000);
		gg.setColor(Color.BLUE);
		gg.drawString("Chron Legacy SCORE: "+p1.score+" to "+p2.score, 25, 25 );
		gg.setColor(Color.GRAY);
		gg.drawString("(UN)PAUSE - P", 25, 75);
		
		// check if match point
		if(p1.score==5 || p2.score==5){
			gg.setFont(myFont);
			gg.setColor(Color.BLACK);
			gg.drawString("MATCH POINT!", 700, 25);
		}
		
		boolean someoneWon=false;
		
		// check out of bounds
		if(p1.bike.getX()<0 || p1.bike.getX()+30>=1600 || p1.bike.getY()<0 || p1.bike.getY()+60>=1000){
			p2.score++;
			gg.setColor(Color.RED);
			
			gg.drawString("PLAYER 1 OUT OF BOUNDS!", 500, 100);
			someoneWon=true;
		}
		if(p2.bike.getX()<0 || p2.bike.getX()+30>=1600 || p2.bike.getY()<0 || p2.bike.getY()+60>=1000){
			p1.score++;
			gg.setColor(Color.RED);
			gg.drawString("PLAYER 2 OUT OF BOUNDS!", 500, 100 );
			someoneWon=true;
		}
		
		// check if hit wall
		if(p1.bike.intersectsWall(w)){
			p2.score++;
			gg.setColor(Color.RED);
			gg.drawString("PLAYER 1 HIT WALL!", 500, 100);
			someoneWon=true;
		}
		if(p2.bike.intersectsWall(w)){
			p1.score++;
			gg.setColor(Color.RED);
			gg.drawString("PLAYER 2 HIT WALL!", 500, 100);
			someoneWon=true;
		}
		
		// check if clocks collide
		if(p1.bike.getX()+30>=p2.bike.getX() && p2.bike.getX()+30>=p1.bike.getX()){
			if(p1.bike.getY()+30>=p2.bike.getY() && p2.bike.getY()+30>=p1.bike.getY()){
				gg.setColor(Color.RED);
				gg.drawString("PLAYERS HAVE COLLIDED!", 500, 100);
				someoneWon=true;
			}
		}
		
		// add to trail and check if intersects self too long or intersects other person's trail
		if(p1.trail.isOn(p1.bike.getY()/10+1,p1.bike.getX()/10+1)){
			p1.tick++;
			if(p1.tick==MAXTICKS){
				gg.setColor(Color.RED);
				gg.drawString("PLAYER 1 STAYED IN OWN PATH FOR TOO LONG!", 500, 100);
				p2.score++;
				someoneWon=true;
			}
		}
		else p1.tick=0;
		if(p2.trail.isOn(p2.bike.getY()/10+1,p2.bike.getX()/10+1)){
			p2.tick++;
			if(p2.tick==MAXTICKS){
				gg.setColor(Color.RED);
				gg.drawString("PLAYER 2 STAYED IN OWN PATH FOR TOO LONG!", 500, 100);
				p1.score++;
				someoneWon=true;
			}
		}
		else p2.tick=0;
		if(!p1.trail.setAndCheckIfGood(p1.bike.getY()/10+1,p1.bike.getX()/10+1,p2.trail)){
			gg.setColor(Color.RED);
			gg.drawString("PLAYER 1 LOSES!", 500, 100);
			p2.score++;
			someoneWon=true;
		}
		p1.bike.move(p1.dir);
		if(!p2.trail.setAndCheckIfGood(p2.bike.getY()/10+1,p2.bike.getX()/10+1,p1.trail)){
			gg.setColor(Color.RED);
			gg.drawString("PLAYER 2 LOSES!", 500, 100);
			p1.score++;
			someoneWon=true;
		}
		p2.bike.move(p2.dir);
		
		// draw the trail and bikes and wall
		p1.trail.draw(gg);
		p2.trail.draw(gg);
		p1.bike.draw(gg);
		p2.bike.draw(gg);
		w.draw(gg);
		
		twoDGraph.drawImage(back,null,0,0);
		
		// if someone lost, then reset
		if(someoneWon) reset();
	}

	public void run() {
		try{
			while(true){
				Thread.currentThread().sleep(5);
				repaint();
			}
		}
		catch(Exception e){
			System.err.println("Error in the run method");
		}
	}
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_LEFT && p2.dir!=RIGHT) p2.dir=0;
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT && p2.dir!=LEFT) p2.dir=1;
		if(arg0.getKeyCode() == KeyEvent.VK_DOWN && p2.dir!=UP) p2.dir=2;
		if(arg0.getKeyCode() == KeyEvent.VK_UP && p2.dir!=DOWN) p2.dir=3;
		if(arg0.getKeyCode() == KeyEvent.VK_A && p1.dir!=RIGHT) p1.dir=0;
		if(arg0.getKeyCode() == KeyEvent.VK_D && p1.dir!=LEFT) p1.dir=1;
		if(arg0.getKeyCode() == KeyEvent.VK_S && p1.dir!=UP) p1.dir=2;
		if(arg0.getKeyCode() == KeyEvent.VK_W && p1.dir!=DOWN) p1.dir=3;
		if(arg0.getKeyCode() == KeyEvent.VK_P) paused^=1;
		if(arg0.getKeyCode() == KeyEvent.VK_SPACE) stmenu.START();
		repaint();
	}

	public void keyReleased(KeyEvent arg0) { }
	public void keyTyped(KeyEvent arg0) { }

}


