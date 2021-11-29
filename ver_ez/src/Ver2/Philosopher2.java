package Ver2;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;

import TestPhilo.Test;

public class Philosopher2 extends Thread implements Runnable {
	
	private int idPhilo; 
	private Object firstFork;
    private Object secondFork;
    private int[] countEat = {0, 0, 0, 0, 0};
    
    JButton state = new JButton(); 
	JButton text = new JButton();
	JButton color1 = new JButton();
	JButton color2 = new JButton();
	JButton color3 = new JButton();
	JButton color4 = new JButton();
	JButton count = new JButton();
	JFrame UI;
	
	public Philosopher2(JFrame UI, int idPhilo, Object firstFork, Object secondFork) {				
		this.UI = UI;
		this.idPhilo = idPhilo;
		this.firstFork = firstFork;
		this.secondFork = secondFork;
	 
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
		
		color1.setBackground(Color.GREEN);
		color1.setText("Pick up Fork");
		color1.setBounds(10, 80, 115, 30);
		
		color2.setBackground(Color.RED);
		color2.setText("Eating");
		color2.setBounds(10, 115, 115, 30);
		
		color3.setBackground(Color.YELLOW);
		color3.setText("Put down Fork");
		color3.setBounds(10, 150, 115, 30);
		
		color4.setBackground(Color.PINK);
		color4.setText("Thinking");
		color4.setBounds(10, 185, 115, 30);
		
	}

	//khong the cung luc do: 2-3, 4-5
	//                 xanh: 1-2, 3-4
	//                 1 do - 5 xanh
	public void setColorButton(String action) {
		state.setFont(new Font("Arial", Font.PLAIN, 23));
		if (action == ": Picked up rightF, EAT"	|| action == ": Picked up leftF, EAT")
			state.setBackground(Color.RED);
		else if (action == ": Picked up leftF" || action == ": Picked up rightF")
			state.setBackground(Color.GREEN);
		else if (action == ": Putdown rightF" || action == ": Put down leftF")
			state.setBackground(Color.YELLOW);
		else state.setBackground(Color.PINK);
		
		state.setForeground(Color.BLACK);
	}
	
	private void countEat(String action) {
		if (action == ": Picked up rightF, EAT" 
		 || action == ": Picked up leftF, EAT") countEat[idPhilo - 1] ++;
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
	
	public void doNothing(){
		Random rand = new Random();
		int time = rand.nextInt(4500) + 1;
		try {
			Thread.sleep(time);
		} catch (Exception e) {}
	}
	 
	private void doAction(String action) {
		
		System.out.println(Thread.currentThread().getName() + " " + action);
		state.setText(Thread.currentThread().getName() + " " + action);
		
		setColorButton(action);
		countEat(action);
		UI.setLayout(null);
		switch (idPhilo) {
        case 1:
        	state.setBounds(405, 150, 300, 50);
        	break;
        case 2:
        	state.setBounds(120, 320, 300, 50); 
    	    break;
        case 3:
        	state.setBounds(210, 600, 300, 50); 
    	    break;
        case 4:
        	state.setBounds(590, 600, 300, 50); 
    	    break;
        case 5:
        	state.setBounds(700, 320, 300, 50); 
    	    break;
        }
		
		UI.setVisible(true);
        doNothing();
    }
	
    @Override
    public void run() {
    	drawComment();//Draw interface
        while (true) {
        	while(!Test.stop){
        		doAction(": Thinking");
        		if (idPhilo % 2 == 0) { // even pick left first
	                synchronized (firstFork) {
	                    doAction(": Picked up leftF"); // pick = GREEN
	                    synchronized (secondFork) {
	                        doAction(": Picked up rightF, EAT"); // eat = RED
	                        
	                        doAction(": Put down rightF");
	                    }
	                    doAction(": Put down leftF, back think");
	                }
                }else { // odd pick right first
                	synchronized (firstFork) {
                        doAction(": Picked up rightF"); // pick = GREEN
                        synchronized (secondFork) {
                            doAction(": Picked up leftF, EAT"); // eat = RED
                            
                            doAction(": Put down leftF");
                        }         
                        doAction(": Put down rightF, back think");
                	}
                }
        	}
        }
    }
}
