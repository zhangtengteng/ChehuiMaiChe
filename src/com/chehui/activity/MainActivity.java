package com.chehui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.chehui.manage.comm.RequestTimeOutManager;
import com.chehui.manager.comm.FragmentsManager;
import com.example.myproject.R;
import com.igexin.sdk.PushManager;

public class MainActivity extends FragmentActivity implements OnClickListener {

	private int title;

	protected TextView topTitle;
	protected TextView left;
	protected TextView right;
	// 定义一个变量，来标识是否退出
	private static boolean isExit = false;
	// public static Fragment[] fragments;
	private static FragmentManager fMgr;
	// 当前显示页面
	public static String TAG = "order_check";
	// 用于查找回退栈中的fragment，findFragmentByTag
	public static final String ORDER_CHECK = "order_check";
	public static final String ORDER_COUNT = "order_count";
	public static final String MESSAGE = "message";
	public static final String SET = "set";

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 获取FragmentManager实例
		fMgr = getSupportFragmentManager();
		FragmentsManager.getInstance().setFragmentManager(fMgr);
		initFragment();
		initTitleView(-1, 0, R.string.main_order, 255, -1, 0);
		RequestTimeOutManager.getInstance().init(this);
	}	

	protected void initTitleView(int leftId, int leftAlpha, int topId,
			int topAlpha, int rightId, int rightAlpha) {
		if (left == null) {
			left = (TextView) findViewById(R.id.tvLeft);
		}
		if (right == null) {

			right = (TextView) findViewById(R.id.tvRight);
		}
		if (topTitle == null) {

			topTitle = (TextView) findViewById(R.id.tvTop);
		}

		if (leftId != -1) {
			left.setText(leftId);
		} else {
			left.setText("");
		}
		left.getBackground().setAlpha(leftAlpha);

		if (topId != -1) {
			topTitle.setText(topId);
		} else {
			topTitle.setText("");
		}

		topTitle.getBackground().setAlpha(topAlpha);
		if (rightId != -1) {
			right.setText(rightId);
		} else {
			right.setText("");
		}
		right.getBackground().setAlpha(rightAlpha);

	}

	/**
	 * 初始化首个Fragment
	 */
	private void initFragment() {
		findViewById(R.id.rbOrderCheck).setOnClickListener(this);
		findViewById(R.id.rbOrderCount).setOnClickListener(this);
		findViewById(R.id.rbMessage).setOnClickListener(this);
		findViewById(R.id.rbSet).setOnClickListener(this);

		FragmentsManager.getInstance()
				.addFragment(
						getSupportFragmentManager().findFragmentById(
								R.id.fgOrderCheck), ORDER_CHECK);

		FragmentsManager.getInstance()
				.addFragment(
						getSupportFragmentManager().findFragmentById(
								R.id.fgOrderCount), ORDER_COUNT);

		FragmentsManager.getInstance().addFragment(
				getSupportFragmentManager().findFragmentById(R.id.fgMessage),
				MESSAGE);

		FragmentsManager.getInstance().addFragment(
				getSupportFragmentManager().findFragmentById(R.id.fgSet), SET);

		FragmentsManager.getInstance().changeFragment(ORDER_CHECK);
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.rbOrderCheck:
			TAG = ORDER_CHECK;
			title = R.string.main_order;
			initTitleView(-1, 0, title, 255, -1, 0);
			break;
		case R.id.rbOrderCount:
			TAG = ORDER_COUNT;
			title = R.string.main_my_order;
			initTitleView(-1, 0, title, 255, -1, 0);
			break;
		case R.id.rbMessage:
			TAG = MESSAGE;
			title = R.string.main_rebate;
			initTitleView(-1, 0, title, 255,R.string.set_account_add_bank_top2,0);
			break;
		case R.id.rbSet:
			TAG = SET;
			title = R.string.main_set;
			initTitleView(-1, 0, title, 255, -1, 0);
			break;
		default:
			break;
		}
		
		FragmentsManager.getInstance().changeFragment(TAG);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			// 利用handler延迟发送更改状态信息
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			finish();
			System.exit(0);
		}
	}
}
