package TestPhilo;

import Ver1.Fork;
import Ver1.Philosopher1;
import Ver2.Philosopher2;

public class Test{
	public static boolean stop = false;
	static TestGUI test = new TestGUI();
	
	public static void PhilosopherVer1() {
		
		Philosopher1 philosopher_1 = new Philosopher1(test, 1);
		Philosopher1 philosopher_2 = new Philosopher1(test, 2);
		Philosopher1 philosopher_3 = new Philosopher1(test, 3);
		Philosopher1 philosopher_4 = new Philosopher1(test, 4);
		Philosopher1 philosopher_5 = new Philosopher1(test, 5);
		
		for (int i = 0; i < 5; i++) {
			Philosopher1.fork.add(new Fork());
		}
		
		Thread thread_1 = new Thread(philosopher_1, "P" + 1);
		Thread thread_2 = new Thread(philosopher_2, "P" + 2);
		Thread thread_3 = new Thread(philosopher_3, "P" + 3);
		Thread thread_4 = new Thread(philosopher_4, "P" + 4);
		Thread thread_5 = new Thread(philosopher_5, "P" + 5);
		
		thread_1.start();
		thread_2.start();
		thread_3.start();
		thread_4.start();
		thread_5.start();
		
	}
	
	public static void PhilosopherVer2() {
		final Philosopher2[] philosophers = new Philosopher2[5];
        Object[] forks = new Object[philosophers.length];  

        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Object();
        }

        for (int i = 0; i < philosophers.length; i ++) {
        	int j = (i + 1) % 2;
            Object first = forks[(i + j) % forks.length];
            Object second = forks[(i + 1 - j) % forks.length];
            
            philosophers[i] = new Philosopher2(test, i + 1, first, second);

            Thread thread = new Thread(philosophers[i], "P" + (i + 1));
            thread.start();
        }
	}
	
	public static void main(String args[]) {
		PhilosopherVer1();
//		PhilosopherVer2();
		
	}
}
