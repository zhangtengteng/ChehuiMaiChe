package com.chehui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import chehui.maichetong.baseinfoservice.ResultOfTSeller;
import chehui.maichetong.baseinfoservice.ResultOfTUSER;
import chehui.maichetong.baseinfoservice.TSeller;
import chehui.maichetong.baseinfoservice.TUSER;

import com.chehui.comm.CommonData;
import com.chehui.comm.ExtractorThread;
import com.chehui.manage.comm.SharedPreManager;
import com.chehui.manager.comm.ActivityManager;
import com.chehui.manager.comm.RegisterManager;
import com.chehui.ui.base.BaseActivity;
import com.chehui.utils.ToastUtils;
import com.chehui.webservice.manager.WebServiceManger;
import com.example.myproject.R;

public class Reg4Activity extends BaseActivity {
	private Button btn;
	private TSeller tseller;
	// private TUSER tuser;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case CommonData.HTTP_HANDLE_SUCCESS:
				onLoginResponse();
				break;
			case CommonData.HTTP_HANDLE_FAILE:
				if(msg.obj!=null){
					ToastUtils.showShortToast(getApplicationContext(),
							msg.obj.toString());
				}else{
					ToastUtils.showShortToast(getApplicationContext(),
							"服务器异常");
				}
				break;
			default:
				break;
			}
			dismissWaitDialog();
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reg4);
		initTitleView(-1, 255, R.string.reg, 255, -1, 0);
		init();
	}

	private void init() {
		btn = (Button) findViewById(R.id.btn_next4);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				login();
			}
		});

	}

	private void login() {
		showWaitDialog(R.string.common_logining);
		ExtractorThread.getInstance().getAsyncHandler().post(new Runnable() {

			@Override
			public void run() {
				// ResultOfTUSER userLoginByName = WebServiceManger
				// .getInstance()
				// .getBaseInfoService()
				// .userLoginByName(RegisterManager.getInstance().getUserName(),
				// RegisterManager.getInstance().getPassWord(), "");
				ResultOfTSeller userLoginByName = WebServiceManger
						.getInstance()
						.getBaseInfoService()
						.sellerLoginByTel(
								RegisterManager.getInstance().getTel(),
								RegisterManager.getInstance().getPassWord(), "");
				Message message = new Message();
				if (userLoginByName.isSuccess()) {
					message.what = CommonData.HTTP_HANDLE_SUCCESS;
					tseller = userLoginByName.getData();
				} else {
					message.what = CommonData.HTTP_HANDLE_FAILE;
					message.obj = userLoginByName.getMess();
				}
				handler.sendMessage(message);
			}
		});
	}

	private void onLoginResponse() {
		// SharedPreManager.getInstance().setInt(CommonData.USER_ID,
		// tuser.getUserID());
		// SharedPreManager.getInstance().setString(CommonData.USER_NAME,
		// tuser.getName());
		// SharedPreManager.getInstance().setString(CommonData.USER_PHONE,
		// tuser.getTel());
		// SharedPreManager.getInstance().setString(CommonData.USER_EMAIL,
		// tuser.getMail());
		// SharedPreManager.getInstance().setString(CommonData.USER_CITY,
		// tuser.getCityName());

		SharedPreManager.getInstance().setInt(CommonData.USER_ID,
				tseller.getId());
		SharedPreManager.getInstance().setString(CommonData.USER_NAME,
				tseller.getName());
		SharedPreManager.getInstance().setString(CommonData.USER_PHONE,
				tseller.getTel());
		SharedPreManager.getInstance().setString(CommonData.USER_EMAIL,
				tseller.getMail());
		SharedPreManager.getInstance().setString(CommonData.USER_CITY,
				tseller.getCityName());

		startActivity(new Intent(getApplicationContext(), MainActivity.class));
		ActivityManager.getInstance().popAllActivity();
	}
}
