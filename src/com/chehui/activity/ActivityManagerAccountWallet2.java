package com.chehui.activity;

/****
 * 我的钱包页面 提现
 */

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.chehui.manager.comm.BankManager;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityManagerAccountWallet2 extends BaseActivity implements
		OnClickListener {
	private Button btn;
	private TextView money;
	private int balance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account_wallet2);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		init();
	}

	private void init() {
		btn= (Button) findViewById(R.id.btn_balance);
		btn.setOnClickListener(this);
		money=(TextView) findViewById(R.id.tv_money);
		money.setText(String.valueOf(BankManager.getInstance().getBalance()));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_balance:
			activityManager.getInstance().startNextActivity(ActivityManagerAccountWallet3.class);
			break;
		default:
			break;
		}
	}

}
