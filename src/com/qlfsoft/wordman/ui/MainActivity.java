package com.qlfsoft.wordman.ui;

import java.io.File;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.R.layout;
import com.qlfsoft.wordman.R.menu;
import com.qlfsoft.wordman.menu.Desktop;
import com.qlfsoft.wordman.menu.Desktop.onChangeViewListener;
import com.qlfsoft.wordman.menu.Home;
import com.qlfsoft.wordman.utils.ActivityForResultUtil;
import com.qlfsoft.wordman.utils.ViewUtil;
import com.qlfsoft.wordman.widget.FlipperLayout;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnOpenListener {

	/**
	 * ��ǰ��ʾ���ݵ�����(�̳���ViewGroup)
	 */
	private FlipperLayout mRoot;
	
	private Desktop mDesktop;
	private Home mHome;
	
	/*
	 * ��ǰ��ʾ��View�ı��
	 */
	private int mViewPosition;
	/*
	 * �˳����
	 */
	private static final int INTERVAL = 2000;
	/**
	 * �˳�ʱ��
	 */
	private long mExitTime;
	
	private static Activity mInstance;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * ��������,������ȫ����С
		 */
		mRoot = new FlipperLayout(this);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		mRoot.setLayoutParams(params);
		mDesktop = new Desktop(this,this);
		mHome = new Home(this,this);
		mRoot.addView(mDesktop.getView(),params);
		mRoot.addView(mHome.getView(),params);
		/**
		 * �����˵������������ҳ����,����ӵ�������,���ڳ�ʼ��ʾ
		 */
		setContentView(mRoot);
		setListener();
		mInstance=this;
	}

	/**
	 * UI�¼�����
	 */
	private void setListener() {
		mHome.setOnOpenListener(this);
		mDesktop.setOnChangeViewListener(new onChangeViewListener(){

			@Override
			public void onChangeView(int arg0) {
				mViewPosition = arg0;
				switch(arg0)
				{
				case ViewUtil.HOME:
					mRoot.close(mHome.getView());
					break;
				default:
						break;
				}
				
			}
			
		});
		}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * ���ؼ�����
	 */
	public void onBackPressed() {
		exit();
	}

	/**
	 * �ж����η���ʱ����,С���������˳�����
	 */
	private void exit() {
		if (System.currentTimeMillis() - mExitTime > INTERVAL) {
			Toast.makeText(this, "�ٰ�һ�η��ؼ�,��ֱ���˳�����", Toast.LENGTH_SHORT).show();
			mExitTime = System.currentTimeMillis();
		} else {
			finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		}
	}

	public void open() {
		if (mRoot.getScreenState() == FlipperLayout.SCREEN_STATE_CLOSE) {
			mRoot.open();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {
		/**
		 * �л�ǽֽʱ�ص��˷���,֪ͨ�˵�������û������޸�ǽֽ
		 */
		case ActivityForResultUtil.REQUESTCODE_CHANGEWALLPAGER:
			if (resultCode == RESULT_OK) {
				//mDesktop.setWallpager();
			}
			break;
		/**
		 * �޸�ǩ��ʱ�ص��˷���,֪ͨ�˵�������û������޸�ǩ��
		 */
		case ActivityForResultUtil.REQUESTCODE_EDITSIGNATURE:
			if (resultCode == RESULT_OK) {
				String arg0 = data.getStringExtra("signature");
				//mDesktop.setSignature(arg0);
			}
			break;
		}
	}
}
