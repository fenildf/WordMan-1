package com.qlfsoft.wordman.model;

import java.util.Date;

import org.litepal.crud.DataSupport;

/**
 * @author hyn
 *
 */
public class UserWords extends DataSupport{
	private int bookId;//���ʱ����
	private int wordId;//�������
	private int orderNo;//��������
	private String account;//�˺�
	private Date date;//�״�ѧϰ����
	private int repeat;//�ظ�����
	private Date upateDate;//��������
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getRepeat() {
		return repeat;
	}
	public void setRepeat(int repeat) {
		this.repeat = repeat;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getWordId() {
		return wordId;
	}
	public void setWordId(int wordId) {
		this.wordId = wordId;
	}
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
	public Date getUpateDate() {
		return upateDate;
	}
	public void setUpateDate(Date upateDate) {
		this.upateDate = upateDate;
	}
	
	
}
