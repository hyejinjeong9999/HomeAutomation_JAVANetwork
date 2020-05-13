package javaThread.procon;

import java.util.LinkedList;

//공유객체는 여러 개가 필요 없고,
//이 1개의 공유객체를 여러 개의 thread가 공유해서 사용
public class SharedObject {
	
	private static final Object MONITOR = new Object();
	private static SharedObject obj = new SharedObject();
	private LinkedList<String> list = new LinkedList<String>();
	
	//singleton의 기본적은 생성자가 private으로 지정
	//그래야 class가 외부에서 생성자 생성을 막을 수 있음
	private SharedObject() {
		
	}
	
	public static SharedObject getInstance() {
		return obj;
	}
	
	//thread에 의해 공용으로 사용되는 business method가 필요
	//1. 생상자 : 자료구조에 데이터를 집어 넣는 일
	//2. 소비자 : 자료구조에서 데이터를 빼내서 화면에 출력
	
	//생산자
	public void put(String s) {
		synchronized (MONITOR) {
			list.addLast(s);
			MONITOR.notify();
		}
	}
	
	//소비자
	public String pop() {
		synchronized (MONITOR) {
			//list에 데이터가 있어야 사용가능
			if(list.isEmpty()) {
				try {
					MONITOR.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}else {return list.removeFirst();}
		}
	}
}
