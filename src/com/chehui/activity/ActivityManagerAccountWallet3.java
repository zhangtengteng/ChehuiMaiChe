package com.chehui.activity;

/****
 * 我的钱包页面 提现金额
 */

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chehui.manager.comm.BankManager;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityManagerAccountWallet3 extends BaseActivity implements
		OnClickListener {
	private Button btn;
	private EditText etMoney;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account_wallet3);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		init();
	}

	private void init() {
		btn= (Button) findViewById(R.id.btn_balance3);
		btn.setOnClickListener(this);
		
		etMoney=(EditText) findViewById(R.id.et_money3);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_balance3:
			if(etMoney.getText()!=null){
				BankManager.getInstance().setMoney(etMoney.getText().toString());
				activityManager.getInstance().startNextActivity(
						ActivityManagerAccountWallet5.class);
			}
			break;

		default:
			break;
		}
	}

}
