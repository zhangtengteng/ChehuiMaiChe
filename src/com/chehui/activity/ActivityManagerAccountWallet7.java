package com.chehui.activity;

/****
 * 我的钱包页面   finish
 */

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityManagerAccountWallet7 extends BaseActivity implements
		OnClickListener {
	private Button btnNext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account__wallet_finish);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		init();
	}

	private void init() {
		btnNext=(Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_next:
			break;

		default:
			break;
		}
	}

}
