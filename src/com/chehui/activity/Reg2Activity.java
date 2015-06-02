package com.chehui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.chehui.diy.EditTextWithDel;
import com.chehui.manager.comm.RegisterManager;
import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class Reg2Activity extends BaseActivity {
	private Button btn;
	private EditTextWithDel nickName;
	private EditTextWithDel pwd2;
	private EditTextWithDel pwd1;
	private EditTextWithDel cityName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg2);
		initTitleView(-1, 255, R.string.reg, 255, -1, 0);
		init();
	}

	private void init() {
		
		nickName=(EditTextWithDel) findViewById(R.id.et_nickName);
		pwd1=(EditTextWithDel) findViewById(R.id.et_pwd1);
		pwd2=(EditTextWithDel) findViewById(R.id.et_pwd2);
		cityName=(EditTextWithDel) findViewById(R.id.et_cityName);
		
		btn=(Button) findViewById(R.id.btn_next2);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String trim = nickName.getText().toString().trim();
				String trim2 = cityName.getText().toString().trim();
				RegisterManager.getInstance().setNickName(trim);
				RegisterManager.getInstance().setPassWord(pwd1.getText().toString().trim());
				RegisterManager.getInstance().setCityName(trim2);
				activityManager.getInstance().startNextActivity(Reg3Activity.class);
			}
		});
	}
}
