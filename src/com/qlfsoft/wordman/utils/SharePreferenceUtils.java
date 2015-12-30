package com.qlfsoft.wordman.utils;

import com.qlfsoft.wordman.BaseApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtils {

	private Context mContext;
	private SharedPreferences sp;
	private String userBookId = "CURRENT_BOOK_ID";
	private String firstopen = "FIRSTOPEN";//��һ�δ�Ӧ��
	private String userAccount = "USERACCOUNT";//�û��˺�
	private String loginState = "LOGINSTATE";//ʱ���¼״̬
	private String nickname = "NICKNAME";//�ǳ�
	private String significance = "SIGNIFICANCE";//ǩ��
	private String password = "PASSWORD";//����
	private String avatarImage = "AVATARIMAGE";//ͷ��·��
	
	public SharePreferenceUtils()
	{
		mContext = BaseApplication.sContext;
		sp = mContext.getSharedPreferences("com.qlfsoft.wordman", Context.MODE_PRIVATE);
	}
	
	private static SharePreferenceUtils sharedPreferenceUtil;
	
	public synchronized static SharePreferenceUtils getInstnace()
	{
		if(null == sharedPreferenceUtil)
			sharedPreferenceUtil = new SharePreferenceUtils();
		return sharedPreferenceUtil;
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
	
	/**
	 * ��ȡ��¼״̬
	 * @return
	 */
	public boolean getLoginState()
	{
		return sp.getBoolean(loginState, true);
	}
	
	/**
	 * ���õ�¼״̬
	 * @param value
	 */
	public void setLoginState(boolean value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putBoolean(loginState, value);
		ed.commit();
	}
	
	/**
	 * ��ȡ�ǳ�
	 * @return
	 */
	public String getNickName()
	{
		return sp.getString(nickname, "���ʾ�");
	}
	
	/**
	 * �����ǳ�
	 * @param value
	 */
	public void setNickName(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(nickname,value);
		ed.commit();
	}
	
	/**
	 * ����ǩ��
	 * @param value
	 */
	public void setSignificance(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(significance,value);
		ed.commit();
	}
	
	public String getSignificance()
	{
		return sp.getString(significance, "���ж�Ŭ�������̸,��Ҫ����������˶��ټƻ�֮�е���");
	}
	
	public String getPassword()
	{
		return sp.getString(password, "");
	}
	
	/**
	 * ��������
	 * @param value
	 */
	public void setPassword(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(password,value);
		ed.commit();
	}
	
	/**
	 * ���ñ���ͼƬ
	 * @param value
	 */
	public void setAvatarImage(String value)
	{
		SharedPreferences.Editor ed = sp.edit();
		ed.putString(avatarImage,value);
		ed.commit();
	}
	
	public String getAvatarImage()
	{
		return sp.getString(avatarImage, "");
	}
}
