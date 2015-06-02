package com.chehui.activity;

/****
 * 添加银行卡页面2 （卡类型 手机号）
 */

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityManagerAccountAddBank2 extends BaseActivity implements
		OnClickListener {
	private Button btnNext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account_add_bank2);
		initTitleView(-1, 255, R.string.set_account_writer_bank_top, 255, -1, 0);
		init();
	}

	private void init() {
		btnNext=(Button) findViewById(R.id.btn_next2);
		btnNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next2:
			activityManager.getInstance().startNextActivity(ActivityManagerAccountAddBank3.class);
			break;
		default:
			break;
		}
	}

}
