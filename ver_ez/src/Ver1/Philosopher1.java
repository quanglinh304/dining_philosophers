package Ver1;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;

import TestPhilo.Test;

public class Philosopher1 extends Thread implements Runnable {
	JButton state = new JButton(); 
	JButton text = new JButton();
	JButton color1 = new JButton();
	JButton color2 = new JButton();
	JButton color3 = new JButton();
	JButton color4 = new JButton();
	JButton count = new JButton();
	JFrame UI;
	
	private int idPhilo;
	public static List<Fork> fork = new ArrayList<Fork>();
	private int[] countEat = {0, 0, 0, 0, 0};

	static Semaphore mutex = new Semaphore(1);
	
	public Philosopher1(JFrame test, int id) {
		this.UI = test;
		this.idPhilo = id;
		
		state = new JButton("P" + this.idPhilo);
		this.UI.add(state);
	}
	
	public int getIdPhilo() {
		return idPhilo;
	}

	public void setIdPhilo(int idPhilo) {
		this.idPhilo = idPhilo;
	}
	
	public void drawComment() {

		this.UI.add(text);
		this.UI.add(color1);
		this.UI.add(color2);
		this.UI.add(color3);
		this.UI.add(color4);
		this.UI.add(count);
		
		text.setText("Count eat");
		text.setBounds(5, 520, 90, 30);
		
		color1.setBackground(Color.YELLOW);
		color1.setText("Hungry");
		color1.setBounds(10, 80, 100, 30);
		
		color2.setBackground(Color.RED);
		color2.setText("Eating");
		color2.setBounds(10, 115, 100, 30);
		
		color3.setBackground(Color.GREEN);
		color3.setText("Back think");
		color3.setBounds(10, 150, 100, 30);
		
		color4.setBackground(Color.PINK);
		color4.setText("Thinking");
		color4.setBounds(10, 185, 100, 30);
	}
	
	public void setColorButton(String action) {
		state.setFont(new Font("Arial", Font.PLAIN, 23));
		if (action == " is eating")
			state.setBackground(Color.RED);
		else if (action == " backs to thinking")
			state.setBackground(Color.GREEN);
		else if (action == " is hungry")
			state.setBackground(Color.YELLOW);
		else state.setBackground(Color.PINK);
		
		state.setForeground(Color.BLACK);
	}
	
	private void countEat(String action) {
		if (action ==  " is eating") countEat[idPhilo - 1] ++;
		count.setText("P" + idPhilo + ": "+ countEat[idPhilo - 1]);
		count.setBackground(Color.WHITE);
		
		switch (idPhilo) {
		case 1:
        	count.setBounds(10, 555, 75, 30);
    	    break;
        case 2:
        	count.setBounds(10, 590, 75, 30); 
    	    break;
        case 3:
        	count.setBounds(10, 625, 75, 30); 
    	    break;
        case 4:
        	count.setBounds(10, 660, 75, 30); 
    	    break;
        case 5:
        	count.setBounds(10, 695, 75, 30); 
    	    break;
        }
	}
	
	public void doNothing() {
		Random rand = new Random();
		int time = rand.nextInt(4500) + 1;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {}
	}
	
	private void doAction(String action) {
		
		UI.setLayout(null);
		System.out.println(Thread.currentThread().getName() + " " + action);
		state.setText(Thread.currentThread().getName() + " " + action);
		setColorButton(action);
		countEat(action);
		
		switch (idPhilo) {
		case 1:
        	state.setBounds(405, 120, 300, 50);
        	break;
        case 2:
        	state.setBounds(130, 320, 300, 50); 
    	    break;
        case 3:
        	state.setBounds(210, 600, 300, 50); 
    	    break;
        case 4:
        	state.setBounds(590, 600, 300, 50); 
    	    break;
        case 5:
        	state.setBounds(690, 320, 300, 50); 
    	    break;
        }
		
		UI.setVisible(true);
		doNothing();
    }
	
	void eat() {
		doAction(" is eating");
		doAction(" backs to thinking");
	}
	
	void think() {
		doAction(" is thinking");
		doAction(" is hungry");
	}
	
	@Override
	public void run() {
		drawComment();
		try {
			while (true) {
				while(!Test.stop){
				
					think();
					mutex.acquire();
					fork.get(idPhilo % 5).sem.acquire();
					fork.get((idPhilo + 1) % 5).sem.acquire();
		
					
					mutex.release();
					eat();
					fork.get(idPhilo % 5).sem.release();
					fork.get((idPhilo + 1) % 5).sem.release();

				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
		
	}
}
