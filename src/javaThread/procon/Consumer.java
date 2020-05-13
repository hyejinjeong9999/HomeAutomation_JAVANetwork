package javaThread.procon;

public class Consumer implements Runnable {
	private SharedObject obj;
	
	public Consumer(SharedObject obj) {
		super();
		this.obj = obj;
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " - Consumer 시작");
		while(true) {
			if(Thread.currentThread().isInterrupted()) {
				break;
			}
			System.out.println(Thread.currentThread().getName() + " 가 소비한 값 : " + obj.pop());
		}
		System.out.println(Thread.currentThread().getName() + " 가 종료됨");
	}

}

