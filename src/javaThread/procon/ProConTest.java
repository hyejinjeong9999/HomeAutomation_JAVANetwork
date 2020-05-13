package javaThread.procon;

public class ProConTest {

	public static void main(String[] args) {
		SharedObject obj =SharedObject.getInstance();
		
		Thread producer = new Thread(new Producer(obj)); 
		Thread consumer1 = new Thread(new Consumer(obj)); 
		Thread consumer2= new Thread(new Consumer(obj)); 
		Thread consumer3 = new Thread(new Consumer(obj));
		
		consumer1.start();
		consumer2.start();
		consumer3.start();
		
		//consumer1.interrupt();
		producer.start();
		
		try {
			Thread.sleep(2);		//main thread를 잠깐 쉼
			producer.interrupt();
			Thread.sleep(2);		//main thread를 잠깐 쉼
			consumer1.interrupt();
			consumer2.interrupt();
			consumer3.interrupt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
