package com.company.couplingex;

import org.springframework.stereotype.Component;

public class AppleSpeaker implements Speaker{
	private int vol = 15;
	
	public AppleSpeaker() {
		System.out.println("===> appleSpeaker 객체 생성");
	}
	public void volumeUp() {
		System.out.println("appleSpeaker----소리 올린다.(현재볼륨 : "+ (vol+=1) +")");
	}
	public void volumeDown() {
		System.out.println("appleSpeaker----소리 내린.(현재볼륨 : "+ (vol-=1) +")");
	}
}
