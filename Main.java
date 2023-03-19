import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

import javax.swing.JFrame;

public class Main extends JFrame implements ActionListener{
	
	static boolean gameJustStarted = true;
	static ArrayList<Monster> startingMonsters = new ArrayList<Monster>();
	int oldplayerx = 241;
	int oldplayery = 241;
	int playerx = 241;
	int playery = 241;
	
	
//-----------------------------------------------------------------------------------------------PAINT METHOD
	
	public void paint(Graphics g) {
		
		BoxDrawer drawer = new BoxDrawer();
		
		g.clearRect(oldplayerx-10,oldplayery-10,20,20);
		g.clearRect(oldplayerx+10,oldplayery+10,20,20);
		g.clearRect(oldplayerx+10,oldplayery-10,20,20);
		g.clearRect(oldplayerx-10,oldplayery+10,20,20);
		
		
		if(gameJustStarted) {
				
			for(int i=0; i<startingMonsters.size(); i++) {
				g.clearRect(startingMonsters.get(i).oldx+10, startingMonsters.get(i).oldy+10, 20, 20);
				g.clearRect(startingMonsters.get(i).oldx-10, startingMonsters.get(i).oldy-10, 20, 20);
				g.clearRect(startingMonsters.get(i).oldx+10, startingMonsters.get(i).oldy-10, 20, 20);
				g.clearRect(startingMonsters.get(i).oldx-10, startingMonsters.get(i).oldy+10, 20, 20);	
			}
			
			
			for(int i=0; i<startingMonsters.size(); i++) {
				if(Math.abs(playerx-startingMonsters.get(i).x) <= 20 && Math.abs(playery-startingMonsters.get(i).y) <= 20)
					System.exit(0);
				
				drawer.drawBox(g, startingMonsters.get(i).x, startingMonsters.get(i).y, Color.BLUE.darker());	
			}
			
			drawer.drawBox(g, playerx, playery, Color.GREEN);
		}
			
		
		
	}

//-----------------------------------------------------------------------------------------------GUI METHOD
	
	public Main() {
		
		setSize(500,500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    addKeyListener(new KeyListener() {
	        @Override
	        public void keyTyped(KeyEvent e) {
	        }

	        @Override
	        public void keyPressed(KeyEvent e) {
        	
	        	if(e.getKeyChar() == 'a' || e.getKeyChar() == 'A') {
	        		oldplayery = playery;
	        		oldplayerx = playerx;
	        		if(playerx-30 < 0)
	        			playerx = 0;
	        		else
	        		playerx = playerx - 10;
	        		repaint();
	        	}
    	
	        	if(e.getKeyChar() == 'w' || e.getKeyChar() == 'W') {
	        		oldplayerx = playerx;
	        		oldplayery = playery;
	        		if(playery-30 < 0)
	        			playery = 20;
	        		else
	        		playery = playery - 10;
	        		repaint();
	        	}
	        	
	        	if(e.getKeyChar() == 's' || e.getKeyChar() == 'S') {
	        		oldplayerx = playerx;
	        		oldplayery = playery;
	        		if(playery+30 > 500)
	        			playery = 480;
	        		else
	        		playery = playery + 10;
	        		repaint();
	        	}
      	
	        	if(e.getKeyChar() == 'd' || e.getKeyChar() == 'D') {
	        		oldplayery = playery;
	        		oldplayerx = playerx;
	        		if(playerx+30 > 500)
	        			playerx = 480;
	        		else
	        		playerx = playerx + 10;
	        		repaint();
	        	}
    	
	        	
	        }

	       @Override
	        public void keyReleased(KeyEvent e) {
	        }	
	    });
		
		
		
		
		
		
		
		repaint();
		setVisible(true);
	}
	
//-----------------------------------------------------------------------------------------------
	
	public void actionPerformed(ActionEvent e) {
		
		
		
		
		
	}

//-----------------------------------------------------------------------------------------------MONSTER METHOD	
	
	public class Monster extends Thread {
		
		int x;
		int y;
		int oldx;
		int oldy;
		
		ReentrantLock lock = new ReentrantLock();
		
		
		public Monster(int x, int y) {
			gameJustStarted = true;
		
			this.x = x;
			this.y = y;
			this.oldx = x;
			this.oldy = y;
			
			startingMonsters.add(this);
			startingMonsters.trimToSize();
			
			repaint();
			
		}
		
//-------		
		
		public void run() {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while(playerx != this.x && playery != this.y) {
				
			this.oldx = x;
			this.oldy = y;
				
			if( playerx-this.x > 0 )
				this.x = this.x + 10;
			else
				this.x = this.x - 10;	
			
			repaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if( playery-this.y > 0 )
				this.y = this.y + 10;
			else
				this.y = this.y - 10;
			
			repaint();
			
			}	

		}
		
		
	}
	
//-----------------------------------------------------------------------------------------------MAIN

	public static void main(String[] args) {
		
		int number_of_monsters = Integer.parseInt(args[0]);
		System.out.println(number_of_monsters);
		Main m = new Main();
		
		Main.Monster [] monsters = new Main.Monster[number_of_monsters];
		
		Random r = new Random();
		
		for(int i=0;i<number_of_monsters;i++)
		{
			monsters[i] = m.new Monster(Math.abs(r.nextInt()%500),Math.abs(r.nextInt()%500));		
		}
		
		for(int i=0;i<number_of_monsters;i++)
			monsters[i].start();
		
		try {
			for(int i=0;i<number_of_monsters;i++)
				monsters[i].join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}
	
}