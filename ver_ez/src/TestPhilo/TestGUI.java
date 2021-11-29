package TestPhilo;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class TestGUI extends JFrame{
	Button stopButton;
	Image image;
	
	public TestGUI(){
	
		super("Dinner philosophers");
		
		setSize(1100, 1200);
		setLayout(new GridLayout(12,1));
		
		addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			setVisible(false);
			dispose();
			System.exit(0);
			}
		});
		loadImage();
		
		init();
	
	}
	
    public void loadImage() {  
    	String path = "data/philo.png"; 
    	File file = new File(path);
    	try {
			image = ImageIO.read(file);
		} catch (IOException e) {}
    }  
    
    @Override
    public void paint(Graphics g) {
    	super.paint(g);
    	g.drawImage(image, 0, -185, this);
    }

	public void stop(){
		if(!Test.stop){
			stopButton.setLabel("Start");
		} else stopButton.setLabel("Stop");
		
		Test.stop = !Test.stop;
		
		if(!Test.stop){
			try{
				notifyAll();
			}catch(Exception e){}
		}
	}

	public void init(){
	
		Button button = new Button("Stop");
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				stop();
			}
		});
		
		add(button);
		stopButton = button;
		
		setVisible(true);
	}
}