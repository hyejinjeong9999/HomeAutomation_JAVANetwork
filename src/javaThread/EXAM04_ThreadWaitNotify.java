package javaThread;

// 1초마다 자신의 이름을 도스창에 출력하는 Thread를 만듬
//thread의 순서는 scheduler에 의해 일반적으로 제어 못하지만
//특수한 thread를 이용해서 순서를 제어할 수 있음
// wait(), notify(), notifyAll() -> 반드시 Critical section에서 사용되어야함
//critical section -> 동기화 코드가 적용된 부분
class ReapeatRunnable implements Runnable {
	private Shared obj;

	public ReapeatRunnable(Shared obj) {
		this.obj = obj;
	}

	@Override
	public void run() {
		obj.printNum();
	}
}

class Shared {
	// thread가 공용으로 사용하는 데이터와 method가 존재
	public synchronized void printNum() {
		for (int i = 1; i < 11; i++) {
			System.out.println(i + " 번 째 반복, Thread 명 : " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);
				notify();		//wait으로 block되어있는 thread를 깨우는 method
				wait();		//자기가 가진 monitor를 반납하고 wait block상태가 됨
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
} // end of class Shared

public class EXAM04_ThreadWaitNotify {

	public static void main(String[] args) {
		Shared obj = new Shared();

		ReapeatRunnable r1 = new ReapeatRunnable(obj);
		ReapeatRunnable r2 = new ReapeatRunnable(obj);

		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);

		// 객체가 가지는 method를 호출할때 blocking method를 이용함
		// blocking method : method의 실행이 끝나고 리턴값이 나온 후 다음 코드를 실행함, 즉 순차처리
		// start()는 non-blocking method이다
		t1.start();
		t2.start();
	}
}
