package com.qlfsoft.wordman.menu;

import com.qlfsoft.wordman.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Desktop {

	private Context mContext;
	private Activity mActivity;
	
	/*
	 * ��ǰ�����View
	 */
	private View mDesktop;
	
	private LinearLayout mWallpaper;
	private RelativeLayout mTopLayout;
	private ImageView mAvatar;
	private TextView mName;
	private TextView mSig;
	private ListView mDisplay;
	private ImageView mSetUp;
	
	public interface onChangeViewListener{
		public abstract void onChangeView(int arg0);
	}
	//private DesktopAdapter mAdapter;
	
	private onChangeViewListener mOnChangeViewListener;
	
	public Desktop(Context context,Activity activity)
	{
		mContext = context;
		mActivity = activity;
		mDesktop = LayoutInflater.from(context).inflate(R.layout.desktop, null);
		findViewById();
		setListener();
		init();
	}
	
	private void findViewById()
	{
		mWallpaper = (LinearLayout) mDesktop.findViewById(R.id.desktop_wallpager);
		mTopLayout = (RelativeLayout) mDesktop.findViewById(R.id.desktop_top_layout);
		mAvatar = (ImageView) mDesktop.findViewById(R.id.desktop_avatar);
		mName = (TextView) mDesktop.findViewById(R.id.desktop_name);
		mSig = (TextView) mDesktop.findViewById(R.id.desktop_sig);
		mDisplay = (ListView) mDesktop.findViewById(R.id.desktop_display);
		mSetUp = (ImageView) mDesktop.findViewById(R.id.desktop_set_up);
		
	}
	
	private void setListener()
	{
		
	}
	
	private void init()
	{
		
	}
	
	public class DesktopAdapter extends BaseAdapter{

		private Context dContext;
		private String[] mName = {"��ҳ","�ҵļƻ�","����","�ʿ�","����������"};
		private int[] mIcon = {R.drawable.sidebar_icon_dynamic,R.drawable.sidebar_icon_myplan,R.drawable.sidebar_icon_process,
				R.drawable.sidebar_icon_library,R.drawable.sidebar_icon_test};
		private int[] mIconPressed = {R.drawable.sidebar_icon_dynamic_pressed,R.drawable.sidebar_icon_myplan_pressed,R.drawable.sidebar_icon_process_pressed,
				R.drawable.sidebar_icon_library_pressed,R.drawable.sidebar_icon_test_pressed};
		
		private int mChoose = 0;
		
		public DesktopAdapter(Context context)
		{
			dContext = context;
		}
		
		@Override
		public int getCount() {
			
			return mName.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		public void setChoose(int choose)
		{
			mChoose = choose;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			if(convertView == null)
			{
				convertView = LayoutInflater.from(dContext).inflate(R.layout.desktop, null);
				holder = new ViewHolder();
				holder.layout = (LinearLayout) convertView.findViewById(R.id.desktop_item_layout);
				holder.icon = (ImageView) convertView.findViewById(R.id.desktop_item_icon);
				holder.name = (TextView) convertView.findViewById(R.id.desktop_item_name);
				convertView.setTag(holder);
			}else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.name.setText(mName[position]);
			if(position == mChoose)
			{
				holder.name.setTextColor(Color.parseColor("#ffffffff"));
				holder.icon.setImageResource(mIconPressed[position]);
				holder.layout.setBackgroundColor(Color.parseColor("#20000000"));
			}else
			{
				holder.name.setTextColor(Color.parseColor("#7fffffff"));
				holder.icon.setImageResource(mIcon[position]);
				holder.layout.setBackgroundColor(Color.parseColor("#00000000"));
			}
			
			
			return convertView;
		}
		
		class ViewHolder{
			LinearLayout layout;
			ImageView icon;
			TextView name;
		}
		
	}
	
}
