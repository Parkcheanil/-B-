package com.company.couplingex;

import org.springframework.stereotype.Component;

public class SonySpeaker implements Speaker{
	private int vol = 15;
	
	public SonySpeaker() {
		System.out.println("===> SonySpeaker 객체 생성");
	}
	public void volumeUp() {
		System.out.println("SonySpeaker----소리 올린다.(현재볼륨 : "+ (vol+=1) +")");
	}
	public void volumeDown() {
		System.out.println("SonySpeaker----소리 내린.(현재볼륨 : "+ (vol-=1) +")");
	}
}
