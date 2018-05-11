package chron;

import java.awt.Color;
import java.awt.Graphics;

public class Trail {
	Color c;
	private boolean[][] a;
	public Trail(){
		a=new boolean[100][160];
	}
	public Trail(Color cc){
		a=new boolean[100][160];
		setColor(cc);
	}
	public void setColor(Color cc){
		c=cc;
	}
	public void draw(Graphics window){
		window.setColor(c);
		for(int i=0;i<100;i++){
			for(int j=0;j<160;j++){
				if(a[i][j]){
					window.fillRect(j*10, i*10, 10, 10);
				}
			}
		}
	}
	public boolean setAndCheckIfGood(int i,int j,Trail o){
		a[i][j]=true;
		if(o.isOn(i, j)){
			return false;
		}
		return true;
	}
	public boolean isOn(int i,int j){
		return a[i][j];
	}
}
