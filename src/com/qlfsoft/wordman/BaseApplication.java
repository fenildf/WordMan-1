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
	public static int curBookId;//��ǰѡ��ĵ��ʱ������
	public static String userAccount;//�û��˺�
	public static String nickName;//�ǳ�
	public static String significance;//ǩ��
	public static int haveStudy;//�Ѿ�ѧϰ�ĵ�����
	public static int wordSize;//���ʱ��ʻ���
	public static int totalDay;////�ƻ�ѧϰ������
	public static int remainDay;//ʣ��ѧϰ������
	public static int dailyWord;//ÿ����Ҫѧϰ�ĵ�����
	
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
