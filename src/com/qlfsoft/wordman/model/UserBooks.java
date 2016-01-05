package com.qlfsoft.wordman.model;

import org.litepal.crud.DataSupport;

public class UserBooks extends DataSupport{
	private String account;//�˺�
	private int bookId;//���ʱ����
	private boolean inUser;//�Ƿ���ʹ����
	private int haveStudy;//�Ѿ�ѧϰ�ĵ�����
	private int totalDay;//�ƻ�ѧϰ������
	private int remainDay;//ʣ��ѧϰ������
	private int dailyword;//ÿ��ѧϰ������
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public boolean isInUser() {
		return inUser;
	}
	public void setInUser(boolean inUser) {
		this.inUser = inUser;
	}
	public int getHaveStudy() {
		return haveStudy;
	}
	public void setHaveStudy(int haveStudy) {
		this.haveStudy = haveStudy;
	}
	public int getTotalDay() {
		return totalDay;
	}
	public void setTotalDay(int totalDay) {
		this.totalDay = totalDay;
	}
	public int getRemainDay() {
		return remainDay;
	}
	public void setRemainDay(int remainDay) {
		this.remainDay = remainDay;
	}
	public int getDailyword() {
		return dailyword;
	}
	public void setDailyword(int dailyword) {
		this.dailyword = dailyword;
	}
}
