package com.company.couplingex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

//@Component("tv")
public class LgTV implements TV {
	@Autowired
	private Speaker speaker;
	private int price;

	public LgTV() {
		System.out.println("===> LgTV() 객체 생성");
	}
//
//	public void setSpeaker(Speaker speaker) {
//		System.out.println("===> setSpeaker() 메서드 호출됨");
//		this.speaker = speaker;
//	}
//
//	public void setPrice(int price) {
//		System.out.println("===> setPrice() 메서드 호출됨.");
//		this.price = price;
//	}

	@Override
	public void powerOn() {
		System.out.println("LgTV---전원켠다");
	}

	@Override
	public void powerOff() {
		System.out.println("LgTV---전원끈다");
	}

	@Override
	public void volumeUp() {
		speaker.volumeUp();
	}

	@Override
	public void volumeDown() {
		speaker.volumeDown();
	}

}
