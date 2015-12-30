package com.qlfsoft.wordman.menu;

import java.io.File;

import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.ui.LoginActivity;
import com.qlfsoft.wordman.utils.FileUtils;
import com.qlfsoft.wordman.utils.SharePreferenceUtils;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfo {

	private Context mContext;
	private Activity mActivity;
	private View mUserInfo;
	private OnOpenListener mOnOpenListener;
	
	private TextView tv_title;
	private ImageButton btn_back;
	private Button btn_submit;
	private ImageButton ib_avatar;
	private Button btn_avatarchg;
	private TextView tv_nickname;
	private TextView tv_account;
	private TextView tv_significances;
	private LinearLayout ll_orginPwd;
	private TextView tv_orginPwd;
	private LinearLayout ll_newPwd;
	private LinearLayout ll_rePwd;
	private TextView tv_newPwd;
	private TextView tv_rePwd;
	private Button btn_logout;
	private boolean modify_flag;
	
	private String[] items = new String[] { "ѡ�񱾵�ͼƬ", "����" };
	/* ͷ������ */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* ������ */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	
	public UserInfo(Context context,Activity activity)
	{
		mContext = context;
		mActivity = activity;
		mUserInfo = LayoutInflater.from(context).inflate(R.layout.about_activity, null);
		findViewById();
		setListener();
		init();
	}

	private void init() {
		SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
		tv_nickname.setText(spu.getNickName());
		tv_account.setText(spu.getUserAccount());
		tv_significances.setText(spu.getSignificance());
		String imgUri = spu.getAvatarImage();
		if(imgUri.equals(""))
		{
			ib_avatar.setImageResource(R.drawable.head);
		}else{
			
		}
			
	}

	private void setListener() {
		btn_back.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(null != mOnOpenListener)
					mOnOpenListener.open();
				
			}
			
		});
		btn_submit.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				if(btn_submit.getText().toString().equals(mContext.getResources().getString(R.string.about_btn_modify_str)))
				{
					//�����޸�
					btn_submit.setText(R.string.about_btn_complete_str);
					ll_orginPwd.setVisibility(View.VISIBLE);
					ll_newPwd.setVisibility(View.VISIBLE);
					ll_rePwd.setVisibility(View.VISIBLE);
				}else
				{
					//�޸����
					
					
					
					btn_submit.setText(R.string.about_btn_modify_str);
					ll_orginPwd.setVisibility(View.GONE);
					ll_newPwd.setVisibility(View.GONE);
					ll_rePwd.setVisibility(View.GONE);
				}
				
			}
			
		});
		btn_avatarchg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		btn_logout.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				SharePreferenceUtils spu = SharePreferenceUtils.getInstnace();
				if(!(null == spu.getUserAccount() || "".equals(spu.getUserAccount())))
				{
					spu.setLoginState(false);
				}
				Intent intent = new Intent(mContext,LoginActivity.class);
				mActivity.startActivity(intent);
				mActivity.finish();
				
			}
			
		});
	}

	private void findViewById() {
		tv_title = (TextView) mUserInfo.findViewById(R.id.about_title);
		btn_back = (ImageButton) mUserInfo.findViewById(R.id.about_back);
		btn_submit = (Button) mUserInfo.findViewById(R.id.about_submit);
		ib_avatar = (ImageButton) mUserInfo.findViewById(R.id.about_avatar);
		btn_avatarchg = (Button) mUserInfo.findViewById(R.id.about_avatar_change);
		tv_nickname = (TextView) mUserInfo.findViewById(R.id.about_tv_nickname);
		tv_account = (TextView) mUserInfo.findViewById(R.id.about_tv_account);
		tv_significances = (TextView) mUserInfo.findViewById(R.id.about_tv_significances);
		ll_orginPwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_orginpwd);
		tv_orginPwd = (TextView) mUserInfo.findViewById(R.id.about_tv_orginpwd);
		ll_newPwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_newpwd);
		tv_newPwd = (TextView) mUserInfo.findViewById(R.id.about_tv_newpwd);
		ll_rePwd = (LinearLayout) mUserInfo.findViewById(R.id.about_ll_repwd);
		tv_rePwd = (TextView) mUserInfo.findViewById(R.id.about_tv_repwd);
		btn_logout = (Button) mUserInfo.findViewById(R.id.about_btn_logout);
	}
	
	public View getView() {
		return mUserInfo;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}
	
	/**
	 * ��ʾѡ��Ի���
	 */
	private void showDialog() {

		new AlertDialog.Builder(mContext)
				.setTitle("����ͷ��")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // �����ļ�����
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							mActivity.startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// �жϴ洢���Ƿ�����ã����ý��д洢
							if (FileUtils.ExistSDCard()) {

								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}

							mActivity.startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	/*
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//����벻����ȡ��ʱ��
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory()
									+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(MainActivity.this, "δ�ҵ��洢�����޷��洢��Ƭ��",
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
*/
	/**
	 * �ü�ͼƬ����ʵ��
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		mActivity.startActivityForResult(intent, 2);
	}

	/**
	 * ����ü�֮���ͼƬ����
	 * 
	 * @param picdata
	 */
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			ib_avatar.setImageDrawable(drawable);
		}
	}
}
