package com.qlfsoft.wordman;

import org.litepal.LitePalApplication;

import android.content.Context;
import android.view.WindowManager;

public class BaseApplication extends LitePalApplication {

	public static Context sContext;
	public static String book_db_Path;
	public static String dic_db_Path;
	public static String dictionary_db_Path;
	public static int screenWidth;
	public static int screenHeight;
	/**
	 * ��ǰѡ��ĵ��ʱ������
	 */
	public static int curBookId;
	/**
	 * �û��˺�
	 */
	public static String userAccount;
	/**
	 * �ǳ�
	 */
	public static String nickName;
	/**
	 * ǩ��
	 */
	public static String significance;
	/**
	 * ��ѧϰ�ĵ�����
	 */
	public static int haveStudy;
	/**
	 * ���ʱ��ʻ���
	 */
	public static int wordSize;
	/**
	 * �ƻ�ѧϰ������
	 */
	public static int totalDay;
	/**
	 * ʣ��ѧϰ������
	 */
	public static int remainDay;
	/**
	 * ÿ����Ҫѧϰ�ĵ�����
	 */
	public static int dailyWord;
	
	/* ͷ������ */
	public static final String FACEIMAGE_FILE_NAME = "faceImage.jpg";
	@Override
	public void onCreate()
	{
		super.onCreate();
		sContext = getApplicationContext();
		WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
		screenHeight = wm.getDefaultDisplay().getHeight();
	}
	
	public static Context getContext()
	{
		return sContext;
	}
}
