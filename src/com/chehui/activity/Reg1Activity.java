package com.chehui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.diy.EditTextWithDel;
import com.chehui.manager.comm.RegisterManager;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class Reg1Activity extends BaseActivity {
	private Button btn;
	EditTextWithDel userName;
	EditTextWithDel pwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg1);
		initTitleView(-1, 255, R.string.reg, 255, -1, 0);
		init();
	}

	private void init() {
		userName = (EditTextWithDel) findViewById(R.id.et_userName);
		pwd = (EditTextWithDel) findViewById(R.id.et_pwd);

		btn = (Button) findViewById(R.id.btn_next1);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				RegisterManager.getInstance().setUserName(
						userName.getText().toString().trim());

				RegisterManager.getInstance().setPassWord(
						pwd.getText().toString().trim());

				activityManager.getInstance().startNextActivity(
						Reg2Activity.class);
			}
		});
	}
}
