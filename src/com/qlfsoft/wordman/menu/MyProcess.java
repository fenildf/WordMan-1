package com.qlfsoft.wordman.menu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.qlfsoft.wordman.BaseApplication;
import com.qlfsoft.wordman.IPlanObserver;
import com.qlfsoft.wordman.R;
import com.qlfsoft.wordman.model.UserWords;
import com.qlfsoft.wordman.ui.BaseActivity;
import com.qlfsoft.wordman.ui.ProcessDailyDiagram;
import com.qlfsoft.wordman.ui.ProcessTotalDiagram;
import com.qlfsoft.wordman.utils.ActivityForResultUtil;
import com.qlfsoft.wordman.widget.FlipperLayout.OnOpenListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyProcess implements IPlanObserver {

	private LinearLayout ll_top;
	private ImageButton ib_menu;
	private TextView tv_needStudy;
	private TextView tv_haveStudy;
	private TextView tv_needReview;
	private TextView tv_haveReview;
	private Button btn_todayDiagram;
	private TextView tv_wordSize;
	private TextView tv_wordStudy;
	private TextView tv_wordControl;
	private Button btn_totalDiagram;
	private BarChart bc_barChart;
	private LinearLayout ll_main;
	
	private Context mContext;
	private Activity mActivity;
	private View mProcess;
	private OnOpenListener mOnOpenListener;
	public MyProcess(Context context, Activity activity) {

		mContext = context;
		mActivity = activity;
		mProcess = LayoutInflater.from(mContext).inflate(R.layout.activity_process, null);
		initView();
		initData();
		setListener();
	}

	private void initView() {
		ll_top = (LinearLayout) mProcess.findViewById(R.id.process_top_layout);
		ib_menu = (ImageButton) mProcess.findViewById(R.id.process_menu);
		tv_needStudy = (TextView) mProcess.findViewById(R.id.process_tv_needStudy);
		tv_haveStudy = (TextView) mProcess.findViewById(R.id.process_tv_haveStudy);
		tv_needReview = (TextView) mProcess.findViewById(R.id.process_tv_needReview);
		tv_haveReview = (TextView) mProcess.findViewById(R.id.process_tv_haveReview);
		btn_todayDiagram = (Button) mProcess.findViewById(R.id.process_today_diagram);
		tv_wordSize = (TextView) mProcess.findViewById(R.id.process_tv_wordSize);
		tv_wordStudy = (TextView) mProcess.findViewById(R.id.process_tv_wordStudy);
		tv_wordControl = (TextView) mProcess.findViewById(R.id.process_tv_wordControl);
		btn_totalDiagram = (Button) mProcess.findViewById(R.id.process_total_diagram);
		bc_barChart = (BarChart) mProcess.findViewById(R.id.process_lineChart);
		ll_main = (LinearLayout) mProcess.findViewById(R.id.process_ll_main);
		ll_main.setOnTouchListener(new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}

	private void initData() {
		final SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		final String today = dateformat.format(new Date());
		final List<UserWords> todayWords = DataSupport.where("account=? and bookId=? and date([date])=date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today).find(UserWords.class);
		List<UserWords> beforeWords = DataSupport.where("account=? and bookId=? and date([date])<>date(?) and repeat<=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,"4").order("upateDate asc").order("repeat desc").find(UserWords.class);
		final int beforeSize = beforeWords.size();//前面还未学习完全的单词数
		final int reviewSize = beforeSize / 3 * 2;//今日需要复习的单词数
		final List<UserWords> reviewedWords = DataSupport.where("account=? and bookId=? and date(upateDate)=date(?) and date([date])<>date(?)",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),today,today).find(UserWords.class);
		tv_needStudy.setText(String.valueOf(BaseApplication.dailyWord));
		tv_haveStudy.setText(String.valueOf(todayWords.size()));
		tv_needReview.setText(String.valueOf(reviewSize));
		tv_haveReview.setText(String.valueOf(reviewedWords.size()));
		tv_wordSize.setText(String.valueOf(BaseApplication.wordSize));
		tv_wordStudy.setText(String.valueOf(BaseApplication.haveStudy));
		final List<UserWords> controlWords = DataSupport.where("account=? and bookId=? and repeat=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),"4").find(UserWords.class);
		tv_wordControl.setText(String.valueOf(controlWords.size()));
		
		bc_barChart.setDrawBarShadow(false);
		bc_barChart.setDrawValueAboveBar(true);
		bc_barChart.setDescription("");
		bc_barChart.setMaxVisibleValueCount(7);
		bc_barChart.setPinchZoom(false);
		bc_barChart.setDrawGridBackground(false);
		
		XAxis xAxis = bc_barChart.getXAxis();
		xAxis.setPosition(XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);
		xAxis.setSpaceBetweenLabels(2);
		xAxis.setTextSize(16);
		
		YAxis leftAxis = bc_barChart.getAxisLeft();
		leftAxis.setLabelCount(8, false);
		leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART);
		leftAxis.setSpaceTop(15f);
		leftAxis.setTextSize(16);
		YAxis rightAxis = bc_barChart.getAxisRight();
		rightAxis.setEnabled(false);
		
		Legend l = bc_barChart.getLegend();
		l.setPosition(LegendPosition.BELOW_CHART_LEFT);
		l.setForm(LegendForm.SQUARE);
		l.setFormSize(9f);
		l.setTextSize(13f);
		l.setXEntrySpace(4f);
	
		setChartData();
		
		btn_todayDiagram.setOnClickListener(new OnClickListener(){
			public void onClick(View view)
			{
				Intent intent = new Intent(mContext,ProcessDailyDiagram.class);
				intent.putExtra("HAVESTUDY", todayWords.size());
				intent.putExtra("NEEDSTUDY", BaseApplication.dailyWord);
				intent.putExtra("NEEDREVIEW", reviewSize);
				intent.putExtra("HAVEREVIEW", reviewedWords.size());
				mActivity.startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_DAILYDIAGRAM);
			}
		});
		
		btn_totalDiagram.setOnClickListener(new OnClickListener(){
			public void onClick(View view)
			{
				Intent intent = new Intent(mContext,ProcessTotalDiagram.class);
				intent.putExtra("WORDSIZE", BaseApplication.wordSize);
				intent.putExtra("WORDSTUDY", BaseApplication.haveStudy);
				intent.putExtra("WORDCONTROL", controlWords.size());
				mActivity.startActivityForResult(intent, ActivityForResultUtil.REQUESTCODE_TOTALDIAGRAM);
			}
		});
	}

	private void setChartData() {
		ArrayList<String> xVals = new ArrayList<String>();
		ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 0; i < 7; i++)
		{
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, -i);
			String date = dateformat.format(c.getTime());
			int count = DataSupport.where("account=? and bookId=? and date=?",BaseApplication.userAccount,String.valueOf(BaseApplication.curBookId),date).count(UserWords.class);
			xVals.add(date);
			yVals.add(new BarEntry(count,i));
		}
		
		BarDataSet dataset = new BarDataSet(yVals,"七日学习情况");
		dataset.setBarSpacePercent(35f);
		ArrayList<IBarDataSet> datasets = new ArrayList<IBarDataSet>();
		datasets.add(dataset);
		
		BarData data = new BarData(xVals,datasets);
		data.setValueTextSize(13f);
		bc_barChart.setData(data);
		
	}

	private void setListener() {
		ib_menu.setOnClickListener(new OnClickListener(){
			public void onClick(View v)
			{
				mOnOpenListener.open();
			}
		});
		
	}

	@Override
	public void planUpdate() {
		initData();
		
	}
	
	public View getView() {
		return mProcess;
	}

	public void setOnOpenListener(OnOpenListener onOpenListener) {
		mOnOpenListener = onOpenListener;
	}

}
