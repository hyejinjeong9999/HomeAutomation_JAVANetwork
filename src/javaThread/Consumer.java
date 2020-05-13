package javaThread;

import java.util.LinkedList;

public class Consumer {

	public static void main(String[] args) {
		Share obj = new Share();

		ProdecerRunnable pr = new ProdecerRunnable(obj);
		ConsumerRunnable cr1 = new ConsumerRunnable(obj);
		ConsumerRunnable cr2 = new ConsumerRunnable(obj);
		ConsumerRunnable cr3 = new ConsumerRunnable(obj);

		Thread p = new Thread(pr);
		Thread c1 = new Thread(cr1);
		Thread c2 = new Thread(cr2);
		Thread c3 = new Thread(cr3);

		p.start();
		c1.start();
		c2.start();
		c3.start();
	}
}

class ConsumerRunnable implements Runnable {
	private Share share;

	public ConsumerRunnable(Share share) {
		super();
		this.share = share;
	}

	@Override
	public void run() {
		share.consume();
	}
}

class ProdecerRunnable implements Runnable {
	private Share share;

	public ProdecerRunnable(Share share) {
		super();
		this.share = share;
	}

	@Override
	public void run() {
		share.produce();
	}
}

class Share {
	int number;

	public void produce() {
		while (true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			number++;
			System.out.println(number);
		}
	}

	public synchronized void consume() {
		while (true) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			number--;
			System.out.println(number);
		}
	}
}