package com.qlfsoft.wordman.model;

import org.litepal.crud.DataSupport;
/**
 * �û���Ϣ
 * @author hyn
 *
 */
public class UserModel extends DataSupport {
	private String nickname;//�ǳ�
	private String account;//�˺�
	private String password;//����
	private String avatar;//ͷ��
	private int selBook;//ѡ��ĵ��ʱ�
	private int haveStudy;//�Ѿ�ѧϰ�ĵ�����
	private int totalDay;//�ƻ�ѧϰ������
	private int remainDay;//ʣ��ѧϰ������
	private int dailyword;//ÿ��ѧϰ������
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getSelBook() {
		return selBook;
	}
	public void setSelBook(int selBook) {
		this.selBook = selBook;
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
