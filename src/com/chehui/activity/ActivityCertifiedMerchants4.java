package com.chehui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityCertifiedMerchants4 extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cretified_ok);
		init();
		initTitleView(-1, 255, R.string.set_account_money, 255, -1, 0);
	}

	private void init() {
		findViewById(R.id.btn_next1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				ActivityCertifiedMerchants4.this.finish();
			}
		});
	}
}
