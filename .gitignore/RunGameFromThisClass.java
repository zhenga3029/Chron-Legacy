package chron;
import java.awt.Component;

import javax.swing.JFrame;

public class RunGameFromThisClass extends JFrame {

	private static final int WIDTH = 1600;
	private static final int HEIGHT = 1000;

	public RunGameFromThisClass()
	{
		super("Chron Legacy");
		setSize(WIDTH,HEIGHT);

		Chron theGame = new Chron();
		((Component)theGame).setFocusable(true);

		getContentPane().add(theGame);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main( String args[] )
	{
		RunGameFromThisClass run = new RunGameFromThisClass();
	}
}
