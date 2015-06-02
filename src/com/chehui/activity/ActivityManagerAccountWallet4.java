package com.chehui.activity;

/****
 * 我的钱包页面 输入密码
 */

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.widget.EditText;

import com.chehui.ui.base.BaseActivity;
import com.example.myproject.R;

public class ActivityManagerAccountWallet4 extends BaseActivity implements
		OnClickListener {
	private EditText et1;
	private EditText et2;
	private EditText et3;
	private EditText et4;
	private EditText et5;
	private EditText et6;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager_account_wallet4);
		initTitleView(-1, 255, R.string.set_account_persion, 255, -1, 0);
		init();
	}

	private void init() {

		et1 = (EditText) findViewById(R.id.tv_pwd1);
		et2 = (EditText) findViewById(R.id.tv_pwd2);
		et3 = (EditText) findViewById(R.id.tv_pwd3);
		et4 = (EditText) findViewById(R.id.tv_pwd4);
		et5 = (EditText) findViewById(R.id.tv_pwd5);
		et6 = (EditText) findViewById(R.id.tv_pwd6);
			
		
		et1.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (et1.getText().toString() != null) {
					et1.setFocusable(false);
					et2.setFocusable(true);
				}
			}
		});
		et2.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (et2.getText().toString() != null) {
					et2.setFocusable(false);
					et3.setFocusable(true);
				}
			}
		});
		et3.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (et3.getText().toString() != null) {
					et3.setFocusable(false);
					et4.setFocusable(true);
				}
			}
		});
		
		et4.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View arg0, boolean arg1) {
				if (et4.getText().toString() != null) {
					et4.setFocusable(false);
					et5.setFocusable(true);
				}
			}
		});
		
		
		
//		et1.setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(View arg0, int arg1, KeyEvent keyEvent) {
//					if (et1.getText().toString() != null) {
//						et1.setFocusable(false);
//						et2.setFocusable(true);
//					}else{
//						et1.setFocusable(true);
//					}
//				return false;
//			}
//		});
//
//		et2.setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(View arg0, int arg1, KeyEvent keyEvent) {
//					if (et2.getText().toString() != null) {
//						et2.setFocusable(false);
//						et3.setFocusable(true);
//					}else{
//						et2.setFocusable(true);
//					}
//				return false;
//			}
//		});
//		et3.setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(View arg0, int arg1, KeyEvent keyEvent) {
//				if (et3.getText().toString() != null) {
//					et3.setFocusable(false);
//					et4.setFocusable(true);
//				}else{
//					et3.setFocusable(true);
//				}
//
//				return false;
//			}
//		});
//		et4.setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(View arg0, int arg1, KeyEvent keyEvent) {
//				if (et4.getText().toString() != null) {
//
//					et4.setFocusable(false);
//					et5.setFocusable(true);
//				}else{
//					et4.setFocusable(true);
//				}
//				return false;
//			}
//		});
//		et5.setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(View arg0, int arg1, KeyEvent keyEvent) {
//				if (et5.getText().toString() != null) {
//					et5.setFocusable(false);
//					et6.setFocusable(true);
//				}else{
//					et5.setFocusable(true);
//				}
//				return false;
//			}
//		});
//		et6.setOnKeyListener(new OnKeyListener() {
//			@Override
//			public boolean onKey(View arg0, int arg1, KeyEvent keyEvent) {
//				if (et6.getText().toString() != null) {
//					activityManager.getInstance().startNextActivity(
//							ActivityManagerAccountWallet5.class);
//				}
//				return false;
//			}
//		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_balance3:
			break;

		default:
			break;
		}
	}

	private void etsetOnKeyListener(EditText editText1, final EditText editText2) {
		editText1.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View arg0, int arg1, KeyEvent keyEvent) {
				editText2.setFocusable(true);
				return false;
			}
		});

	}

}
