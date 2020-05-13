package javaThread;

//공유 객체를 만들기 위한 class
//일반적으로 singleton으로 작성
class SharedObject{
	//thread가 공유해서 사용하는 공유객체는
	//thread가 사용하는 데이터와 로직을 포함함
	private int number;		//thread에 의해서 공유되는 field value
	private Object monitor = new Object();

	public int getNumber() {
		return number;
	}
	
	//1번째 해결방법은 method 호출을 순차적으로 처리
	//각 Thread가 가지고 있는 공용객체의 method호출을 synchronized 하여 순차적으로 호출하게끔 처리
	//method자체가 동기화 처리가 되서 프로그래밍 하기는 쉬움
	//단, 해당 method의 실행이 오래걸리면 성능저하의 원인이됨
	//전체 method를 동기화하는 것이 아닌 필요한 부분만 동기화하도록 한다.
	public void setNumber(int number) {
		System.out.println("소리 없는 아우성");
		synchronized (monitor) {
			this.number = number;
			try {
				//현재 사용하는 공유객체를 1초간 재운다
				Thread.sleep(2000);
				System.out.println("현재 number의 값 : " + getNumber());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("무슨소리?");
	}
	
}// end of Class SharedObject

class NumberRunnable implements Runnable{
	private SharedObject obj;
	private int number;
	
	NumberRunnable (){
		
	}

	public NumberRunnable(SharedObject obj, int number) {
		this.obj = obj;
		this.number = number;
	}

	@Override
	public void run() {
		obj.setNumber(number);
	}
	
}
public class EXAM03_ThreadSynch {
	
	
	public static void main(String[] args) {
		//thread에 의해서 공유되는 공유객체를 1개 생성
		//일반적인 공유객체는 class로부터 객체가 딱 1개만 생성되는 형태를 만듬
		//single tone pattern
		//thread는 로직처리를 공유객체를 이용해서 로직처리를 하고
		//데이터 처리 역시 공유객체를 통해서 처리
		SharedObject obj = new SharedObject();
		
		//thread를 생성하기 위해서 runnable interface를 구현한 객체를 만들기 위한 class 정의
		//
		NumberRunnable r1 = new NumberRunnable(obj, 100);
		NumberRunnable r2 = new NumberRunnable(obj, 200);
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(r2);
		
		t1.start();
		t2.start();
	}
}






































