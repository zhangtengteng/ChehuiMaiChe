package com.chehui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityCertifiedMerchants extends BaseActivity {
	private Button btnCertified;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_certified_merchants);
		init();
		initTitleView(-1, 255, R.string.set_account_money, 255, -1, 0);
	}

	private void init() {
		btnCertified = (Button) findViewById(R.id.btn_certified);
		btnCertified.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				activityManager.getInstance().startNextActivity(ActivityCertifiedMerchants2.class);
			}
		});
	}
}
