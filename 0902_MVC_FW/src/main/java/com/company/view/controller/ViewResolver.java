package com.company.view.controller;

public class ViewResolver {
	
	//필드
	public String prefix;	//접두사
	public String suffix;	//접미사
	
	//필드의 setter 메소드만 필요
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	//사용자 정의 메소드
	public String getView(String viewName) {
		return prefix + viewName + suffix;
	}
}
