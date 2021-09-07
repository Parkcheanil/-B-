package com.company.couplingex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("tv")
public class SamsungTV implements TV{
	@Autowired
	//SonySpeaker의 멤버변수 선언
	private Speaker speaker;
	private int price;
	
	public SamsungTV() {
		System.out.println("===> SamsungTV(1) 객체 생성");
	}
	// setter 의존성 주입을 위한 메서드
	public void setSpeaker(Speaker speaker) {
		System.out.println("===> setSpeaker() 메서드 호출됨");
		this.speaker = speaker;
	}

	public void setPrice(int price) {
		System.out.println("===> setPrice() 메서드 호출됨.");
		this.price = price;
	}
//
//	//SonySpeaker생성자
//	public SamsungTV(Speaker speaker) {
//		System.out.println("===> SamsungTV(2) 객체 생성");
//		this.speaker = speaker;
//	}
//	public SamsungTV(Speaker speaker, int price) {
//		System.out.println("===> SamsungTV(3) 객체 생성");
//		this.speaker = speaker;
//		this.price = price;
//	}

	@Override
	public void powerOn() {
		System.out.println("SamsungTV---전원켠다.(가격: "+ price +")");
	}

	@Override
	public void powerOff() {
		System.out.println("SamsungTV---전원끈다");
	}

	@Override
	public void volumeUp() {
//		speaker = new SonySpeaker();
		speaker.volumeUp();
//		System.out.println("SamsungTV---소리 올린다");		
	}

	@Override
	public void volumeDown() {
//		speaker = new SonySpeaker();
		speaker.volumeDown();
//		System.out.println("SamsungTV---소리 내린다");		
	}

}
