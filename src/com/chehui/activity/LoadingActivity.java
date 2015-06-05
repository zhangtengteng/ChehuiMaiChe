package com.chehui.activity;

import java.util.ArrayList;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.chehui.adapter.MyPagerAdapter;
import com.chehui.manager.comm.ActivityManager;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class LoadingActivity extends BaseActivity {
	private String userName;
	private String passWord;
	private final int MESSAGESUCCESS = 0;
	private LocalActivityManager manager;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				ActivityManager.getInstance().startNextActivity(
						LoginActivity.class);
				LoadingActivity.this.finish();
				break;

			default:
				break;
			}
		}
	};
	private ViewPager vp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		initViwes();
	}

	private void initViwes() {
		vp = (ViewPager) findViewById(R.id.vp);
		ArrayList<View> list = new ArrayList<View>();
		Intent intent1 = new Intent(this, ViewPager1Activity.class);
		list.add(getViews("0", intent1));
		Intent intent2 = new Intent(this, ViewPager2Activity.class);
		list.add(getViews("1", intent2));
		Intent intent3 = new Intent(this, ViewPager3Activity.class);
		list.add(getViews("2", intent3));
		vp.setAdapter(new MyPagerAdapter(list));
	}

	/**
	 * 通过activity获取视图
	 * 
	 * @param id
	 * @param intent
	 * @return
	 */
	private View getViews(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

}
