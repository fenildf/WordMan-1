package com.qlfsoft.wordman.utils;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

	private Context mContext;
	private SharedPreferences sp;
	private String userBookId = "CURRENT_BOOK_ID";
	private String firstopen = "FIRSTOPEN";
	private String userAccount = "USERACCOUNT";
	
	public SharePreferenceUtils()
	{
		mContext = BaseApplication.sContext;
		sp = mContext.getSharedPreferences("com.qlfsoft.wordman", Context.MODE_PRIVATE);
	}
	
	/**
	 * ��ǰ�û��Ƿ��Ѿ�ѡ���˱��е��ʱ�
	 * @return
	 */
	public boolean hasSelBook()
	{
		int bookId = sp.getInt(userBookId, 0);
		return bookId != 0;
	}
	
	public boolean getFirstOpen()
	{
		boolean flag = sp.getBoolean(firstopen, true);
		return flag;
	}
	
	public void setFirstOpen()
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putBoolean(firstopen, false);
		ed.commit();
	}
	
	
	/**
	 * ����ѡ�еĵ��ʱ����
	 * @param bookId
	 */
	public void setBookId(int bookId)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putInt(userBookId, bookId);
		ed.commit();
	}
	
	/**
	 * ��ȡ��ǰ�û����ڱ��еĵ��ʱ����
	 * @return
	 */
	public int getSelBookId()
	{
		return sp.getInt(userBookId, 0);
	}
	
	public String getUserAccount()
	{
		String account = sp.getString(userAccount, "");
		return account;
	}
	
	public void setUserAccount(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(userAccount, value);
		ed.commit();
	}
}
