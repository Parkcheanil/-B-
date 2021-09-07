package com.company.couplingex;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class UserTV {

	public static void main(String[] args) {
		//다형성을 이용한 느슨한 결합 이용.
//		BeanFactory factory = new BeanFactory();
//		TV tv = (TV) factory.getBean(args[0]);
//		tv.powerOn();
//		tv.volumeUp();
//		tv.volumeDown();
//		tv.powerOff();
		
		//1. 스프링 컨테이너 구동
		AbstractApplicationContext factory = 
				new GenericXmlApplicationContext("applicationContext.xml");
		
		//2. 스프링 컨테이너로부터 필요한 객체를 요청(Lookup)한다.
		TV tv = (TV) factory.getBean("tv");
		tv.powerOn();
		tv.volumeUp();
		tv.volumeDown();
		tv.powerOff();
		
		//3. 스프링 컨테이너를 종료한다.
		factory.close();
	}

}
